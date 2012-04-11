package components;

import scripting.IScript;
import utils.GameTime;
import entityFramework.IComponent;

public class ScriptComp implements IComponent {
	private IScript script;
	
	public ScriptComp(IScript script) {
		if(script == null) {
			throw new NullPointerException("script cannot by null!");
		}
		this.setScript(script);
	}
	
	public ScriptComp(ScriptComp other) {
		this.setScript((IScript)other.clone());		
	}
	
	public void startScript() {
		script.start();
	}
	
	public void updateScript(GameTime time) {
		script.update(time);
	}

	public IScript getScript() {
		return script;
	}

	public void setScript(IScript script) {
		this.script = script;
	}

	public Object clone() {
		return new ScriptComp(this);
	}
	
}
