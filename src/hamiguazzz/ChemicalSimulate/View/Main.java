package hamiguazzz.ChemicalSimulate.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{

	private BorderPane root;

	private AnchorPane createPane;

	@Override
	public void start(Stage primaryStage){
		root = new BorderPane();
		primaryStage.setTitle("Chemical Simulate");
		try {
			createPane = FXMLLoader.load(getClass().getResource("CreateLayout.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		root.setCenter(createPane);
		primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();
	}



}
