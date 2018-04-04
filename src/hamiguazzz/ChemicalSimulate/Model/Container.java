package hamiguazzz.ChemicalSimulate.Model;

import java.math.BigDecimal;
import java.util.*;

public class Container implements Cloneable{
	private final HashMap<String,Substance> substances = new HashMap<>();
	private final HashSet<Reaction> reactions = new HashSet<>();
	private double volume=1.0;
	private BigDecimal time = new BigDecimal("0");

	public Container() {
	}

	public Substance find(String name){
		return substances.get(name);
	}

	private Container(Container clone_target){
		for (Map.Entry<String,Substance> e :clone_target.substances.entrySet()) {
			this.substances.put(e.getKey(), e.getValue().clone());
		}
		this.reactions.addAll(clone_target.reactions);
		this.volume = clone_target.volume;
		this.time = clone_target.time;
	}

	public void addSubstance(Substance substance){
		this.substances.put(substance.getName(), substance);
	}

	public boolean addReaction(Reaction reaction){
		return this.reactions.add(reaction);
	}

	public HashSet<Reaction> getReactions() {
		return reactions;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getVolume() {
		return volume;
	}

	public Container takePic(){
		return new Container(this);
	}

	public void next(BigDecimal time){
		for (Reaction reaction:reactions)
			reaction.react(time.doubleValue());
		this.time=this.time.add(time);
	}

	public BigDecimal getTime() {
		return time;
	}

	@Override
	public Container clone(){
		return this.takePic();
	}
}