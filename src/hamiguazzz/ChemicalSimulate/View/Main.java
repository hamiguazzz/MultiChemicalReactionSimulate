package hamiguazzz.ChemicalSimulate.View;

import hamiguazzz.ChemicalSimulate.Model.Environment;
import hamiguazzz.ChemicalSimulate.Model.EnvironmentAdapter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{

	 ProgressBar pb;
	 BorderPane root;
	 AnchorPane createPane;

	 private CreateCtrler createCtrler;

	private Environment environment;

	@Override
	public void start(Stage primaryStage){
		pb = new ProgressBar(0);
		primaryStage.setTitle("Chemical Simulate");
		try {
			var loader = new FXMLLoader(getClass().getResource("CreateLayout.fxml"));
			createPane = loader.load();
			createCtrler = loader.getController();
			createCtrler.setMain(this);
			loader = new FXMLLoader(getClass().getResource("MainLayout.fxml"));
			root = loader.load();
			MainCtrler mainCtrler = loader.getController();
			mainCtrler.setMain(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		root.setCenter(createPane);
		primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();
	}


	void afterCalc() {
		pb.setVisible(false);
		EnvironmentAdapter adapter = new EnvironmentAdapter(environment);
		Platform.runLater(() -> root.setCenter(adapter.getChart("", "")));
	}


	void call() {
		root.setBottom(pb);
		environment = createCtrler.getEnvironment();
		Thread callThread = new Thread(environment);
		callThread.start();
	}

	public Environment getEnvironment() {
		return environment;
	}
}
