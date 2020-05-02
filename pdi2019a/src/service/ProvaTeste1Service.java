package service;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public interface ProvaTeste1Service {

	Image questao1(Image imagem, Double distancia, Color color);
	Image questao2(Image imagem);
	Image segmentacao(Image imagem);

	void questao3(Image imagem, Double xInicial, Double xFinal, Double yInicial, Double yFinal);

}
