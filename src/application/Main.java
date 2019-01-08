package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader fxLoader= new FXMLLoader(getClass().getResource("Sample.fxml"));
			AnchorPane root = (AnchorPane)fxLoader.load();
			Scene scene = new Scene(root,512,512);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setTitle("テトリス");
			primaryStage.show();

			SampleController controller = fxLoader.getController();

			scene.setOnKeyPressed(controller::onKeyPressed);
			scene.setOnKeyReleased(controller::onKeyReleased);

			//controller.start();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
