package service.impl;

import java.util.ArrayList;
import java.util.Objects;

import factory.ServiceFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import service.ImageProcessService;
import service.Prova1Service;
import util.Funcoes;

public class Prova1ServiceImpl implements Prova1Service {
	
	private ImageProcessService imageProcessService = ServiceFactory.getImageProcessService();

	@Override
	public Image questao1(Image imagem, Integer quantidade) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		var pr = imagem.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();

		imageProcessService.insereImagemSemEfeito(w, h, pr, pw);

		for (int q = 0; q < quantidade; q++) {
			if (q % 2 == 0) {
				var quadrante = (w / quantidade) * q;
				var proxQuadrante = quadrante + (w / quantidade);

				for (int i = quadrante; i < proxQuadrante; i++) {
					for (int j = 0; j < h; j++) {
						var corAntiga = pr.getColor(i, j);
						var mediaA = (corAntiga.getBlue() + corAntiga.getGreen() + corAntiga.getRed()) / 3;
						pw.setColor(i, j, new Color(mediaA, mediaA, mediaA, corAntiga.getOpacity()));
					}
				}
			}
		}

		return wi;
	}

	@Override
	public Image questao2(Image imagem, Double xInicial, Double xFinal, Double yInicial, Double yFinal) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();

		var pr = imagem.getPixelReader();
		var wi = new WritableImage(w, h);
		var pw = wi.getPixelWriter();

		var pixels = new ArrayList<Color>();
		int count = 0;

		imageProcessService.insereImagemSemEfeito(w, h, pr, pw);

		try {
			if (Objects.isNull(xInicial) || Objects.isNull(xFinal) || Objects.isNull(yInicial)
					|| Objects.isNull(yFinal)) {
				throw new RuntimeException("Selecione uma área válida da imagem!");
			}

			yFinal = (yFinal > h) ? h : yFinal;
			xFinal = (xFinal > w) ? w : xFinal;

			// BUSCA OS PIXELS PARA INVERTER
			for (int i = xInicial.intValue(); i < xFinal.intValue(); i++) {
				for (int j = yInicial.intValue(); j < yFinal.intValue(); j++) {
					pixels.add(pr.getColor(i, j));
				}
			}

			// INSERE OS PIXELS POREM INVERTIDOS
			for (int i = xFinal.intValue() - 1; i > xInicial.intValue() - 1; i--) {
				for (int j = yFinal.intValue() - 2; j > yInicial.intValue() - 1; j--) {
					pw.setColor(i, j, pixels.get(count));
					count++;
				}
				count++;
			}
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}

		return wi;
	}

	@Override
	public void questao3(Image imagem) {
		int w = (int) imagem.getWidth();
		int h = (int) imagem.getHeight();
		var pr = imagem.getPixelReader();

		var msg = new StringBuilder();
		var bordasY = new ArrayList<Integer>();
		
		for (int i = 0; i < w; i++) {
			var qtdPreto = 0;
			
			for (int j = 0; j < h; j++) {
				var color = pr.getColor(i, j);
				
				if (isBlack(color)) {
					qtdPreto++;
				}
			}
			
			if (qtdPreto > 2) {
				bordasY.add(qtdPreto);
			}
			
		}

		if (bordasY.size() > 2) {
			msg.append("Preenchido");
		} else {
			msg.append("Não preenchido");
		}

		Funcoes.exibeMsg("Questão 3", "Questão 3", msg.toString(), AlertType.INFORMATION);
	}

	@Override
	public Image segmentacao(Image imagem) {

		double limiar = 100;
		return imageProcessService.segmentacao(imagem, limiar / 255);
	}

	private boolean isBlack(Color cor) {
		return cor.getRed() == 0 && cor.getBlue() == 0 && cor.getGreen() == 0;
	}
}
