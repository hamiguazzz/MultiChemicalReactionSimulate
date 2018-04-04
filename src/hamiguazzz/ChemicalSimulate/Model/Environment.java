package hamiguazzz.ChemicalSimulate.Model;

import hamiguazzz.ChemicalSimulate.Log.Log;
import hamiguazzz.ChemicalSimulate.Log.TimeCounter;

import java.util.ArrayList;

public class Environment implements Runnable {

	public static int FPS = 20;
	public static int Multi = 10;
	private Container mainContainer;
	private final double dt;
	private ArrayList<Container> historyContainers = new ArrayList<>();
	private double endTime;
	private boolean isRunned = false;

	public Environment(Container mainContainer, double dt, double endTime) {
		this.mainContainer = mainContainer;
		this.dt = dt;
		this.endTime = endTime;
	}

	private static boolean isKeyTime(double time, double dt) {
		if (1 / dt <= FPS) return true;
		int cur = (int) (time / dt);
		int k = (int) (1.0/FPS/dt);
		return cur % k == 0;
	}

	private static boolean isSaveTime(double time, double dt) {
		if (1 / dt <= Multi * FPS) return true;
		int cur = (int) (time / dt);
		int k = (int) (1.0/(FPS * Multi)/dt);
		return cur % k == 0;
	}

	public ArrayList<Container> getKeys() {
		if (!isRunned)return null;
		ArrayList<Container> re = new ArrayList<>();
		for (Container c : historyContainers) {
			if (isKeyTime(c.getTime(), dt)) re.add(c.clone());
		}
		return re;
	}

	public ArrayList<Container> getHistory() {
		if (!isRunned)return null;
		return historyContainers;
	}

	public Container getMainContainer() {
		return mainContainer;
	}

	public double getCurrentTime() {
		return mainContainer.getTime();
	}

	private void save(Container container) {
		if (isSaveTime(container.getTime(), this.dt))
			historyContainers.add(container.takePic());
	}

	public void setEndTime(double endTime) {
		this.endTime = endTime > 0 ? endTime : 0;
		this.clear();
		this.run();
	}

	private void clear() {
		if (historyContainers.size() > 0)
			this.mainContainer = historyContainers.get(0);
		this.historyContainers = new ArrayList<>();
		isRunned = false;
	}


	@Override
	public void run() {
		if (isRunned)return;
		reRun();
	}

	public void reRun(){
		TimeCounter counter = new TimeCounter();
		counter.start();
		Log.log.printInfoMessage("Run Start:End time is " + endTime);
		for (; getCurrentTime() < endTime + dt; mainContainer.next(dt)) {
			save(mainContainer);
		}
		counter.end();
		Log.log.printInfoMessage("Run End:Calculate using time:" + counter);
		isRunned = true;
	}

}
