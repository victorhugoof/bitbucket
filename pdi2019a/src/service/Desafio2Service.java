package service;

import javafx.scene.image.Image;

public interface Desafio2Service {

	Image executar(Image imagem, Double xInicial, Double xFinal, Double yInicial, Double yFinal);
	Image borda(Image imagem, Double xInicial, Double xFinal, Double yInicial, Double yFinal, Integer expessura);
}
