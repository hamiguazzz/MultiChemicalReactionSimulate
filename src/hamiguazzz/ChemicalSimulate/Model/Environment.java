package hamiguazzz.ChemicalSimulate.Model;

import hamiguazzz.ChemicalSimulate.Log.Log;
import hamiguazzz.ChemicalSimulate.Log.TimeCounter;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Environment implements Runnable {

	public static BigDecimal FPS = new BigDecimal("100");
	public static BigDecimal Multi = new BigDecimal("10");
	private Container mainContainer;
	private final BigDecimal dt;
	private ArrayList<Container> historyContainers = new ArrayList<>();
	private BigDecimal endTime;
	private boolean isRunned = false;

	public Environment(Container mainContainer, String dt, String endTime) {
		this.mainContainer = mainContainer;
		this.dt = new BigDecimal(dt);
		this.endTime = new BigDecimal(endTime);
	}

	private static boolean isKeyTime(BigDecimal time, BigDecimal dt) {
		if (FPS.multiply(dt).doubleValue()>=1.0) return true;
		int cur = time.divide(dt).toBigInteger().intValue();
		int k = new BigDecimal(1).divide(FPS).divide(dt).toBigInteger().intValue();
		return cur % k == 0;
	}

	private static boolean isSaveTime(BigDecimal time, BigDecimal dt) {
		if (FPS.multiply(Multi).multiply(dt).doubleValue()>=1.0) return true;
		int cur = time.divide(dt).toBigInteger().intValue();
		int k = new BigDecimal(1).divide(FPS.multiply(Multi)).divide(dt).toBigInteger().intValue();
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

	public BigDecimal getCurrentTime() {
		return mainContainer.getTime();
	}

	private void save(Container container) {
		if (isSaveTime(container.getTime(), this.dt))
			historyContainers.add(container.takePic());
	}

	public void setEndTime(String endTime) {
		BigDecimal end = new BigDecimal(endTime);
		this.endTime = end.doubleValue()>0.0 ? end : new BigDecimal("0");
		this.clear();
		this.reRun();
		this.isRunned = true;
	}

	public BigDecimal getEndTime() {
		return endTime;
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
		afterRun();
	}

	protected void afterRun() {
	}

	private synchronized void reRun(){
		TimeCounter counter = new TimeCounter();
		counter.start();
		Log.log.printInfoMessage("Run Start:End time is " + endTime+"s");
		for (; getCurrentTime().compareTo(endTime)<1; mainContainer.next(dt)) {
			update();
		}
		counter.end();
		Log.log.printInfoMessage("Run End:Calculate used time:" + counter);
		isRunned = true;
	}

	protected synchronized void update(){
		save(mainContainer);
	}

}
