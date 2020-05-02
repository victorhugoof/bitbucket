package service;

import java.util.List;

import enuns.EnumTipoEqualizacao;
import enuns.EnumTipoVizinho;
import javafx.scene.chart.BarChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import model.Pixels;

public interface ImageProcessService {

	Image escalaDeCinza(Image imagem);
	Image limiarizacao(Image imagem, double limiar);
	Image negativo(Image imagem);
	Image ruido(Image imagem, EnumTipoVizinho tipoVizinho);
	Image adicao(Image image, Image image2, Double percent1, Double percent2);
	Image subtracao(Image img1, Image img2);
	Image histogramaEqualizado(Image imagem, EnumTipoEqualizacao tipoEqualizacao);
	Image centralizaPixels(Image imagem, int tonsValidos);
	Image segmentacao(Image imagem, double limiarSegmentacao);
	Image borda(Image imagem, Double xInicial, Double xFinal, Double yInicial, Double yFinal, Integer expessura);
	
	List<Pixels> criaVizinhoX(Image imagem, Pixels p, int x, int y);
	List<Pixels> criaVizinhoC(Image imagem, Pixels p, int x, int y);

	WritableImage rotate90(Image imagem);
	WritableImage rotate180(Image imagem);
	
	void insereImagemSemEfeito(int w, int h, PixelReader pr, PixelWriter pw);
	void histograma(Image imagem, BarChart<String, Number> barChart, boolean acumulado, boolean separaCor);
	Image subsPreto(Image imagem);
	
}
