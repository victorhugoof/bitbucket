package controller;

import java.io.File;
import java.util.Objects;

import org.opencv.core.Core;

import enuns.EnumTipoEqualizacao;
import enuns.EnumTipoVizinho;
import factory.ServiceFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.Desafio1Service;
import service.Desafio2Service;
import service.ImageProcessService;
import service.Prova1Service;
import service.ProvaTeste1Service;
import util.Funcoes;

public class PrincipalController extends ArtigoController {
	
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
	private Slider slider2;

	@FXML
	private Pane pnlCor;

	@FXML
	private RadioButton eqNormal;

	@FXML
	private RadioButton eqValid;

	@FXML
	private RadioButton eqCentralizada;

	private ImageProcessService imageProcessService = ServiceFactory.getImageProcessService();
	private Desafio1Service desafio1Service = ServiceFactory.getDesafio1Service();
	private Desafio2Service desafio2Service = ServiceFactory.getDesafio2Service();
	private ProvaTeste1Service provaTeste1Service = ServiceFactory.getProvaTeste1Service();
	private Prova1Service prova1Service = ServiceFactory.getProva1Service();

	@FXML
	public void initialize() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		slider.valueProperty().addListener((observable, oldValue, newValue) -> limiarizacao());
		slider1.valueProperty().addListener((observable, oldValue, newValue) -> adicao());
		slider2.valueProperty().addListener((observable, oldValue, newValue) -> segmentacao());
		
		sliderProvaTeste1Questao1.valueProperty().addListener((observable, oldValue, newValue) -> aplicarQuestao1ProvaTeste1());
		
		sliderDilatacaoOpenCv.valueProperty().addListener((observable, oldValue, newValue) -> {
			lblSizeDilatacao.setText(String.format("Size dilatação: %s", newValue));
			aplicaFiltroDilatacaoOpenCv();
			});
		sliderErosaoOpenCv.valueProperty().addListener((observable, oldValue, newValue) -> {
			lblSizeErosao.setText(String.format("Size erosão: %s", newValue));
			aplicaFiltroErosaoOpenCv();
		});
		sliderBordaOpenCv.valueProperty().addListener((observable, oldValue, newValue) -> {
			lblSizeBordaCanny.setText(String.format("Size Canny: %s", newValue));
			aplicaFiltroBordaCannyOpenCv();
		});
		sliderBordaBlurOpenCv.valueProperty().addListener((observable, oldValue, newValue) -> {
			lblSizeBordaCannyBlur.setText(String.format("Size Blur: %s", newValue.intValue()));
		});
		sliderBordaSobelOpenCv.valueProperty().addListener((observable, oldValue, newValue) -> {
			lblSizeBordaSobel.setText(String.format("Size Sobel: %s", newValue));
			aplicaFiltroBordaSobelOpenCv();
		});
		sliderBordaLaplacianOpenCv.valueProperty().addListener((observable, oldValue, newValue) -> {
			lblSizeBordaLaplacian.setText(String.format("Size Laplacian: %s", newValue));
			aplicaFiltroBordaLaplacianOpenCv();
		});
		
