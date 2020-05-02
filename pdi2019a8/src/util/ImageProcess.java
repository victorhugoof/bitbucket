package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enuns.EnumTipoPixel;
import enuns.EnumTipoVizinho;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import model.Pixels;

public class ImageProcess {

	private ImageProcess() {
		super();
	}

	public static Image escalaDeCinza(Image imagem, double red, double green, double blue) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();
		double soma = (red + green + blue);

		PixelReader pr = imagem.getPixelReader();
		WritableImage wi = new WritableImage(w, h);
		PixelWriter pw = wi.getPixelWriter();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color corAntiga = pr.getColor(i, j);

				if (soma == 100) {
					double mediaP = (corAntiga.getBlue() * (blue / 100) + corAntiga.getGreen() * (green / 100) + corAntiga.getRed() * (red / 100));

					Color novaCor = new Color(mediaP, mediaP, mediaP, corAntiga.getOpacity());
					pw.setColor(i, j, novaCor);

				} else {
					double mediaA = (corAntiga.getBlue() + corAntiga.getGreen() + corAntiga.getRed()) / 3;

					Color novaCor = new Color(mediaA, mediaA, mediaA, corAntiga.getOpacity());
					pw.setColor(i, j, novaCor);
				}
			}
		}

		return wi;
	}

	public static Image limiarizacao(Image imagem, double limiar) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		PixelReader pr = imagem.getPixelReader();
		WritableImage wi = new WritableImage(w, h);
		PixelWriter pw = wi.getPixelWriter();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color corAntiga = pr.getColor(i, j);
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

	public static Image negativo(Image imagem) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		PixelReader pr = imagem.getPixelReader();
		WritableImage wi = new WritableImage(w, h);
		PixelWriter pw = wi.getPixelWriter();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color corAntiga = pr.getColor(i, j);

				double color1 = (1 - (corAntiga.getRed()));
				double color2 = (1 - (corAntiga.getGreen()));
				double color3 = (1 - (corAntiga.getBlue()));

				Color novaCor = new Color(color1, color2, color3, corAntiga.getOpacity());
				pw.setColor(i, j, novaCor);
			}
		}
		return wi;
	}

	public static Image ruido(Image imagem, EnumTipoVizinho tipoVizinho) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();
		PixelReader pr = imagem.getPixelReader();
		WritableImage wi = new WritableImage(w, h);
		PixelWriter pw = wi.getPixelWriter();

		for (int i = 1; i < (w - 1); i++) {
			for (int j = 1; j < (h - 1); j++) {

				Color prevColor = pr.getColor(i, j);
				Pixels p = new Pixels(prevColor.getRed(), prevColor.getGreen(), prevColor.getBlue(), i, j);

				if (tipoVizinho.equals(EnumTipoVizinho.VIZINHO_CRUZ)) {
					List<Pixels> lista = criaVizinhoC(imagem, p, i, j);

					double medianaR = mediana(lista, EnumTipoPixel.RED);
					double medianaG = mediana(lista, EnumTipoPixel.GREEN);
					double medianaB = mediana(lista, EnumTipoPixel.BLUE);

					pw.setColor(i, j, new Color(medianaR, medianaG, medianaB, prevColor.getOpacity()));
				}

				if (tipoVizinho.equals(EnumTipoVizinho.VIZINHO_X)) {
					List<Pixels> lista = criaVizinhoX(imagem, p, i, j);

					double medianaR = mediana(lista, EnumTipoPixel.RED);
					double medianaG = mediana(lista, EnumTipoPixel.GREEN);
					double medianaB = mediana(lista, EnumTipoPixel.BLUE);

					pw.setColor(i, j, new Color(medianaR, medianaG, medianaB, prevColor.getOpacity()));
				}

				if (tipoVizinho.equals(EnumTipoVizinho.VIZINHO_3X3)) {
					List<Pixels> lista = new ArrayList<>();
					lista.addAll(criaVizinhoC(imagem, p, i, j));
					lista.addAll(criaVizinhoX(imagem, p, i, j));

					double medianaR = mediana(lista, EnumTipoPixel.RED);
					double medianaG = mediana(lista, EnumTipoPixel.GREEN);
					double medianaB = mediana(lista, EnumTipoPixel.BLUE);

					pw.setColor(i, j, new Color(medianaR, medianaG, medianaB, prevColor.getOpacity()));
				}

			}
		}

		return wi;
	}

	public static List<Pixels> criaVizinhoX(Image imagem, Pixels p, int x, int y) {
		List<Pixels> vizinhos = new ArrayList<>();
		PixelReader pr = imagem.getPixelReader();

		Color cor1 = pr.getColor(x - 1, y + 1);
		Color cor2 = pr.getColor(x + 1, y - 1);
		Color cor3 = pr.getColor(x - 1, y - 1);
		Color cor4 = pr.getColor(x + 1, y + 1);

		vizinhos.add(new Pixels(cor1.getRed(), cor1.getGreen(), cor1.getBlue(), x - 1, y + 1));
		vizinhos.add(new Pixels(cor2.getRed(), cor2.getGreen(), cor2.getBlue(), x + 1, y - 1));
		vizinhos.add(new Pixels(cor3.getRed(), cor3.getGreen(), cor3.getBlue(), x - 1, y - 1));
		vizinhos.add(new Pixels(cor4.getRed(), cor4.getGreen(), cor4.getBlue(), x + 1, y + 1));
		vizinhos.add(p);

		return vizinhos;
	}

	public static List<Pixels> criaVizinhoC(Image imagem, Pixels p, int x, int y) {
		List<Pixels> vizinhos = new ArrayList<>();
		PixelReader pr = imagem.getPixelReader();

		Color cor1 = pr.getColor(x, y - 1);
		Color cor2 = pr.getColor(x, y + 1);
		Color cor3 = pr.getColor(x - 1, y);
		Color cor4 = pr.getColor(x + 1, y);

		vizinhos.add(new Pixels(cor1.getRed(), cor1.getGreen(), cor1.getBlue(), x, y - 1));
		vizinhos.add(new Pixels(cor2.getRed(), cor2.getGreen(), cor2.getBlue(), x, y + 1));
		vizinhos.add(new Pixels(cor3.getRed(), cor3.getGreen(), cor3.getBlue(), x - 1, y));
		vizinhos.add(new Pixels(cor4.getRed(), cor4.getGreen(), cor4.getBlue(), x + 1, y));
		vizinhos.add(p);

		return vizinhos;
	}

	private static Double mediana(List<Pixels> listaPixels, EnumTipoPixel tipoPixel) {
		List<Double> lista = new ArrayList<Double>();

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

	public static Image adicao(Image image, Image image2, Double percent1, Double percent2) {
		int w1 = (int) image.getWidth();
		int h1 = (int) image.getHeight();
		int w2 = (int) image2.getWidth();
		int h2 = (int) image2.getHeight();
		int w = (w1 <= w2) ? w1 : w2;
		int h = (h1 <= h2) ? h1 : h2;

		PixelReader pr1 = image.getPixelReader();
		PixelReader pr2 = image2.getPixelReader();
		WritableImage wi = new WritableImage(w2, h2);
		PixelWriter pw = wi.getPixelWriter();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {

				Color prevColor1 = pr1.getColor(i, j);
				Color prevColor2 = pr2.getColor(i, j);

				double color1 = (prevColor2.getRed() * percent2 + prevColor1.getRed() * percent1);
				double color2 = (prevColor2.getGreen() * percent2 + prevColor1.getGreen() * percent1);
				double color3 = (prevColor2.getBlue() * percent2 + prevColor1.getBlue() * percent1);

				pw.setColor(i, j, new Color(color1, color2, color3, prevColor1.getOpacity()));
			}
		}

		return wi;

	}

	public static Image subtracao(Image img1, Image img2) {
		int w1 = (int) img1.getWidth();
		int h1 = (int) img1.getHeight();
		int w2 = (int) img2.getWidth();
		int h2 = (int) img2.getHeight();
		int w = (w1 <= w2) ? w1 : w2;
		int h = (h1 <= h2) ? h1 : h2;

		PixelReader pr1 = img1.getPixelReader();
		PixelReader pr2 = img2.getPixelReader();

		WritableImage wi = new WritableImage(w2, h2);
		PixelWriter pw = wi.getPixelWriter();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color prevColor1 = pr1.getColor(i, j);
				Color prevColor2 = pr2.getColor(i, j);

				double color1 = (prevColor1.getRed() - prevColor2.getRed());
				double color2 = (prevColor1.getGreen() - prevColor2.getGreen());
				double color3 = (prevColor1.getBlue() - prevColor2.getBlue());

				color1 = (color1 < 0) ? 0 : color1;
				color2 = (color2 < 0) ? 0 : color2;
				color3 = (color3 < 0) ? 0 : color3;

				pw.setColor(i, j, new Color(color1, color2, color3, prevColor1.getOpacity()));
			}
		}
		return wi;
	}

}