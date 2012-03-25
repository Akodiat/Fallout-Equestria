package components;

import entityFramework.IComponent;
import scripting.LineScript;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("Death")
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
	
	public String toString() {
		return "Death Comp: " +
			   "\n Script: \n" + this.deathScript.toString();
		
	}
}
