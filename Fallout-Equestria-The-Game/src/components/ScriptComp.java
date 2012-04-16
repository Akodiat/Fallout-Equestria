package components;

import scripting.Script;
import utils.GameTime;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityManager;

public class ScriptComp implements IComponent {
	private Script script;
	
	public ScriptComp(Script script) {
		if(script == null) {
			throw new NullPointerException("script cannot by null!");
		}
		this.setScript(script);
	}
	
	public ScriptComp(ScriptComp other) {
		this.setScript(other.getScript().createNew());		
	}
	
	public void startScript(IEntityManager entityManager, IEntity entity) {
		script.initialize(entityManager, entity);
		script.start();
	}
	
	public void updateScript(GameTime time) {
		script.update(time);
	}

	public Script getScript() {
		return script;
	}

	public void setScript(Script script) {
		this.script = script;
	}

	public boolean isInitialized() {
		return script.isInitialized();
	}

	public Object clone() {
		return new ScriptComp(this);
	}

	
}
