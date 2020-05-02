package service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import enuns.EnumTipoEqualizacao;
import enuns.EnumTipoPixel;
import enuns.EnumTipoVizinho;
import javafx.scene.chart.BarChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import model.Pixels;
import service.ImageProcessService;
import util.Funcoes;

public class ImageProcessServiceImpl implements ImageProcessService {

	@Override
	public Image escalaDeCinza(Image imagem) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		var pr = imagem.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				var corAntiga = pr.getColor(i, j);
				
				double red = 20;
				double green = 80;
				double blue = 0;

				double mediaA = (corAntiga.getBlue()*(blue/100) + corAntiga.getGreen()*(green/100) + corAntiga.getRed()*(red/100));
//				double mediaA = (corAntiga.getBlue() + corAntiga.getGreen() + corAntiga.getRed()) / 3;
				pw.setColor(i, j, new Color(mediaA, mediaA, mediaA, corAntiga.getOpacity()));
			}
		}

		return wi;
	}

	@Override
	public Image limiarizacao(Image imagem, double limiar) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		var pr = imagem.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				var corAntiga = pr.getColor(i, j);
				Color novaCor;

				if (corAntiga.getRed() >= limiar) {
					novaCor = new Color(1, 1, 1, corAntiga.getOpacity());
				} else {
					novaCor = new Color(0, 0, 0, corAntiga.getOpacity());
				}
				pw.setColor(i, j, novaCor);
			}
		}
		return wi;
	}

	@Override
	public Image negativo(Image imagem) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		var pr = imagem.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				var corAntiga = pr.getColor(i, j);

				double color1 = (1 - (corAntiga.getRed()));
				double color2 = (1 - (corAntiga.getGreen()));
				double color3 = (1 - (corAntiga.getBlue()));

				var novaCor = new Color(color1, color2, color3, corAntiga.getOpacity());
				pw.setColor(i, j, novaCor);
			}
		}
		return wi;
	}

	@Override
	public Image ruido(Image imagem, EnumTipoVizinho tipoVizinho) {
		var w = (int) imagem.getWidth();
		var h = (int) imagem.getHeight();
		var pr = imagem.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();

		for (int i = 1; i < (w - 1); i++) {
			for (int j = 1; j < (h - 1); j++) {

				var prevColor = pr.getColor(i, j);
				var p = new Pixels(prevColor.getRed(), prevColor.getGreen(), prevColor.getBlue(), i, j);

				if (tipoVizinho.equals(EnumTipoVizinho.VIZINHO_CRUZ)) {
					List<Pixels> lista = criaVizinhoC(imagem, p, i, j);

					var medianaR = mediana(lista, EnumTipoPixel.RED);
					var medianaG = mediana(lista, EnumTipoPixel.GREEN);
					var medianaB = mediana(lista, EnumTipoPixel.BLUE);

					pw.setColor(i, j, new Color(medianaR, medianaG, medianaB, prevColor.getOpacity()));
				}

				if (tipoVizinho.equals(EnumTipoVizinho.VIZINHO_X)) {
					List<Pixels> lista = criaVizinhoX(imagem, p, i, j);

					var medianaR = mediana(lista, EnumTipoPixel.RED);
					var medianaG = mediana(lista, EnumTipoPixel.GREEN);
					var medianaB = mediana(lista, EnumTipoPixel.BLUE);

					pw.setColor(i, j, new Color(medianaR, medianaG, medianaB, prevColor.getOpacity()));
				}

				if (tipoVizinho.equals(EnumTipoVizinho.VIZINHO_3X3)) {
					List<Pixels> lista = new ArrayList<>();
					lista.addAll(criaVizinhoC(imagem, p, i, j));
					lista.addAll(criaVizinhoX(imagem, p, i, j));

					var medianaR = mediana(lista, EnumTipoPixel.RED);
					var medianaG = mediana(lista, EnumTipoPixel.GREEN);
					var medianaB = mediana(lista, EnumTipoPixel.BLUE);

					pw.setColor(i, j, new Color(medianaR, medianaG, medianaB, prevColor.getOpacity()));
				}

			}
		}

		return wi;
	}

	@Override
	public List<Pixels> criaVizinhoX(Image imagem, Pixels p, int x, int y) {
		List<Pixels> vizinhos = new ArrayList<>();
		var pr = imagem.getPixelReader();

		var cor1 = pr.getColor(x - 1, y + 1);
		var cor2 = pr.getColor(x + 1, y - 1);
		var cor3 = pr.getColor(x - 1, y - 1);
		var cor4 = pr.getColor(x + 1, y + 1);

		vizinhos.add(new Pixels(cor1.getRed(), cor1.getGreen(), cor1.getBlue(), x - 1, y + 1));
		vizinhos.add(new Pixels(cor2.getRed(), cor2.getGreen(), cor2.getBlue(), x + 1, y - 1));
		vizinhos.add(new Pixels(cor3.getRed(), cor3.getGreen(), cor3.getBlue(), x - 1, y - 1));
		vizinhos.add(new Pixels(cor4.getRed(), cor4.getGreen(), cor4.getBlue(), x + 1, y + 1));
		vizinhos.add(p);

		return vizinhos;
	}

	@Override
	public List<Pixels> criaVizinhoC(Image imagem, Pixels p, int x, int y) {
		List<Pixels> vizinhos = new ArrayList<>();
		var pr = imagem.getPixelReader();

		var cor1 = pr.getColor(x, y - 1);
		var cor2 = pr.getColor(x, y + 1);
		var cor3 = pr.getColor(x - 1, y);
		var cor4 = pr.getColor(x + 1, y);

		vizinhos.add(new Pixels(cor1.getRed(), cor1.getGreen(), cor1.getBlue(), x, y - 1));
		vizinhos.add(new Pixels(cor2.getRed(), cor2.getGreen(), cor2.getBlue(), x, y + 1));
		vizinhos.add(new Pixels(cor3.getRed(), cor3.getGreen(), cor3.getBlue(), x - 1, y));
		vizinhos.add(new Pixels(cor4.getRed(), cor4.getGreen(), cor4.getBlue(), x + 1, y));
		vizinhos.add(p);

		return vizinhos;
	}

	private Double mediana(List<Pixels> listaPixels, EnumTipoPixel tipoPixel) {
		var lista = new ArrayList<Double>();

		if (EnumTipoPixel.RED.equals(tipoPixel)) {
			listaPixels.stream().forEach(pixel -> lista.add(pixel.getRed()));

		} else if (EnumTipoPixel.GREEN.equals(tipoPixel)) {
			listaPixels.stream().forEach(pixel -> lista.add(pixel.getGreen()));

		} else if (EnumTipoPixel.BLUE.equals(tipoPixel)) {
			listaPixels.stream().forEach(pixel -> lista.add(pixel.getBlue()));

		}

		Collections.sort(lista);
		Integer size = lista.size();
		Double indice = size.doubleValue() / 2;

		return lista.get(indice.intValue());
	}

	@Override
	public Image adicao(Image image, Image image2, Double percent1, Double percent2) {
		int w1 = (int) image.getWidth();
		int h1 = (int) image.getHeight();
		int w2 = (int) image2.getWidth();
		int h2 = (int) image2.getHeight();
		int w = (w1 <= w2) ? w1 : w2;
		int h = (h1 <= h2) ? h1 : h2;

		var pr1 = image.getPixelReader();
		var pr2 = image2.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {

				var prevColor1 = pr1.getColor(i, j);
				
				if (!prevColor1.equals(Color.RED)) {
					var prevColor2 = pr2.getColor(i, j);
	
					var color1 = (prevColor2.getRed() * percent2 + prevColor1.getRed() * percent1);
					var color2 = (prevColor2.getGreen() * percent2 + prevColor1.getGreen() * percent1);
					var color3 = (prevColor2.getBlue() * percent2 + prevColor1.getBlue() * percent1);
	
					pw.setColor(i, j, new Color(color1, color2, color3, prevColor1.getOpacity()));
				} else {
					pw.setColor(i, j, Color.RED);
				}
			}
		}

		return wi;

	}

	@Override
	public Image subtracao(Image img1, Image img2) {
		int w1 = (int) img1.getWidth();
		int h1 = (int) img1.getHeight();
		int w2 = (int) img2.getWidth();
		int h2 = (int) img2.getHeight();
		int w = (w1 <= w2) ? w1 : w2;
		int h = (h1 <= h2) ? h1 : h2;

		var pr1 = img1.getPixelReader();
		var pr2 = img2.getPixelReader();

		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color prevColor1 = pr1.getColor(i, j);
				Color prevColor2 = pr2.getColor(i, j);

				var color1 = (prevColor1.getRed() - prevColor2.getRed());
				var color2 = (prevColor1.getGreen() - prevColor2.getGreen());
				var color3 = (prevColor1.getBlue() - prevColor2.getBlue());

				color1 = (color1 < 0) ? 0 : color1;
				color2 = (color2 < 0) ? 0 : color2;
				color3 = (color3 < 0) ? 0 : color3;

				pw.setColor(i, j, new Color(color1, color2, color3, prevColor1.getOpacity()));
			}
		}
		return wi;
	}

	@Override
	public void histograma(Image imagem, BarChart<String, Number> barChart, boolean acumulado,
			boolean separaCor) {

		if (Objects.nonNull(imagem)) {
			var red = Funcoes.getQuantidadeCores(imagem, EnumTipoPixel.RED, acumulado);
			var green = Funcoes.getQuantidadeCores(imagem, EnumTipoPixel.GREEN, acumulado);
			var blue = Funcoes.getQuantidadeCores(imagem, EnumTipoPixel.BLUE, acumulado);

			if (separaCor) {
				Funcoes.insereBarChart(barChart, red);
				Funcoes.insereBarChart(barChart, green);
				Funcoes.insereBarChart(barChart, blue);
			} else {
				Funcoes.insereBarChart(barChart, Funcoes.uneArrayCores(red, green, blue));
			}
		}
	}

	@Override
	public Image histogramaEqualizado(Image imagem, EnumTipoEqualizacao tipoEqualizacao) {
		var w = (int) imagem.getWidth();
		var h = (int) imagem.getHeight();
		var pr = imagem.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();

		var red = Funcoes.getQuantidadeCores(imagem, EnumTipoPixel.RED, false);
		var green = Funcoes.getQuantidadeCores(imagem, EnumTipoPixel.GREEN, false);
		var blue = Funcoes.getQuantidadeCores(imagem, EnumTipoPixel.BLUE, false);

		var redAcumulado = Funcoes.acumulaArray(red);
		var greenAcumulado = Funcoes.acumulaArray(green);
		var blueAcumulado = Funcoes.acumulaArray(blue);

		var listaCores = Funcoes.uneArrayCores(red, green, blue);

		var tonsValidos = Funcoes.tonsValidos(listaCores);
		var primeiroValido = Funcoes.primeiroPixelValido(listaCores);

		if (tipoEqualizacao.equals(EnumTipoEqualizacao.NORMAL)) {
			tonsValidos = 255;
			primeiroValido = 0;
		}

		if (tipoEqualizacao.equals(EnumTipoEqualizacao.CENTRALIZADA)) {
			primeiroValido = 0;
		}

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				var oldCor = pr.getColor(i, j);
				Double oldRed = oldCor.getRed() * 255;
				Double oldGreen = oldCor.getGreen() * 255;
				Double oldBlue = oldCor.getBlue() * 255;

				var acR = redAcumulado[oldRed.intValue()];
				var acG = greenAcumulado[oldGreen.intValue()];
				var acB = blueAcumulado[oldBlue.intValue()];

				var n = Double.parseDouble(String.valueOf((w * h)));

				var pxR = ((tonsValidos - 1) / n) * acR;
				var pxG = ((tonsValidos - 1) / n) * acG;
				var pxB = ((tonsValidos - 1) / n) * acB;

				var corR = (pxR + primeiroValido) / 255;
				var corG = (pxG + primeiroValido) / 255;
				var corB = (pxB + primeiroValido) / 255;

				pw.setColor(i, j, new Color(corR, corG, corB, oldCor.getOpacity()));
			}
		}

		if (tipoEqualizacao.equals(EnumTipoEqualizacao.CENTRALIZADA)) {
			return centralizaPixels(wi, tonsValidos);
		}

		return wi;
	}

	@Override
	public Image centralizaPixels(Image imagem, int tonsValidos) {
		var w = (int) imagem.getWidth();
		var h = (int) imagem.getHeight();
		var pr = imagem.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();
		var tons = (255 - tonsValidos) / 2;

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				var oldCor = pr.getColor(i, j);

				var corR = ((oldCor.getRed() * 255) + tons) / 255;
				var corG = ((oldCor.getGreen() * 255) + tons) / 255;
				var corB = ((oldCor.getBlue() * 255) + tons) / 255;

				pw.setColor(i, j, new Color(corR, corG, corB, oldCor.getOpacity()));
			}
		}

		return wi;
	}

	@Override
	public Image segmentacao(Image imagem, double limiarSegmentacao) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		var pr = imagem.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();

		var red = new Pixels();
		var green = new Pixels();
		var blue = new Pixels();

		// BUSCA AS CORES MÉDIAS PARA RED,GREEN E BLUE
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				var corPixel = pr.getColor(i, j);

				if (corPixel.getRed() > limiarSegmentacao) {
					red.setColor(corPixel);
					
				} else if (corPixel.getGreen() > limiarSegmentacao) {
					green.setColor(corPixel);
					
				} else {
					blue.setColor(corPixel);
				}
			}
		}

		// SETA AS CORES NOS PIXELS DE ACORDO COM A LIMIAR
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				var corAntiga = pr.getColor(i, j);

				if (corAntiga.getRed() > limiarSegmentacao) {
					pw.setColor(i, j, red.getColor(corAntiga.getOpacity()));
					
				} else if (corAntiga.getGreen() > limiarSegmentacao) {
					pw.setColor(i, j, green.getColor(corAntiga.getOpacity()));
					
				} else {
					pw.setColor(i, j, blue.getColor(corAntiga.getOpacity()));
				}
			}
		}

		return wi;
	}
	
	@Override
	public WritableImage rotate90(Image imagem) {
		int w1 = (int) imagem.getWidth();
		int h1 = (int) imagem.getHeight();

		var pr1 = imagem.getPixelReader();
		var wi = new WritableImage(h1,w1);
		var pw = wi.getPixelWriter();
		
		int m = w1 - 1;
		for (int i = 0; i < w1; i++) {
			int n = h1;
			for (int j = 0; j < h1; j++) {
				Color prevColor = pr1.getColor(i, j);

				double color1 = (prevColor.getRed());
				double color2 = (prevColor.getGreen());
				double color3 = (prevColor.getBlue());
				
				Color newColor = new Color(color1, color2, color3, prevColor.getOpacity());
				
				pw.setColor(h1 - n, m, newColor);
				n--;
			}
			m--;
		}
		return wi;
	}
	
	@Override
	public WritableImage rotate180(Image imagem) {
		var wi = rotate90(imagem);
		return rotate90(wi);
	}

	@Override
	public void insereImagemSemEfeito(int w, int h, PixelReader pr, PixelWriter pw) {
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				pw.setColor(i, j, pr.getColor(i, j));
			}
		}
	}
	
	@Override
	public Image borda(Image imagem, Double xInicial, Double xFinal, Double yInicial, Double yFinal, Integer expessura) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		var pr = imagem.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();
		
		yFinal = ((yFinal + expessura) > h) ? h - expessura : yFinal;
		xFinal = ((xFinal + expessura) > w) ? w - expessura : xFinal;

		// NORMAL
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				pw.setColor(i, j, pr.getColor(i, j));
			}
		}

		// BORDAS X
		for (int i = xInicial.intValue(); i < xFinal.intValue(); i++) {
			for (int z = yInicial.intValue(); z < yInicial.intValue() + expessura; z++) {
				pw.setColor(i, z, new Color(0, 0, 0, 1));
			}
			
			for (int z = yFinal.intValue(); z < yFinal.intValue() + expessura; z++) {
				pw.setColor(i, z, new Color(0, 0, 0, 1));
			}
		}

		// BORDAS Y
		for (int i = yInicial.intValue(); i < yFinal.intValue(); i++) {
			for (int z = xInicial.intValue(); z < xInicial.intValue() + expessura; z++) {
				pw.setColor(z, i, new Color(0, 0, 0, 1));
			}
			
			for (int z = xFinal.intValue(); z < xFinal.intValue() + expessura; z++) {
				pw.setColor(z, i, new Color(0, 0, 0, 1));
			}
		}

		return wi;
	}
	
	@Override
	public Image subsPreto(Image imagem) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		var pr = imagem.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();
		
		insereImagemSemEfeito(w, h, pr, pw);
		
		// NORMAL
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				var cor = pr.getColor(x, y);
				
				if (isBlack(cor)) {
					pw.setColor(x, y, Color.RED);
				} else {
					pw.setColor(x, y, Color.WHITE);
				}
			}
		}
		
		return wi;
	}
	
	private boolean isBlack(Color color) {
		return color.getBlue() == 0 && color.getGreen() == 0 && color.getRed() == 0;
	}
	
}