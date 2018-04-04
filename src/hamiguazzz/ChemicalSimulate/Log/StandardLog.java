package hamiguazzz.ChemicalSimulate.Log;

/**
 * Define which method should the log has.
 *
 * @author hamiguazzz
 */
public interface StandardLog {
	/**
	 * Only the debug is on,should the debug message be printed.
	 *
	 * @return whether the debug is on
	 */
	boolean isDebugOn();

	/**
	 * Set the debug flag.
	 *
	 * @param debug
	 */
	void setDebug(boolean debug);

	/**
	 * Print debug message to out stream
	 *
	 * @param message
	 * @return this
	 */
	StandardLog printDebugMessage(String message);

	default StandardLog printDebugMessage(Object message) {
		return printDebugMessage(message.toString());
	}

	/**
	 * Print error message to out stream
	 *
	 * @param message
	 * @return this
	 */
	StandardLog printInfoMessage(String message);

	default StandardLog printInfoMessage(Object message) {
		return printInfoMessage(message.toString());
	}

	/**
	 * Print info message to out stream
	 *
	 * @param message
	 * @return this
	 */
	StandardLog printErrorMessage(String message);

	default StandardLog printErrorMessage(Object message) {
		return printErrorMessage(message.toString());
	}
}
