package controller;

import java.util.Objects;

import enuns.EnumTipoVizinho;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import util.Desafio1;
import util.Desafio2;
import util.Funcoes;
import util.ImageProcess;

public class PrincipalController {

	@FXML
	private ImageView imageView;

	@FXML
	private ImageView imageView2;

	@FXML
	private ImageView imageView3;

	@FXML
	private Label lblR;

	@FXML
	private Label lblG;

	@FXML
	private Label lblB;

	@FXML
	private Slider slider;

	@FXML
	private Slider slider1;

	@FXML
	private Pane pnlCor;

	@FXML
	public void initialize() {
		slider.valueProperty().addListener((observable, oldValue, newValue) -> limiarizacao());
		slider1.valueProperty().addListener((observable, oldValue, newValue) -> adicao());
	}

	@FXML
	public void abreImagem1() {
		Funcoes.atualizaImageView(imageView, Funcoes.selecionaImagem());
	}

	@FXML
	public void abreImagem2() {
		Funcoes.atualizaImageView(imageView2, Funcoes.selecionaImagem());
	}

	@FXML
	public void rasterIMG(MouseEvent evt) {
		ImageView iw = (ImageView) evt.getTarget();

		if (Objects.nonNull(iw.getImage())) {
			Funcoes.verificaCor(iw.getImage(), (int) evt.getX(), (int) evt.getY(), lblR, lblG, lblB, pnlCor);
		}
	}

	@FXML
	public void salvar() {
		if (Funcoes.verificarImagem(imageView3)) {
			Funcoes.salvarImagem(imageView3.getImage());
		}
	}

	@FXML
	public void escalaDeCinzaMedia() {
		if (Funcoes.verificarImagem(imageView)) {
			Funcoes.atualizaImageView(imageView3, ImageProcess.escalaDeCinza(imageView.getImage(), 0, 0, 0));
		}
	}

	@FXML
	public void limiarizacao() {
		if (Funcoes.verificarImagem(imageView)) {
			Funcoes.atualizaImageView(imageView3,
					ImageProcess.limiarizacao(imageView.getImage(), (slider.getValue() / 255)));
		}
	}

	@FXML
	public void negativo() {
		if (Funcoes.verificarImagem(imageView)) {
			Funcoes.atualizaImageView(imageView3, ImageProcess.negativo(imageView.getImage()));
		}
	}

	@FXML
	public void reduzirRuidoX() {
		if (Funcoes.verificarImagem(imageView)) {
			Funcoes.atualizaImageView(imageView3, ImageProcess.ruido(imageView.getImage(), EnumTipoVizinho.VIZINHO_X));
		}
	}

	@FXML
	public void reduzirRuidoCruz() {
		if (Funcoes.verificarImagem(imageView)) {
			Funcoes.atualizaImageView(imageView3,
					ImageProcess.ruido(imageView.getImage(), EnumTipoVizinho.VIZINHO_CRUZ));
		}
	}

	@FXML
	public void reduzirRuido3x3() {
		if (Funcoes.verificarImagem(imageView)) {
			Funcoes.atualizaImageView(imageView3,
					ImageProcess.ruido(imageView.getImage(), EnumTipoVizinho.VIZINHO_3X3));
		}
	}

	@FXML
	public void adicao() {
		if (Funcoes.verificarImagem(imageView) && Funcoes.verificarImagem(imageView2)) {
			Double img1 = (slider1.getValue() / 100);
			Double img2 = 1 - img1;
			Funcoes.atualizaImageView(imageView3,
					ImageProcess.adicao(imageView.getImage(), imageView2.getImage(), img1, img2));
		}
	}

	@FXML
	public void subtracao() {
		if (Funcoes.verificarImagem(imageView) && Funcoes.verificarImagem(imageView2)) {
			Funcoes.atualizaImageView(imageView3, ImageProcess.subtracao(imageView.getImage(), imageView2.getImage()));
		}
	}

	/* ------------------------ DESAFIO 1 ----------------------- */

	@FXML
	private ImageView imageViewDesafio1;

	private Image imagemDesafio1;

	@FXML
	public void abreImagemDesafio1() {
		imagemDesafio1 = Funcoes.selecionaImagem();
		Funcoes.atualizaImageView(imageViewDesafio1, imagemDesafio1);
	}

	@FXML
	public void aplicarFiltroDesafio1() {
		if (Funcoes.verificarImagem(imageViewDesafio1)) {
			Funcoes.atualizaImageView(imageViewDesafio1, Desafio1.execute(imageViewDesafio1.getImage()));
		}
	}

	@FXML
	public void removerFiltroDesafio1() {
		if (Funcoes.verificarImagem(imageViewDesafio1)) {
			Funcoes.atualizaImageView(imageViewDesafio1, imagemDesafio1);
		}
	}

	@FXML
	public void salvarImagemDesafio1() {
		if (Funcoes.verificarImagem(imageViewDesafio1)) {
			Funcoes.salvarImagem(imageViewDesafio1.getImage());
		}
	}

	/* ------------------------ DESAFIO 2 ----------------------- */

	@FXML
	private ImageView imageViewDesafio2;

	private Image imagemDesafio2;
	
	private Double xInicial;
	private Double xFinal;
	private Double yInicial;
	private Double yFinal;

	@FXML
	public void abreImagemDesafio2() {
		imagemDesafio2 = Funcoes.selecionaImagem();
		Funcoes.atualizaImageView(imageViewDesafio2, imagemDesafio2);
	}

	@FXML
	public void aplicarFiltroDesafio2() {
		if (Funcoes.verificarImagem(imageViewDesafio2)) {
			Funcoes.atualizaImageView(imageViewDesafio2, Desafio2.executar(imageViewDesafio2.getImage(), xInicial, xFinal, yInicial, yFinal));
		}
	}

	@FXML
	public void removerFiltroDesafio2() {
		if (Funcoes.verificarImagem(imageViewDesafio2)) {
			Funcoes.atualizaImageView(imageViewDesafio2, imagemDesafio2);
		}
	}

	@FXML
	public void salvarImagemDesafio2() {
		if (Funcoes.verificarImagem(imageViewDesafio2)) {
			Funcoes.salvarImagem(imageViewDesafio2.getImage());
		}
	}
	
	@FXML
	public void pressedImagemDesafio2(MouseEvent evt) {
		xInicial = evt.getX();
		yInicial = evt.getY();
	}
	
	@FXML
	public void releasedImagemDesafio2(MouseEvent evt) {
		xFinal = evt.getX();
		yFinal = evt.getY();
		
		if (Funcoes.verificarImagem(imageViewDesafio2)) {
			Funcoes.atualizaImageView(imageViewDesafio2, Desafio2.borda(imageViewDesafio2.getImage(), xInicial, xFinal, yInicial, yFinal, 5));
		}
	}

}
