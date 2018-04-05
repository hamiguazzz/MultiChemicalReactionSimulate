package hamiguazzz.ChemicalSimulate.Model;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class EnvironmentAdapter {
	Environment environment;
	ArrayList<Container> keys;

	public EnvironmentAdapter(Environment environment) {
		this.environment = environment;
	}

	public static XYChart getChart(String title) {
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setTitle(title);
		return lineChart;
	}

	public XYChart.Series<Number, Number> getSeries(String name) {
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName(name + " amount");
		ArrayList<Container> keys = getKeys();
		keys.forEach(container -> series.getData().add(
				new XYChart.Data<Number, Number>(
						container.getTime().doubleValue(),
						container.find(name).mol)));
		return series;
	}

	public XYChart getChart(String title, String name) {
		LineChart<Number, Number> re = (LineChart<Number, Number>) getChart(title);
		if (name != null && !name.equals("")) re.getData().add(0, getSeries(name));
		else {
			ArrayList<XYChart.Series<Number, Number>> list = new ArrayList<>();
			for (String sn : environment.getMainContainer().getNamePool())
				list.add(getSeries(sn));
			re.getData().addAll(list);
		}
		return re;
	}

	public ArrayList<Container> getKeys() {
		if (keys != null) {
			return keys;
		}
		update();
		return keys;
	}

	public void update() {
		keys = environment.getKeys();
	}
}
