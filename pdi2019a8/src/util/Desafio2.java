package util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Desafio2 {

	private Desafio2() {
		super();
	}

	public static Image executar(Image imagem, Double xInicial, Double xFinal, Double yInicial, Double yFinal) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		PixelReader pr = imagem.getPixelReader();
		WritableImage wi = new WritableImage(w, h);
		PixelWriter pw = wi.getPixelWriter();

		yFinal = (yFinal > h) ? h : yFinal;
		xFinal = (xFinal > w) ? w : xFinal;

		// NORMAL
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				pw.setColor(i, j, pr.getColor(i, j));
			}
		}

		// CINZA
		for (int i = xInicial.intValue(); i < xFinal.intValue(); i++) {
			for (int j = yInicial.intValue(); j < yFinal.intValue(); j++) {
				Color corAntiga = pr.getColor(i, j);

				double color1 = (1 - (corAntiga.getRed()));
				double color2 = (1 - (corAntiga.getGreen()));
				double color3 = (1 - (corAntiga.getBlue()));

				pw.setColor(i, j, new Color(color1, color2, color3, corAntiga.getOpacity()));
			}
		}

		return wi;
	}

	public static Image borda(Image imagem, Double xInicial, Double xFinal, Double yInicial, Double yFinal, Integer expessura) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		PixelReader pr = imagem.getPixelReader();
		WritableImage wi = new WritableImage(w, h);
		PixelWriter pw = wi.getPixelWriter();

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
}
