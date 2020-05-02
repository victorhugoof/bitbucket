package controller;

import java.io.File;

import javax.imageio.ImageIO;

import factory.ServiceFactory;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import service.ImageProcessService;
import service.OpenCvService;
import service.SegundaChamadaService;
import util.Funcoes;

public class SegundaChamadaController {
	
	@FXML
	private ImageView imageViewSegundaChamada;
	
	@FXML
	private ImageView imageViewSegundaChamada2;

	@FXML
	private Slider sliderSegundaChamadaQuestao1;

	@FXML
	private ColorPicker colorPickerSegundaChamadaQuestao1;
	
	private File arquivoImagemSegundaChamada;
	private Image imagemSegundaChamada;
	private Double questao3XInicial;
	private Double questao3XFinal;
	private Double questao3YInicial;
	private Double questao3YFinal;
	
	private final ImageProcessService servico = ServiceFactory.getImageProcessService();
	private final OpenCvService servicoCv = ServiceFactory.getOpenCvService();
	private final SegundaChamadaService service = ServiceFactory.getSegundaChamadaService();

	public void initialize() {
		sliderSegundaChamadaQuestao1.valueProperty().addListener((observable, oldValue, newValue) -> questao1SegundaChamada());
	}
	
	@FXML
	public void abreImagemSegundaChamada() {
		try {
			arquivoImagemSegundaChamada = Funcoes.selecionaImagemFile();
			imagemSegundaChamada = new Image(arquivoImagemSegundaChamada.toURI().toString());
			
			if (Funcoes.verificarImagem(imagemSegundaChamada)) {
				Funcoes.atualizaImageView(imageViewSegundaChamada, imagemSegundaChamada);
			}

		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}
	
	@FXML
	public void salvarImagemSegundaChamada() {
		try {
			if (Funcoes.verificarImagem(imageViewSegundaChamada2)) {
				Funcoes.salvarImagem(imageViewSegundaChamada2.getImage());
			}
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}

	@FXML
	public void removerFiltroSegundaChamada() {
		if (Funcoes.verificarImagem(imageViewSegundaChamada2)) {
			imageViewSegundaChamada2.setImage(null);
		}
	}

	public void questao1SegundaChamada() {
		if (Funcoes.verificarImagem(imageViewSegundaChamada)) {
			try {

				Funcoes.atualizaImageView(imageViewSegundaChamada2, service.questao1(imageViewSegundaChamada.getImage(), sliderSegundaChamadaQuestao1.getValue(), colorPickerSegundaChamadaQuestao1.getValue()));
			} catch (Exception e) {
				Funcoes.exibeMsgErro(e);
			}
		}
	}

	@FXML
	public void questao3SegundaChamada() {
		if (Funcoes.verificarImagem(imagemSegundaChamada)) {
			service.questao3(imagemSegundaChamada, questao3XInicial, questao3XFinal,
					questao3YInicial, questao3YFinal);
		}
	}

	@FXML
	public void pressedImagemSegundaChamada(MouseEvent evt) {
		questao3XInicial = evt.getX();
		questao3YInicial = evt.getY();
	}

	@FXML
	public void releasedImagemSegundaChamada(MouseEvent evt) {
		questao3XFinal = evt.getX();
		questao3YFinal = evt.getY();

		if (Funcoes.verificarImagem(imageViewSegundaChamada)) {
			Funcoes.atualizaImageView(imageViewSegundaChamada,
					servico.borda(imageViewSegundaChamada.getImage(), questao3XInicial, questao3XFinal,
							questao3YInicial, questao3YFinal, 1));
			questao3SegundaChamada();
		}
	}
	
	@FXML
	public void questao2SegundaChamada() {
		if (Funcoes.verificarImagem(imageViewSegundaChamada)) {
			atualizaImagem(servico.subsPreto(imagemSegundaChamada));
			imagemSegundaChamada = servicoCv.bordaCanny(arquivoImagemSegundaChamada, 1D, 3, true);
			imagemSegundaChamada = servico.negativo(imagemSegundaChamada);
			Funcoes.atualizaImageView(imageViewSegundaChamada2, imagemSegundaChamada);
			service.questao2(imagemSegundaChamada);
		}
	}
	
	private void atualizaImagem(Image imagem) {
		
		try {
			imagemSegundaChamada = imagem;
			var file = new File(arquivoImagemSegundaChamada.getPath() + ".png");
			var bImg = SwingFXUtils.fromFXImage(imagem, null);
			ImageIO.write(bImg, "png", file);
			
			arquivoImagemSegundaChamada = file;
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}
	
}
