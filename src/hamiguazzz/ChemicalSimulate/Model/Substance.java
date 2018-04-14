package hamiguazzz.ChemicalSimulate.Model;

import hamiguazzz.ChemicalSimulate.Log.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Substance implements Cloneable{
	private final String name;
	double mol = 0;


	public Substance(String name) {
		this.name = name;
	}

	public Substance(String name,double mol) {
		this.name = name;
		this.mol=mol;
	}

	public String getName() {
		return name;
	}

	public void add(double x) {
		mol+=x;
	}

	public void reduce(double x){
		if (mol>=x)
			mol-=x;
		else
			mol=0;
	}

	@Override
	public Substance clone(){
		return new Substance(name,mol);
	}

	@Override
	public String toString() {
		if (Log.log.isDebugOn())
			return "["+getName()+":"+mol+"mol]"+"@" + Integer.toHexString(hashCode());
		return "["+getName()+":"+new BigDecimal(mol).setScale(6, RoundingMode.HALF_UP)+"mol]";
	}
}
