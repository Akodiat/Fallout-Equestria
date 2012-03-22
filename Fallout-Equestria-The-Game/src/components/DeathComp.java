package components;

import entityFramework.IComponent;
import utils.LineScript;

public class DeathComp implements IComponent{
	LineScript deathScript;
	
	public DeathComp() {
		this.deathScript = null;
	}
	
	public DeathComp(LineScript deathScript) {
		this.deathScript = deathScript;
	}
	
	private DeathComp(DeathComp other) {
		this.deathScript = other.deathScript;
	}
	
	public LineScript getDeathScript() {
		return deathScript;
	}

	public void setDeathScript(LineScript deathScript) {
		this.deathScript = deathScript;
	}
	
	public Object clone() {
		return new DeathComp(this);
	}
}
