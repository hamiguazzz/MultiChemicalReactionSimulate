package hamiguazzz.ChemicalSimulate.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{

	private BorderPane root;

	@Override
	public void start(Stage primaryStage){
		root = new BorderPane();
		primaryStage.setTitle("Chemical Simulate");
		primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();
	}



}
