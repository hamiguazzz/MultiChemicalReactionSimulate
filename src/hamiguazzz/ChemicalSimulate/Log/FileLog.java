package hamiguazzz.ChemicalSimulate.Log;

import hamiguazzz.ChemicalSimulate.Log.StandardLog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLog implements StandardLog {
	private File ioFile;
	private FileWriter writer;
	private boolean debug = false;

	public FileLog(String filename) {
		this(new File(filename));
	}

	public FileLog(File file) {
		Initialization(file);
	}

	private static String getSTDInfo(InformationType type) {
		Date dNow = new Date();
		SimpleDateFormat dt = new SimpleDateFormat("yy.MM.dd'|'hh:mm:ss");
		String t1 = "[";
		t1 += dt.format(dNow);
		t1 += "]";
		return t1 + type.name;
	}

	private boolean Initialization(File file) {
		this.ioFile = file;
		try {
			writer = new FileWriter(file);
		} catch (IOException e) {
			SystemLog.log.printErrorMessage("Can't open file:" + file.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean setFile(File file) {
		close();
		return Initialization(file);
	}

	public boolean setFile(String file) {

		return Initialization(new File(file));
	}

	public boolean close() {
		try {
			this.writer.close();
		} catch (IOException e) {
			SystemLog.log.printErrorMessage("Can't close file:" + ioFile.toString());
			e.printStackTrace();
			return false;
		}
		return true;
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
		if (isDebugOn()) {
			String tString = getSTDInfo(InformationType.debug) + message + '\n';
			try {
				writer.write(tString);
			} catch (IOException e) {
				SystemLog.log.printErrorMessage("Can't write to:" + ioFile.toString() + "\nmessage:" + tString);
				e.printStackTrace();
			}
		}
		return this;
	}

	@Override
	public StandardLog printInfoMessage(String message) {
		String tString = getSTDInfo(InformationType.info) + message + '\n';
		try {
			writer.write(tString);
		} catch (IOException e) {
			SystemLog.log.printErrorMessage("Can't write to:" + ioFile.toString() + "\nmessage:" + tString);
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public StandardLog printErrorMessage(String message) {
		String tString = getSTDInfo(InformationType.error) + message + '\n';
		try {
			writer.write(tString);
		} catch (IOException e) {
			SystemLog.log.printErrorMessage("Can't write to:" + ioFile.toString() + "\nmessage:" + tString);
			e.printStackTrace();
		}
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
