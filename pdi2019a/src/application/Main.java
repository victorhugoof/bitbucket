package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			TabPane root = FXMLLoader.load(getClass().getResource("../controller/fxml/Principal.fxml"));
			var scene = new Scene(root);

			primaryStage.setMinHeight(900);
			primaryStage.setMinWidth(900);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Editor de Imagens");
			primaryStage.setMaximized(true);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
