package hamiguazzz.ChemicalSimulate.Log;

import hamiguazzz.ChemicalSimulate.Log.StandardLog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemLog implements StandardLog {

	public static SystemLog log = new SystemLog();
	private boolean debug = false;
	private String history = "";

	private SystemLog() {
	}

	private static String getSTDInfo(InformationType type) {
		Date dNow = new Date();
		SimpleDateFormat dt = new SimpleDateFormat("yy.MM.dd'|'HH:mm:ss");
		String t1 = "[";
		t1 += dt.format(dNow);
		t1 += "]";
		return t1 + type.name;
	}

	public String getHistory() {
		return history;
	}

	public String clear() {
		String tHistory = history;
		history = "";
		return tHistory;
	}

	@Override
	public boolean isDebugOn() {
		return debug;
	}

	@Override
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	@Override
	public StandardLog printDebugMessage(String message) {
		if (debug) {
			String tString = getSTDInfo(InformationType.debug) + message + '\n';
			this.history += tString;
			System.out.print(tString);
		}
		return this;
	}

	@Override
	public StandardLog printInfoMessage(String message) {
		String tString = getSTDInfo(InformationType.info) + message + '\n';
		this.history += tString;
		System.out.print(tString);
		return this;
	}

	@Override
	public StandardLog printErrorMessage(String message) {
		String tString = getSTDInfo(InformationType.error) + message + '\n';
		this.history += tString;
		System.err.print(tString);
		System.out.print(tString);
		return this;
	}

	private enum InformationType {
		info("[info]"), error("[error]"), debug("[debug]");
		final String name;

		InformationType(String name) {
			this.name = name;
		}
	}

}
