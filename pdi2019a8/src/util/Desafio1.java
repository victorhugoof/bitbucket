package util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Desafio1 {

	private Desafio1() {
		super();
	}

	public static Image execute(Image imagem) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		PixelReader pr = imagem.getPixelReader();
		WritableImage wi = new WritableImage(w, h);
		PixelWriter pw = wi.getPixelWriter();

		insereImagemSemEfeito(w, h, pr, pw);
		aplicaCinza(w, h, pr, pw);
		aplicaLimiar(w, h, pr, pw);
		aplicaNegativo(w, h, pr, pw);

		return wi;
	}

	private static void insereImagemSemEfeito(int w, int h, PixelReader pr, PixelWriter pw) {
		for (int i = 0; i < w / 4; i++) {
			for (int j = 0; j < h; j++) {
				pw.setColor(i, j, pr.getColor(i, j));
			}
		}
	}

	private static void aplicaCinza(int w, int h, PixelReader pr, PixelWriter pw) {
		for (int i = w / 4; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color corAntiga = pr.getColor(i, j);
				double mediaA = (corAntiga.getBlue() + corAntiga.getGreen() + corAntiga.getRed()) / 3;
				Color novaCor = new Color(mediaA, mediaA, mediaA, corAntiga.getOpacity());
				pw.setColor(i, j, novaCor);
			}
		}
	}

	private static void aplicaLimiar(int w, int h, PixelReader pr, PixelWriter pw) {
		for (int i = (w / 4) * 2; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color corAntiga = pr.getColor(i, j);

				if (corAntiga.getRed() >= 127D / 255) {
					pw.setColor(i, j, new Color(1, 1, 1, corAntiga.getOpacity()));
				} else {
					pw.setColor(i, j, new Color(0, 0, 0, corAntiga.getOpacity()));
				}
			}
		}
	}

	private static void aplicaNegativo(int w, int h, PixelReader pr, PixelWriter pw) {
		for (int i = (w / 4) * 3; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color corAntiga = pr.getColor(i, j);

				double color1 = (1 - (corAntiga.getRed()));
				double color2 = (1 - (corAntiga.getGreen()));
				double color3 = (1 - (corAntiga.getBlue()));

				Color novaCor = new Color(color1, color2, color3, corAntiga.getOpacity());
				pw.setColor(i, j, novaCor);
			}
		}
	}
}
