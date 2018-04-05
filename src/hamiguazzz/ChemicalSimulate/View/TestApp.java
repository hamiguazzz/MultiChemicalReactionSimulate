package hamiguazzz.ChemicalSimulate.View;

import hamiguazzz.ChemicalSimulate.Model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.Test;

import java.util.ArrayList;

public class TestApp extends Application {
	Environment environment;
	Reaction reaction;
	private ProgressBar pb;
	private BorderPane root;

	@Override
	public void start(Stage stage) {
		initEnvironment();

		root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Progress Controls");

		pb = new ProgressBar(0);

		root.setBottom(pb);
		scene.setRoot(root);

		Thread calThread = new Thread(environment);
		calThread.start();

		root.setPrefWidth(800);
		root.setPrefHeight(600);

		stage.show();
	}

	private void initEnvironment() {
		ArrayList<Double> chs = new ArrayList<>();
		chs.add(3.0);
		chs.add(2.0);
		chs.add(0.5);
		chs.add(0.5);

		ArrayList<Substance> substances = new ArrayList<>();
		substances.add(new Substance("X",3));
		substances.add(new Substance("Y",2));
		substances.add(new Substance("Z"));
		substances.add(new Substance("Z"));

		double kp = 1,kn = 4;
		Container container = new Container();
		reaction = new FourSTDReaction(container,substances,chs,kp,kn);
		reaction.increaseRate(0.001);
		container.setVolume(1);container.addReaction(reaction);

		environment = new Environment(container,"0.00001","20"){
			@Override
			public synchronized void update() {
				super.update();
				if (getCurrentTime().doubleValue() == 5.0)getMainContainer().setVolume(0.5);
				if (getCurrentTime().doubleValue() == 2.0)reaction.increaseRate(5);
				if (getCurrentTime().doubleValue() == 10.0)getMainContainer().find("X").add(3);
				pb.setProgress(getCurrentTime().divide(getEndTime()).doubleValue());
			}
			@Override
			public void afterRun() {
				afterCalc();
			}
		};
	}

	private void afterCalc() {
		pb.setVisible(false);

		System.out.println(environment.getKeys().size());
		EnvironmentAdapter adapter = new EnvironmentAdapter(environment);
		Platform.runLater(() -> root.setCenter(adapter.getChart("a", "")));
	}


	public static void main(String[] args) {
		launch(args);
	}

}
