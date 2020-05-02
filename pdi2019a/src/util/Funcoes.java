package util;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import javax.imageio.ImageIO;

import enuns.EnumTipoPixel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class Funcoes extends FuncoesBD {
	
	public static void salvarImagem(Image imagem) throws SQLException, IOException {
		if (Objects.isNull(imagem)) {
			exibeMsg("Salvar imagem", "Não é possível salvar a imagem.", "Não há nenhuma imagem modificada",
					AlertType.ERROR);
			return;
		}

		var fileChooser = new FileChooser();
		var file = new File(listarCaminhoPadrao());
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG (.png)", "*.png"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GIF (.gif)", "*.gif"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BITMAP (.bmp)", "*.bmp"));

		if (!file.exists()) {
			atualizaCaminho(CAMINHO_PADRAO);
			file = new File(listarCaminhoPadrao());
		}
		
		fileChooser.setInitialDirectory(file);
		var imgSelec = fileChooser.showSaveDialog(null);

		if (Objects.nonNull(imgSelec)) {
			var bImg = SwingFXUtils.fromFXImage(imagem, null);

			ImageIO.write(bImg, fileChooser.getSelectedExtensionFilter().getExtensions().get(0).replace("*.", ""), imgSelec);
			atualizaCaminho(imgSelec.getParentFile().getPath());
			exibeMsg("Informação", "Salvar imagem", "Imagem salva com sucesso", AlertType.INFORMATION);
		}
	}

	public static Image selecionaImagem() throws SQLException {
		var fileChooser = new FileChooser();
		var file = new File(listarCaminhoPadrao());
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens (.jpg, .jpeg, .png, .gif, .bmp)",
				"*.jpg", "*.JPG", "*.png", "*.PNG", "*.gif", "*.GIF", "*.bmp", "*.BMP", "*.jpeg", "*.JPEG"));
		
		if (!file.exists()) {
			atualizaCaminho(CAMINHO_PADRAO);
			file = new File(listarCaminhoPadrao());
		}
		
		fileChooser.setInitialDirectory(file);
		var imgSelec = fileChooser.showOpenDialog(null);

		if (Objects.nonNull(imgSelec)) {
			atualizaCaminho(imgSelec.getParentFile().getPath());
			return new Image(imgSelec.toURI().toString());
		}

		return null;
	}

	public static File selecionaImagemFile() throws SQLException {
		var fileChooser = new FileChooser();
		var file = new File(listarCaminhoPadrao());
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens (.jpg, .jpeg, .png, .gif, .bmp)",
				"*.jpg", "*.JPG", "*.png", "*.PNG", "*.gif", "*.GIF", "*.bmp", "*.BMP", "*.jpeg", "*.JPEG"));
		
		if (!file.exists()) {
			atualizaCaminho(CAMINHO_PADRAO);
			file = new File(listarCaminhoPadrao());
		}
		
		fileChooser.setInitialDirectory(file);
		var imgSelec = fileChooser.showOpenDialog(null);

		if (Objects.nonNull(imgSelec)) {
			atualizaCaminho(imgSelec.getParentFile().getPath());
			return imgSelec;
		}

		return null;
	}

	public static void atualizaImageView(ImageView imgView, Image img) {
		if (Funcoes.verificarImagem(img)) {
			imgView.setImage(img);
			imgView.setFitWidth(img.getWidth());
			imgView.setFitHeight(img.getHeight());
		}
	}

	public static void verificaCor(Image img, int x, int y, Label lblR, Label lblG, Label lblB, Pane pnlCor) {
		var yy = y - 1;
		var xx = x - 1;

		if (yy >= 0 && xx >= 0) {
			var cor = img.getPixelReader().getColor(xx, yy);

			Double red = cor.getRed() * 255;
			Double green = cor.getGreen() * 255;
			Double blue = cor.getBlue() * 255;

			lblR.setText("Red: " + red.intValue());
			lblG.setText("Green: " + green.intValue());
			lblB.setText("Blue: " + blue.intValue());

			pnlCor.setStyle("-fx-background-color: RGB(" + red.intValue() + "," + green.intValue() + ","
					+ blue.intValue() + ")");
		}
	}

	public static boolean verificarImagem(ImageView imageView) {
		return verificarImagem(imageView.getImage());
	}

	public static boolean verificarImagem(Image imagem) {
		if (Objects.isNull(imagem)) {
			exibeMsg("Erro!", "Erro ao atualizar imagem", "Nenhuma imagem selecionada!", AlertType.ERROR);
			return false;
		}

		return true;
	}

	public static void exibeMsg(String titulo, String cabecalho, String msg, AlertType tipo) {
		var alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	public static void exibeMsgErro(Exception e) {
		Funcoes.exibeMsg("Erro",
				e.getMessage().substring(0, 1).toUpperCase() + e.getMessage().substring(1, e.getMessage().length()),
				e.getLocalizedMessage(), AlertType.ERROR);
		e.printStackTrace();
	}
	
	public static void exibeMsgErro(String msg, Exception e) {
		Funcoes.exibeMsg("Erro",
				e.getMessage().substring(0, 1).toUpperCase() + e.getMessage().substring(1, e.getMessage().length()),
				msg, AlertType.ERROR);
		e.printStackTrace();
	}

	public static int[] getQuantidadeCores(Image image, EnumTipoPixel tipo, boolean acumulado) {
		int[] lista = new int[256];
		int w = (int) image.getWidth();
		int h = (int) image.getHeight();

		var pr = image.getPixelReader();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {

				var color = pr.getColor(i, j);
				Double cor = null;

				if (tipo.equals(EnumTipoPixel.RED)) {
					cor = color.getRed() * 255;

				} else if (tipo.equals(EnumTipoPixel.GREEN)) {
					cor = color.getGreen() * 255;

				} else {
					cor = color.getBlue() * 255;
				}

				cor = cor > 255 ? 255 : cor;
				lista[cor.intValue()]++;

			}
		}

		if (acumulado) {
			return acumulaArray(lista);
		}

		return lista;

	}

	public static int[] uneArrayCores(int[] red, int[] green, int[] blue) {
		int[] lista = new int[256];

		for (int i = 0; i < 256; i++) {
			lista[i] = lista[i] + red[i] + green[i] + blue[i];
		}

		return lista;
	}

	@SuppressWarnings("unchecked")
	public static boolean insereBarChart(BarChart<String, Number> barChart, int[] lista) {
		int i = 0;
		var valores = new XYChart.Series<String, Number>();

		for (int vlr : lista) {
			valores.getData().add(new XYChart.Data<String, Number>(String.valueOf(i++), vlr));
		}

		return barChart.getData().addAll(valores);
	}

	public static int[] acumulaArray(int[] array) {
		var lista = new int[256];
		int qtd = 0;
		int i = 0;

		for (int vlr : array) {
			qtd = qtd + vlr;
			lista[i] = qtd;

			i++;
		}

		return lista;
	}

	public static int tonsValidos(int[] lista) {
		int tons = 0;

		for (int i = 0; i < lista.length; i++) {
			if (lista[i] > 0) {
				tons++;
			}
		}

		return tons;
	}

	public static int primeiroPixelValido(int[] lista) {
		for (int i = 0; i < lista.length; i++) {
			if (lista[i] > 0) {
				return i;
			}
		}

		return 0;
	}
}
