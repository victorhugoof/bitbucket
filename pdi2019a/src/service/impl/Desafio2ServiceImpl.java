package service.impl;

import java.util.Objects;

import factory.ServiceFactory;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import service.Desafio2Service;
import util.Funcoes;

public class Desafio2ServiceImpl implements Desafio2Service {

	@Override
	public Image executar(Image imagem, Double xInicial, Double xFinal, Double yInicial, Double yFinal) {

		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		var pr = imagem.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();

		// NORMAL
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				pw.setColor(i, j, pr.getColor(i, j));
			}
		}

		try {
			if (Objects.isNull(xInicial) || Objects.isNull(xFinal) || Objects.isNull(yInicial)
					|| Objects.isNull(yFinal)) {
				throw new RuntimeException("Selecione uma área válida da imagem!");
			}

			yFinal = (yFinal > h) ? h : yFinal;
			xFinal = (xFinal > w) ? w : xFinal;

			// CINZA
			for (int i = xInicial.intValue(); i < xFinal.intValue(); i++) {
				for (int j = yInicial.intValue(); j < yFinal.intValue(); j++) {
					var corAntiga = pr.getColor(i, j);

					double color1 = (1 - (corAntiga.getRed()));
					double color2 = (1 - (corAntiga.getGreen()));
					double color3 = (1 - (corAntiga.getBlue()));

					pw.setColor(i, j, new Color(color1, color2, color3, corAntiga.getOpacity()));
				}
			}
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}

		return wi;
	}

	@Override
	public Image borda(Image imagem, Double xInicial, Double xFinal, Double yInicial, Double yFinal,
			Integer expessura) {
		return ServiceFactory.getImageProcessService().borda(imagem, xInicial, xFinal, yInicial, yFinal, expessura);
	}
}
