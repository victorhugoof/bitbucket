package util;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Prova1 {

	public Prova1() {
		super();
	}

	public static Image questao1(Image imagem, Double distancia, Color color) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		var pr = imagem.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();

		ImageProcess.insereImagemSemEfeito(w, h, pr, pw);

		// INSERE AS GRADES
		for (int i = 0; i < h; i++) {
			for (int x = distancia.intValue(); x < w; x += distancia + 1) {
				for (int z = x; z < x + 1; z++) {
					pw.setColor(z, i, color);
				}
			}
		}

		return wi;
	}

	public static Image questao2(Image imagem) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		var pr = imagem.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();

		var pixels = new ArrayList<Color>();
		int count = 0;

		ImageProcess.insereImagemSemEfeito(w, h, pr, pw);

		// BUSCA OS PIXELS PARA INVERTER
		for (int i = 0; i < w; i++) {
			for (int j = h / 2; j < h; j++) {
				pixels.add(pr.getColor(i, j));
			}
		}

		// INSERE OS PIXELS POREM INVERTIDOS
		for (int i = w - 1; i > 0 - 1; i--) {
			for (int j = h - 2; j > (h / 2) - 1; j--) {
				pw.setColor(i, j, pixels.get(count));
				count++;
			}
			count++;
		}

		return wi;
	}

	public static void questao3(Image imagem, Double xInicial, Double xFinal, Double yInicial, Double yFinal) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();
		var pr = imagem.getPixelReader();

		var cores = new ArrayList<Color>();
		var coresDiferentes = new ArrayList<Color>();
		var msg = new StringBuilder();

		try {
			if (Objects.isNull(xInicial) || Objects.isNull(xFinal) || Objects.isNull(yInicial)
					|| Objects.isNull(yFinal)) {
				throw new RuntimeException("Selecione uma área válida da imagem!");
			}

			yFinal = (yFinal > h) ? h : yFinal;
			xFinal = (xFinal > w) ? w : xFinal;

			for (int i = xInicial.intValue(); i < xFinal.intValue(); i++) {
				for (int j = yInicial.intValue(); j < yFinal.intValue(); j++) {
					cores.add(pr.getColor(i, j));
				}
			}
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}

		if (!cores.isEmpty()) {
			coresDiferentes.addAll(cores.stream().distinct().collect(Collectors.toList()));
			coresDiferentes.stream().forEach(cor -> {
				Double red = cor.getRed() * 255;
				Double green = cor.getGreen() * 255;
				Double blue = cor.getBlue() * 255;
	
				msg.append("Cor: RGB(")
					.append(red.intValue())
					.append(", ")
					.append(green.intValue())
					.append(", ")
					.append(blue.intValue())
					.append(")\n");
			});
	
			Funcoes.exibeMsg("Questão 3", "Questão 3 -> Cores diferentes", msg.toString(), AlertType.INFORMATION);
		}
	}

	public static Image segmentacao(Image imagem) {

		double limiar = 100;
		return ImageProcess.segmentacao(imagem, limiar / 255);
	}

}
