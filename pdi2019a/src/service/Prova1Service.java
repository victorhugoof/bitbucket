package service;

import javafx.scene.image.Image;

public interface Prova1Service {

	Image questao1(Image imagem, Integer quantidade);
	Image questao2(Image imagem, Double xInicial, Double xFinal, Double yInicial, Double yFinal);
	Image segmentacao(Image imagem);
	
	void questao3(Image imagem);

}
