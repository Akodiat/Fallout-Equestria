package components;

import utils.LineScript;

public class DeathComp {
	LineScript deathScript;
	
	public DeathComp() {
		this.deathScript = null;
	}
	
	public DeathComp(LineScript deathScript) {
		this.deathScript = deathScript;
	}
	
	public LineScript getDeathScript() {
		return deathScript;
	}

	public void setDeathScript(LineScript deathScript) {
		this.deathScript = deathScript;
	}
}
