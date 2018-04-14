package hamiguazzz.ChemicalSimulate.View;

import hamiguazzz.ChemicalSimulate.Log.Log;
import hamiguazzz.ChemicalSimulate.Log.StandardLog;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

public class MainCtrler {
	@FXML
	private MenuItem editOrigin;
	@FXML
	private MenuItem showInfo;
	@FXML
	private TextArea logText;

	private Main main;

	@FXML
	public void showInfo(){
		var env = getMain().getEnvironment();
		for (String name:env.getMainContainer().getNamePool()) {
			Log.log.printInfoMessage(env.getMainContainer().find(name));
		}
	}

	public MainCtrler() {
		createLog();
	}

	private void createLog(){
		Log.log = new StandardLog(){
			private boolean isDebugOn = false;
			@Override
			public boolean isDebugOn() {
				return isDebugOn;
			}

			@Override
			public void setDebug(boolean debug) {
				this.isDebugOn = debug;
			}

			@Override
			public StandardLog printDebugMessage(String message) {
				addLog("[DEBUG]"+message+"\n");
				return this;
			}

			@Override
			public StandardLog printInfoMessage(String message) {
				addLog("[INFO]"+message+"\n");
				return this;
			}

			@Override
			public StandardLog printErrorMessage(String message) {
				addLog("[ERROR]"+message+"\n");
				return this;
			}
		};
	}

	private void addLog(String message){
		logText.setText(logText.getText()+message);
	}

	@FXML
	public void returnToOrigin(){
		getMain().root.setCenter(getMain().createPane);
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}
}
