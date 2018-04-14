package hamiguazzz.ChemicalSimulate.View;

import hamiguazzz.ChemicalSimulate.Model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TestApp extends Application {
	Environment environment;
	Reaction reaction;
	Reaction reaction2;
	private ProgressBar pb;

	@Override
	public void start(Stage stage) {
		initEnvironment();

		BorderPane root = new BorderPane();
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
		Container container = new Container();container.setVolume(1);

		ArrayList<Double> chs1 = new ArrayList<>();
		chs1.add(1.0);
		chs1.add(1.0);
		chs1.add(0.5);
		chs1.add(0.5);

		ArrayList<Substance> substances1 = new ArrayList<>();
		substances1.add(new Substance("X",6));
		substances1.add(new Substance("Y",4));
		substances1.add(new Substance("Z"));
		substances1.add(new Substance("Z"));

		reaction = new FourSTDReaction(container,substances1,chs1,0.005,0.005);
		reaction.increaseRate(1);
		container.addReaction(reaction);

		ArrayList<Double> chs2 = new ArrayList<>();
		chs2.add(1.0);
		chs2.add(2.0);
		chs2.add(0.5);
		chs2.add(0.5);

		ArrayList<Substance> substances2 = new ArrayList<>();
		substances2.add(new Substance("X"));
		substances2.add(new Substance("Y"));
		substances2.add(new Substance("W"));
		substances2.add(new Substance("W"));

		reaction2 = new FourSTDReaction(container,substances2,chs2,0.005,0.005);
		reaction2.increaseRate(1);
		container.addReaction(reaction2);

		Environment.FPS = new BigDecimal("5");



		environment = new Environment(container,"0.0001","1.0"){
			@Override
			public synchronized void update() {
				super.update();
//				if (getCurrentTime().doubleValue() == 5.0)getMainContainer().setVolume(0.5);
//				if (getCurrentTime().doubleValue() == 2.0)reaction.increaseRate(5);
//				if (getCurrentTime().doubleValue() == 10.0)getMainContainer().find("X").add(3);
				pb.setProgress(getCurrentTime().divide(getEndTime()).doubleValue());
			}
			@Override
			public void afterRun() {
				afterCalc();
			}
		};
	}

	private void afterCalc() {
		pb.setVisible(true);
		ArrayList<Container> history = environment.getHistory();
		System.out.println(history.get(history.size()-1).find("X"));
		System.out.println(history.get(history.size()-1).find("Y"));
		System.out.println(history.get(history.size()-1).find("Z"));
		System.out.println(history.get(history.size()-1).find("W"));
		//EnvironmentAdapter adapter = new EnvironmentAdapter(environment);
		//Platform.runLater(() -> root.setCenter(adapter.getChart("X+2Y=Z,X+2Y=W+Q", "")));
	}


	public static void main(String[] args) {
		launch(args);
	}

}
