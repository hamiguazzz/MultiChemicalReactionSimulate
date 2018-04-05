package hamiguazzz.ChemicalSimulate.Model;

import java.util.ArrayList;

public class FourSTDReaction extends Reaction {

	private final ArrayList<String> names = new ArrayList<>();
	private double coefficient_K_positive;
	private double coefficient_K_negative;
	private ArrayList<Double> coefficient_h;

	public FourSTDReaction(Container container, ArrayList<Substance> substances) {
		super(container);
		int count = 4;
		assert substances.size()>= count;
		for (int i = 0; i < count; i++) {
			if (container.find(substances.get(i).getName())==null)
				container.addSubstance(substances.get(i).clone());
			names.add(substances.get(i).getName());
		}
		coefficient_h = new ArrayList<>();
	}

	public void setCoefficient_K_negative(double coefficient_K_negative) {
		this.coefficient_K_negative = coefficient_K_negative;
	}

	public void setCoefficient_K_positive(double coefficient_K_positive) {
		this.coefficient_K_positive = coefficient_K_positive;
	}

	@Override
	public void increaseRate(double rate){
		this.coefficient_K_positive*=rate;
		this.coefficient_K_negative*=rate;
	}

	public FourSTDReaction(Container container, ArrayList<Substance> substances, ArrayList<Double> coefficient_h,
	                       double coefficient_K_positive, double coefficient_K_negative) {
		this(container,substances);
		this.coefficient_h = coefficient_h;
		this.coefficient_K_negative=coefficient_K_negative;
		this.coefficient_K_positive = coefficient_K_positive;
	}

	@Override
	public void react(double time) {
		double V = getContainer().getVolume();
		Substance s1 = getContainer().find(names.get(0));
		Substance s2 = getContainer().find(names.get(1));
		Substance s3 = getContainer().find(names.get(2));
		Substance s4 = getContainer().find(names.get(3));
		double vp = coefficient_K_positive
				*Math.pow(s1.mol/V, coefficient_h.get(0))
				*Math.pow(s2.mol/V, coefficient_h.get(1));
		double vn = coefficient_K_negative
				*Math.pow(s3.mol/V, coefficient_h.get(2))
				*Math.pow(s4.mol/V, coefficient_h.get(3));
		s1.reduce(V*vp*coefficient_h.get(0)*time);
		s2.reduce(V*vp*coefficient_h.get(1)*time);
		s3.add(V*vp*coefficient_h.get(2)*time);
		s4.add(V*vp*coefficient_h.get(3)*time);
		s3.reduce(V*vn*coefficient_h.get(2)*time);
		s4.reduce(V*vn*coefficient_h.get(3)*time);
		s1.add(V*vn*coefficient_h.get(0)*time);
		s2.add(V*vn*coefficient_h.get(1)*time);
	}
}
