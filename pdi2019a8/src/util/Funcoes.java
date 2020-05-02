package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class Funcoes {
	public static final String CAMINHO_SALVAR = "C:";

	public static void salvarImagem(Image imagem) {
		if (Objects.isNull(imagem)) {
			exibeMsg("Salvar imagem", "Não é possível salvar a imagem.", "Não há nenhuma imagem modificada", AlertType.ERROR);
			return;
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG (.png)", "*.png"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GIF (.gif)", "*.gif"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BITMAP (.bmp)", "*.bmp"));
		fileChooser.setInitialDirectory(new File(CAMINHO_SALVAR));

		File file = fileChooser.showSaveDialog(null);

		if (Objects.nonNull(file)) {
			BufferedImage bImg = SwingFXUtils.fromFXImage(imagem, null);

			try {
				ImageIO.write(bImg, fileChooser.getSelectedExtensionFilter().getExtensions().get(0).replace("*.", ""), file);
			} catch (IOException e) {
			}

			exibeMsg("Informação", "Salvar imagem", "Imagem salva com sucesso", AlertType.INFORMATION);
		}
	}

	public static Image selecionaImagem() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens (.jpg, .jpeg, .png, .gif, .bmp)", "*.jpg", "*.JPG", "*.png", "*.PNG", "*.gif", "*.GIF", "*.bmp", "*.BMP", "*.jpeg", "*.JPEG"));
		fileChooser.setInitialDirectory(new File(CAMINHO_SALVAR));

		File imgSelec = fileChooser.showOpenDialog(null);

		if (Objects.nonNull(imgSelec)) {
			return new Image(imgSelec.toURI().toString());
		}

		throw new RuntimeException("Erro ao selecionar imagem");
	}

	public static void atualizaImageView(ImageView imgView, Image img) {
		if (Objects.nonNull(img)) {
			imgView.setImage(img);
			imgView.setFitWidth(img.getWidth());
			imgView.setFitHeight(img.getHeight());
		} else {
			exibeMsg("Erro", "Erro ao atualizar", "Nenhuma imagem selecionada", AlertType.ERROR);
		}
	}

	public static void verificaCor(Image img, int x, int y, Label lblR, Label lblG, Label lblB, Pane pnlCor) {
		int yy = y - 1;
		int xx = y - 1;

		if (yy >= 0 && xx >= 0) {
			Color cor = img.getPixelReader().getColor(xx, yy);

			Double red = cor.getRed() * 255;
			Double green = cor.getGreen() * 255;
			Double blue = cor.getBlue() * 255;

			lblR.setText(String.valueOf(red.intValue()));
			lblG.setText(String.valueOf(green.intValue()));
			lblB.setText(String.valueOf(blue.intValue()));

			pnlCor.setStyle("-fx-background-color: RGB(" + red.intValue() + "," + green.intValue() + "," + blue.intValue() + ")");
		}
	}

	public static boolean verificarImagem(ImageView imageView) {
		if (Objects.isNull(imageView.getImage())) {
			exibeMsg("Erro!", "Erro ao atualizar imagem", "Nenhuma imagem selecionada!", AlertType.ERROR);
			return false;
		}

		return true;
	}

	public static void exibeMsg(String titulo, String cabecalho, String msg, AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(msg);
		alert.showAndWait();
	}
}
