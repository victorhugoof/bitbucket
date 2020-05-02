package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.stage.Stage;

public class HistogramaModalController {
	private Stage dialogStage;

	@FXML
	private BarChart<String, Number> histoImageView;
	
	@FXML
	private BarChart<String, Number> histoImageView2;
	
	@FXML
	private BarChart<String, Number> histoImageView3;
	
	
	public Stage getDialogStage() {
		return dialogStage;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public BarChart<String, Number> getHistoImageView() {
		return histoImageView;
	}

	public void setHistoImageView(BarChart<String, Number> histoImageView) {
		this.histoImageView = histoImageView;
	}

	public BarChart<String, Number> getHistoImageView2() {
		return histoImageView2;
	}

	public void setHistoImageView2(BarChart<String, Number> histoImageView2) {
		this.histoImageView2 = histoImageView2;
	}

	public BarChart<String, Number> getHistoImageView3() {
		return histoImageView3;
	}

	public void setHistoImageView3(BarChart<String, Number> histoImageView3) {
		this.histoImageView3 = histoImageView3;
	}
	
}
