package hamiguazzz.ChemicalSimulate.View;


import hamiguazzz.ChemicalSimulate.Model.Container;
import hamiguazzz.ChemicalSimulate.Model.Environment;
import hamiguazzz.ChemicalSimulate.Model.FourSTDReaction;
import hamiguazzz.ChemicalSimulate.Model.Substance;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CreateCtrler {
	@FXML
	private TextField r111;
	@FXML
	private TextField r112;
	@FXML
	private TextField r113;
	@FXML
	private TextField r121;
	@FXML
	private TextField r122;
	@FXML
	private TextField r123;
	@FXML
	private TextField r131;
	@FXML
	private TextField r132;
	@FXML
	private TextField r133;
	@FXML
	private TextField r141;
	@FXML
	private TextField r142;
	@FXML
	private TextField r143;
	@FXML
	private TextField r211;
	@FXML
	private TextField r212;
	@FXML
	private TextField r213;
	@FXML
	private TextField r221;
	@FXML
	private TextField r222;
	@FXML
	private TextField r223;
	@FXML
	private TextField r231;
	@FXML
	private TextField r232;
	@FXML
	private TextField r233;
	@FXML
	private TextField r241;
	@FXML
	private TextField r242;
	@FXML
	private TextField r243;
	@FXML
	private TextField p11;
	@FXML
	private TextField p12;
	@FXML
	private TextField p13;
	@FXML
	private TextField p21;
	@FXML
	private TextField p22;
	@FXML
	private TextField p23;
	@FXML
	private TextField t1;
	@FXML
	private TextField t2;
	@FXML
	private TextField t3;
	@FXML
	private TextField t4;
	@FXML
	private TextField vol;
	@FXML
	private Button btnStart;

	private Environment environment = null;

	private static Substance convertToSubstance(TextField name,TextField mol){
		return new Substance(name.getText(),mol !=null? Double.valueOf(mol.getText()):0.0);
	}

	private static FourSTDReaction convertToReaction(Container container,List<TextField> names, List<TextField> mols,
	                                   List<TextField> cofs){
		var subs = new ArrayList<Substance>();
		var cs = new ArrayList<Double>();
		for (TextField cof : cofs) {
			cs.add(Double.valueOf(cof.getText()));
		}
		for (int i = 0; i <names.size(); i++) {
			subs.add(convertToSubstance(names.get(i), mols.get(i)));
		}
		return new FourSTDReaction(container,subs,cs,0,0);
	}

	private static FourSTDReaction finalReaction(FourSTDReaction reaction,TextField kp,TextField kn,TextField rate){
		reaction.setCoefficient_K_negative( Double.valueOf(kn.getText()));
		reaction.setCoefficient_K_positive( Double.valueOf(kp.getText()));
		reaction.increaseRate( Double.valueOf(rate.getText()));
		return reaction;
	}

	private Container getContainer(){
		var container = new Container();
		var names1 = new ArrayList<TextField>();
		names1.add(r112);names1.add(r122);names1.add(r132);names1.add(r142);
		var names2 = new ArrayList<TextField>();
		names2.add(r212);names2.add(r222);names2.add(r232);names2.add(r242);
		var cofs1 = new ArrayList<TextField>();
		cofs1.add(r111);cofs1.add(r121);cofs1.add(r131);cofs1.add(r141);
		var cofs2 = new ArrayList<TextField>();
		cofs2.add(r211);cofs2.add(r221);cofs2.add(r231);cofs2.add(r241);
		var mol1 = new ArrayList<TextField>();
		mol1.add(r113);mol1.add(r123);mol1.add(r133);mol1.add(r143);
		var mol2 = new ArrayList<TextField>();
		mol2.add(r213);mol2.add(r223);mol2.add(r233);mol2.add(r243);

		var reaction1 = finalReaction(convertToReaction(container, names1, mol1, cofs1), p11, p13,p12);
		var reaction2 = finalReaction(convertToReaction(container, names2, mol2, cofs2), p21, p23,p22);

		container.addReaction(reaction1);
		container.addReaction(reaction2);

		container.setVolume(Double.valueOf(vol.getText()));

		return container;
	}

	public Environment getEnvironment() {
		if(environment!=null)return environment;
		environment = new Environment(getContainer(), t1.getText(), t2.getText());
		Environment.FPS = new BigDecimal(t3.getText());
		Environment.Multi=new BigDecimal(t4.getText());
		return environment;
	}

	@FXML
	public void start(){
		System.out.println(getEnvironment());
	}

}