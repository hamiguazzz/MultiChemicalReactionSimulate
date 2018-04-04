package hamiguazzz.ChemicalSimulate.Model;

public class Substance implements Comparable<Substance>,Cloneable{
	private final String name;
	public double mol = 0;


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
	public int compareTo(Substance o) {
		if (this.mol==o.mol)return 0;
		return this.mol>o.mol?1:-1;
	}

	@Override
	public Substance clone(){
		return new Substance(name,mol);
	}

	@Override
	public String toString() {
		return "["+getName()+":"+mol+"mol]"+"@" + Integer.toHexString(hashCode());
	}
}
