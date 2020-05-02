package service;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public interface SegundaChamadaService {

	Image questao1(Image imagem, Double distancia, Color color);
	void questao2(Image imagem);

	void questao3(Image imagem, Double xInicial, Double xFinal, Double yInicial, Double yFinal);

}
