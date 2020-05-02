package controller;

import java.io.File;
import java.util.Objects;

import javax.imageio.ImageIO;

import enuns.EnumTipoPixel;
import enuns.EnumTipoVizinho;
import factory.ServiceFactory;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import service.ImageProcessService;
import service.OpenCvService;
import util.Funcoes;

public class ArtigoController extends SegundaChamadaController {
	
	@FXML
	private ImageView imageViewArtigo;
	
	@FXML
	private ImageView imageViewArtigo2;
	
	protected File arquivoImagem;
	private Image imagem;
	private int countSaves = 0;
	
	private final ImageProcessService servico = ServiceFactory.getImageProcessService();
	private final OpenCvService servicoCv = ServiceFactory.getOpenCvService();

	public void initialize() {
		super.initialize();
	}
	
	@FXML
	public void abreImagemArtigo() {
		try {
//			arquivoImagem = Funcoes.selecionaImagemFile();
			var imagemArtigo = Objects.nonNull(arquivoImagem) ? new Image(arquivoImagem.toURI().toString()) : null;
			
			if (Funcoes.verificarImagem(imagemArtigo)) {
				Funcoes.atualizaImageView(imageViewArtigo, imagemArtigo);
			}

		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}
	
	@FXML
	public void salvarImagemArtigo() {
		try {
			if (Funcoes.verificarImagem(imageViewArtigo2)) {
				Funcoes.salvarImagem(imageViewArtigo2.getImage());
			}
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}

	@FXML
	public void removerFiltroArtigo() {
		if (Funcoes.verificarImagem(imageViewArtigo2)) {
			imageViewArtigo2.setImage(null);
		}
	}

	@FXML
	public void executarArtigo() {
		if (Funcoes.verificarImagem(imageViewArtigo)) {
			imagem = servicoCv.escalaCinza(arquivoImagem, EnumTipoPixel.RED);
			atualizaImagem(); // 1
			
			imagem = servico.subtracao(imagem, servicoCv.escalaCinza(arquivoImagem, EnumTipoPixel.GREEN)); // 1 SUBTRAI IMAGEM RED DA IMAGEM GREEN
			atualizaImagem(); // 2
			
			imagem = servico.limiarizacao(imagem, (22.3D / 255));
			atualizaImagem(); // 3
			
			imagem = servico.ruido(imagem, EnumTipoVizinho.VIZINHO_3X3);
			atualizaImagem(); // 4
			
			imagem = servico.ruido(imagem, EnumTipoVizinho.VIZINHO_CRUZ);
			atualizaImagem(); // 5
			
			imagem = servico.ruido(imagem, EnumTipoVizinho.VIZINHO_X);
			atualizaImagem(); // 6
			
			imagem = servico.adicao(imagem, new Image(arquivoImagem.toURI().toString()), 0D, 1D);
			atualizaImagem(); // 7
			
			imagem = servico.ruido(imagem, EnumTipoVizinho.VIZINHO_3X3);
			atualizaImagem(); // 8
			
			imagem = servico.ruido(imagem, EnumTipoVizinho.VIZINHO_CRUZ);
			atualizaImagem(); // 9
			
			imagem = servico.ruido(imagem, EnumTipoVizinho.VIZINHO_X);
			atualizaImagem(); // 10
			
//			imagem = servico.escalaDeCinza(imagem);
//			atualizaImagem(); // 11
//			
//			imagem = servico.limiarizacao(imagem, (115D / 255));
//			atualizaImagem(); // 12
			
			Funcoes.atualizaImageView(imageViewArtigo2, imagem);
		}
	}
	
	private void atualizaImagem() {
		
		try {
			countSaves++;
			
			var file = new File(arquivoImagem.getPath() + countSaves + ".png");
			var bImg = SwingFXUtils.fromFXImage(imagem, null);
			ImageIO.write(bImg, "png", file);
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
	}
}
