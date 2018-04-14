package hamiguazzz.ChemicalSimulate.Model;

public abstract class Reaction{
	private final Container container;

	protected Container getContainer(){
		return  this.container;
	}

	public abstract void react(double time);

	public abstract void increaseRate(double rate);

	public Reaction(Container container){
		this.container = container;
	}
}
