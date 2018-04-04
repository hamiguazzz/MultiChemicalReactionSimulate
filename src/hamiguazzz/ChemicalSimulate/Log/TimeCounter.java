package hamiguazzz.ChemicalSimulate.Log;

public class TimeCounter {
	private long start = 0;
	private long end = 0;

	public TimeCounter() {
	}

	public TimeCounter(Object tag) {
		start = System.nanoTime();
	}

	public void start() {
		start = System.nanoTime();
	}

	public void end() {
		end = System.nanoTime();
	}

	public long time() {
		if (end == 0) {
			end();
		}
		return end - start;
	}

	@Override
	public String toString() {
		String rString = "" + (time() / 1000000) + "ms";
		start = 0;
		end = 0;
		return rString;
	}
}