		super.initialize();
//		super.arquivoImagem = new File("C:\\Users\\vhmf1\\Documents\\UNISUL\\pdi\\artigo\\kiwi.png");
//		abreImagemArtigo();
//		executarArtigo();
	}

	@FXML
	public void abreImagem1() {
		try {
			Funcoes.atualizaImageView(imageView, Funcoes.selecionaImagem());
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}

	@FXML
	public void abreImagem2() {
		try {
			Funcoes.atualizaImageView(imageView2, Funcoes.selecionaImagem());
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}

	@FXML
	public void rasterIMG(MouseEvent evt) {
		var iw = (ImageView) evt.getTarget();

		if (Objects.nonNull(iw.getImage())) {
			Funcoes.verificaCor(iw.getImage(), (int) evt.getX(), (int) evt.getY(), lblR, lblG, lblB, pnlCor);
		}
	}

	@FXML
	public void salvar() {
		try {
			if (Funcoes.verificarImagem(imageView3)) {
				Funcoes.salvarImagem(imageView3.getImage());
			}
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}

	@FXML
	public void escalaDeCinzaMedia() {
		if (Funcoes.verificarImagem(imageView)) {
			Funcoes.atualizaImageView(imageView3, imageProcessService.escalaDeCinza(imageView.getImage()));
		}
	}

	@FXML
	public void limiarizacao() {
		if (Funcoes.verificarImagem(imageView)) {
			Funcoes.atualizaImageView(imageView3,
					imageProcessService.limiarizacao(imageView.getImage(), (slider.getValue() / 255)));
		}
	}

	@FXML
	public void negativo() {
		if (Funcoes.verificarImagem(imageView)) {
			Funcoes.atualizaImageView(imageView3, imageProcessService.negativo(imageView.getImage()));
		}
	}

	@FXML
	public void reduzirRuidoX() {
		if (Funcoes.verificarImagem(imageView)) {
			Funcoes.atualizaImageView(imageView3,
					imageProcessService.ruido(imageView.getImage(), EnumTipoVizinho.VIZINHO_X));
		}
	}

	@FXML
	public void reduzirRuidoCruz() {
		if (Funcoes.verificarImagem(imageView)) {
			Funcoes.atualizaImageView(imageView3,
					imageProcessService.ruido(imageView.getImage(), EnumTipoVizinho.VIZINHO_CRUZ));
		}
	}

	@FXML
	public void reduzirRuido3x3() {
		if (Funcoes.verificarImagem(imageView)) {
			Funcoes.atualizaImageView(imageView3,
					imageProcessService.ruido(imageView.getImage(), EnumTipoVizinho.VIZINHO_3X3));
		}
	}

	@FXML
	public void adicao() {
		if (Funcoes.verificarImagem(imageView) && Funcoes.verificarImagem(imageView2)) {
			var img1 = (slider1.getValue() / 100);
			var img2 = 1 - img1;
			Funcoes.atualizaImageView(imageView3,
					imageProcessService.adicao(imageView.getImage(), imageView2.getImage(), img1, img2));
		}
	}

	@FXML
	public void subtracao() {
		if (Funcoes.verificarImagem(imageView) && Funcoes.verificarImagem(imageView2)) {
			Funcoes.atualizaImageView(imageView3,
					imageProcessService.subtracao(imageView.getImage(), imageView2.getImage()));
		}
	}

	@FXML
	public void histograma(ActionEvent event) {
		if (Funcoes.verificarImagem(imageView)) {
			try {
				var loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("fxml/HistogramaModal.fxml"));

				var page = loader.load();
				var scene = new Scene((GridPane) page);
				var dialogStage = new Stage();

				dialogStage.setTitle("Histograma");
				dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
				dialogStage.setScene(scene);
				dialogStage.setMaximized(true);

				HistogramaModalController controller = loader.getController();
				controller.setDialogStage(dialogStage);

				imageProcessService.histograma(imageView.getImage(), controller.getHistoImageView(), false, true);
				imageProcessService.histograma(imageView.getImage(), controller.getHistoImageView3(), true, true);

				EnumTipoEqualizacao tipoEqualizacao = EnumTipoEqualizacao.NORMAL;

				if (eqValid.isSelected()) {
					tipoEqualizacao = EnumTipoEqualizacao.VALIDOS;
				} else if (eqCentralizada.isSelected()) {
					tipoEqualizacao = EnumTipoEqualizacao.CENTRALIZADA;
				}

				imageProcessService.histograma(
						imageProcessService.histogramaEqualizado(imageView.getImage(), tipoEqualizacao),
						controller.getHistoImageView2(), false, true);

				dialogStage.showAndWait();
			} catch (Exception e) {
				Funcoes.exibeMsgErro(e);
			}
		}
	}

	@FXML
	public void equalizar() {
		if (Funcoes.verificarImagem(imageView)) {
			EnumTipoEqualizacao tipoEqualizacao = EnumTipoEqualizacao.NORMAL;

			if (eqValid.isSelected()) {
				tipoEqualizacao = EnumTipoEqualizacao.VALIDOS;
			} else if (eqCentralizada.isSelected()) {
				tipoEqualizacao = EnumTipoEqualizacao.CENTRALIZADA;
			}

			Funcoes.atualizaImageView(imageView3,
					imageProcessService.histogramaEqualizado(imageView.getImage(), tipoEqualizacao));
		}
	}

	@FXML
	public void segmentacao() {
		if (Funcoes.verificarImagem(imageView)) {
			Funcoes.atualizaImageView(imageView3,
					imageProcessService.segmentacao(imageView.getImage(), (slider2.getValue() / 255)));
		}
	}

	@FXML
	public void pintaVerm() {
		if (Funcoes.verificarImagem(imageView)) {
			Funcoes.atualizaImageView(imageView3,
					imageProcessService.subsPreto(imageView.getImage()));
		}
	}

	/* ------------------------ DESAFIO 1 ----------------------- */

	@FXML
	private ImageView imageViewDesafio1;

	private Image imagemDesafio1;

	@FXML
	public void abreImagemDesafio1() {
		try {
			imagemDesafio1 = Funcoes.selecionaImagem();
			Funcoes.atualizaImageView(imageViewDesafio1, imagemDesafio1);
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}

	@FXML
	public void aplicarFiltroDesafio1() {
		if (Funcoes.verificarImagem(imageViewDesafio1)) {
			Funcoes.atualizaImageView(imageViewDesafio1, desafio1Service.execute(imageViewDesafio1.getImage()));
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
		try {
			if (Funcoes.verificarImagem(imageViewDesafio1)) {
				Funcoes.salvarImagem(imageViewDesafio1.getImage());
			}
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
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
		try {
			imagemDesafio2 = Funcoes.selecionaImagem();
			Funcoes.atualizaImageView(imageViewDesafio2, imagemDesafio2);
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}

	@FXML
	public void aplicarFiltroDesafio2() {
		if (Funcoes.verificarImagem(imageViewDesafio2)) {
			Funcoes.atualizaImageView(imageViewDesafio2,
					desafio2Service.executar(imageViewDesafio2.getImage(), xInicial, xFinal, yInicial, yFinal));
		}
	}

	@FXML
	public void removerFiltroDesafio2() {
		if (Funcoes.verificarImagem(imageViewDesafio2)) {
			xInicial = null;
			xFinal = null;
			yInicial = null;
			yFinal = null;
			Funcoes.atualizaImageView(imageViewDesafio2, imagemDesafio2);
		}
	}

	@FXML
	public void salvarImagemDesafio2() {
		try {
			if (Funcoes.verificarImagem(imageViewDesafio2)) {
				Funcoes.salvarImagem(imageViewDesafio2.getImage());
			}
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
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
			Funcoes.atualizaImageView(imageViewDesafio2,
					desafio2Service.borda(imageViewDesafio2.getImage(), xInicial, xFinal, yInicial, yFinal, 5));
		}
	}

	/* ------------------------ Prova Teste 1 ----------------------- */

	@FXML
	private ImageView imageViewProvaTeste1;

	@FXML
	private Slider sliderProvaTeste1Questao1;

	@FXML
	private ColorPicker colorPickerProvaTeste1Questao1;

	private Image imagemProvaTeste1;
	private Double provaTeste1Questao3XInicial;
	private Double provaTeste1Questao3XFinal;
	private Double provaTeste1Questao3YInicial;
	private Double provaTeste1Questao3YFinal;

	@FXML
	public void abreImagemProvaTeste1() {
		try {
			imagemProvaTeste1 = Funcoes.selecionaImagem();

			if (Funcoes.verificarImagem(imagemProvaTeste1)) {
				sliderProvaTeste1Questao1.setMax(imagemProvaTeste1.getWidth());
				Funcoes.atualizaImageView(imageViewProvaTeste1, imagemProvaTeste1);
			}
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}

	@FXML
	public void aplicarQuestao1ProvaTeste1() {
		if (Funcoes.verificarImagem(imageViewProvaTeste1)) {
			Funcoes.atualizaImageView(imageViewProvaTeste1, provaTeste1Service.questao1(imagemProvaTeste1,
					sliderProvaTeste1Questao1.getValue(), colorPickerProvaTeste1Questao1.getValue()));
		}
	}

	@FXML
	public void aplicarQuestao2ProvaTeste1() {
		if (Funcoes.verificarImagem(imageViewProvaTeste1)) {
			Funcoes.atualizaImageView(imageViewProvaTeste1, provaTeste1Service.questao2(imagemProvaTeste1));
		}
	}

	@FXML
	public void aplicarQuestao3ProvaTeste1() {
		if (Funcoes.verificarImagem(imageViewProvaTeste1)) {
			provaTeste1Service.questao3(imagemProvaTeste1, provaTeste1Questao3XInicial, provaTeste1Questao3XFinal,
					provaTeste1Questao3YInicial, provaTeste1Questao3YFinal);
		}
	}

	@FXML
	public void aplicarSegmentacaoProvaTeste1() {
		if (Funcoes.verificarImagem(imageViewProvaTeste1)) {
			Funcoes.atualizaImageView(imageViewProvaTeste1, provaTeste1Service.segmentacao(imagemProvaTeste1));
		}
	}

	@FXML
	public void removerFiltroProvaTeste1() {
		if (Funcoes.verificarImagem(imageViewProvaTeste1)) {
			provaTeste1Questao3XInicial = null;
			provaTeste1Questao3XFinal = null;
			provaTeste1Questao3YInicial = null;
			provaTeste1Questao3YFinal = null;
			Funcoes.atualizaImageView(imageViewProvaTeste1, imagemProvaTeste1);
		}
	}

	@FXML
	public void salvarImagemProvaTeste1() {
		try {
			if (Funcoes.verificarImagem(imageViewProvaTeste1)) {
				Funcoes.salvarImagem(imageViewProvaTeste1.getImage());
			}
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}

	@FXML
	public void pressedImagemProvaTeste1(MouseEvent evt) {
		provaTeste1Questao3XInicial = evt.getX();
		provaTeste1Questao3YInicial = evt.getY();
	}

	@FXML
	public void releasedImagemProvaTeste1(MouseEvent evt) {
		provaTeste1Questao3XFinal = evt.getX();
		provaTeste1Questao3YFinal = evt.getY();

		if (Funcoes.verificarImagem(imageViewProvaTeste1)) {
			Funcoes.atualizaImageView(imageViewProvaTeste1,
					imageProcessService.borda(imagemProvaTeste1, provaTeste1Questao3XInicial, provaTeste1Questao3XFinal,
							provaTeste1Questao3YInicial, provaTeste1Questao3YFinal, 1));
			aplicarQuestao3ProvaTeste1();
		}
	}

	/* ------------------------ Prova 1 ----------------------- */

	@FXML
	private ImageView imageViewProva1;

	@FXML
	private TextField inputTextProva1;

	private Image imagemProva1;
	private Double prova1Questao2XInicial;
	private Double prova1Questao2XFinal;
	private Double prova1Questao2YInicial;
	private Double prova1Questao2YFinal;

	@FXML
	public void abreImagemProva1() {
		try {
			imagemProva1 = Funcoes.selecionaImagem();

			if (Funcoes.verificarImagem(imagemProva1)) {
				Funcoes.atualizaImageView(imageViewProva1, imagemProva1);
			}

		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}

	@FXML
	public void aplicarQuestao1Prova1() {
		if (Funcoes.verificarImagem(imageViewProva1)) {
			try {

				if (Objects.isNull(inputTextProva1.getText()) || inputTextProva1.getText().isBlank()) {
					throw new RuntimeException("Insira a quantidade de colunas a ser dividida");
				}

				var quantidade = Integer.parseInt(inputTextProva1.getText());
				Funcoes.atualizaImageView(imageViewProva1, prova1Service.questao1(imagemProva1, quantidade));
			} catch (Exception e) {
				Funcoes.exibeMsgErro(e);
			}
		}
	}

	@FXML
	public void aplicarQuestao2Prova1() {
		if (Funcoes.verificarImagem(imageViewProva1)) {
			Funcoes.atualizaImageView(imageViewProva1, prova1Service.questao2(imagemProva1, prova1Questao2XInicial,
					prova1Questao2XFinal, prova1Questao2YInicial, prova1Questao2YFinal));
		}
	}

	@FXML
	public void aplicarQuestao3Prova1() {
		if (Funcoes.verificarImagem(imageViewProva1)) {
			prova1Service.questao3(imagemProva1);
		}
	}

	@FXML
	public void aplicarSegmentacaoProva1() {
		if (Funcoes.verificarImagem(imageViewProva1)) {
			Funcoes.atualizaImageView(imageViewProva1, prova1Service.segmentacao(imagemProva1));
		}
	}

	@FXML
	public void removerFiltroProva1() {
		if (Funcoes.verificarImagem(imageViewProva1)) {
			prova1Questao2XInicial = null;
			prova1Questao2XFinal = null;
			prova1Questao2YInicial = null;
			prova1Questao2YFinal = null;
			Funcoes.atualizaImageView(imageViewProva1, imagemProva1);
		}
	}

	@FXML
	public void salvarImagemProva1() {
		try {
			if (Funcoes.verificarImagem(imageViewProva1)) {
				Funcoes.salvarImagem(imageViewProva1.getImage());
			}
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}

	@FXML
	public void pressedImagemProva1(MouseEvent evt) {
		prova1Questao2XInicial = evt.getX();
		prova1Questao2YInicial = evt.getY();
	}

	@FXML
	public void releasedImagemProva1(MouseEvent evt) {
		prova1Questao2XFinal = evt.getX();
		prova1Questao2YFinal = evt.getY();

		if (Funcoes.verificarImagem(imageViewProva1)) {
			Funcoes.atualizaImageView(imageViewProva1, imageProcessService.borda(imagemProva1, prova1Questao2XInicial,
					prova1Questao2XFinal, prova1Questao2YInicial, prova1Questao2YFinal, 3));
			aplicarQuestao2Prova1();
		}
	}

	/**************** OPENCV **************/
	
	@FXML
	private ImageView imageViewOpenCV;
	
	@FXML
	private ImageView imageViewOpenCV2;
	
	@FXML
	private Slider sliderDilatacaoOpenCv;
	
	@FXML
	private Slider sliderErosaoOpenCv;
	
	@FXML
	private Slider sliderBordaOpenCv;
	
	@FXML
	private Slider sliderBordaSobelOpenCv;
	
	@FXML
	private Slider sliderBordaLaplacianOpenCv;
	
	@FXML
	private Slider sliderBordaBlurOpenCv;
	
	@FXML
	private Label lblSizeDilatacao;
	
	@FXML
	private Label lblSizeErosao;
	
	@FXML
	private Label lblSizeBordaCanny;
	
	@FXML
	private Label lblSizeBordaSobel;
	
	@FXML
	private Label lblSizeBordaLaplacian;
	
	@FXML
	private Label lblSizeBordaCannyBlur;
	
	@FXML
	private CheckBox checkUsaBlurCanny;
	
	@FXML
	private CheckBox checkUsaBlurSobel;
	
	@FXML
	private CheckBox checkUsaGraySobel;
	
	@FXML
	private CheckBox checkUsaBlurLaplacian;
	
	@FXML
	private CheckBox checkUsaGrayLaplacian;
	
	private File arquivoImagem;
	
	@FXML
	public void abreImagemOpenCv() {
		try {
			arquivoImagem = Funcoes.selecionaImagemFile();
			var imagemOpenCv = Objects.nonNull(arquivoImagem) ? new Image(arquivoImagem.toURI().toString()) : null;
			
			if (Funcoes.verificarImagem(imagemOpenCv)) {
				Funcoes.atualizaImageView(imageViewOpenCV, imagemOpenCv);
			}

		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}
	
	@FXML
	public void salvarImagemOpenCv() {
		try {
			if (Funcoes.verificarImagem(imageViewOpenCV2)) {
				Funcoes.salvarImagem(imageViewOpenCV2.getImage());
			}
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}

	@FXML
	public void removerFiltroOpenCv() {
		if (Funcoes.verificarImagem(imageViewOpenCV2)) {
			imageViewOpenCV2.setImage(null);
		}
	}
	
	@FXML
	public void aplicaFiltroDilatacaoOpenCv() {
		if (Funcoes.verificarImagem(imageViewOpenCV)) {
			Funcoes.atualizaImageView(imageViewOpenCV2, ServiceFactory.getOpenCvService().dilatacao(arquivoImagem, sliderDilatacaoOpenCv.getValue()));
		}
	}
	
	@FXML
	public void aplicaFiltroErosaoOpenCv() {
		if (Funcoes.verificarImagem(imageViewOpenCV)) {
			Funcoes.atualizaImageView(imageViewOpenCV2, ServiceFactory.getOpenCvService().erosao(arquivoImagem, sliderErosaoOpenCv.getValue()));
		}
	}
	
	@FXML
	public void aplicaFiltroBordaCannyOpenCv() {
		if (Funcoes.verificarImagem(imageViewOpenCV)) {
			Funcoes.atualizaImageView(imageViewOpenCV2, ServiceFactory.getOpenCvService().bordaCanny(arquivoImagem, sliderBordaOpenCv.getValue(), Double.valueOf(sliderBordaBlurOpenCv.getValue()).intValue(), checkUsaBlurCanny.isSelected()));
		}
	}
	
	@FXML
	public void aplicaFiltroBordaSobelOpenCv() {
		if (Funcoes.verificarImagem(imageViewOpenCV)) {
			Funcoes.atualizaImageView(imageViewOpenCV2, ServiceFactory.getOpenCvService().bordaSobel(arquivoImagem, sliderBordaSobelOpenCv.getValue(), Double.valueOf(sliderBordaBlurOpenCv.getValue()).intValue(), checkUsaBlurSobel.isSelected(), checkUsaGraySobel.isSelected()));
		}
	}
	
	@FXML
	public void aplicaFiltroBordaLaplacianOpenCv() {
		if (Funcoes.verificarImagem(imageViewOpenCV)) {
			Funcoes.atualizaImageView(imageViewOpenCV2, ServiceFactory.getOpenCvService().bordaLaplace(arquivoImagem, sliderBordaLaplacianOpenCv.getValue(), Double.valueOf(sliderBordaBlurOpenCv.getValue()).intValue(), checkUsaBlurLaplacian.isSelected(), checkUsaGrayLaplacian.isSelected()));
		}
	}

}
