package hamiguazzz.ChemicalSimulate.Log;

import hamiguazzz.ChemicalSimulate.Log.StandardLog;
import hamiguazzz.ChemicalSimulate.Log.SystemLog;

public class Log {
	public static StandardLog log = SystemLog.log;

	public static void setDefaultLog(StandardLog newLog) {
		log = newLog;
	}
}
