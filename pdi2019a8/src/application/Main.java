package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("Principal.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setMinHeight(900);
			primaryStage.setMinWidth(900);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Editor de Imagens");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
