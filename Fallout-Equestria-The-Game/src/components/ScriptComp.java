package components;

import anotations.Editable;
import scripting.BehaviourScript;
import utils.GameTime;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityManager;

@Editable
public class ScriptComp implements IComponent {
	@Editable
	private BehaviourScript script;
	
	public ScriptComp() {
		this((BehaviourScript)null);
	}
	
	public ScriptComp(BehaviourScript script) {
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

	public BehaviourScript getScript() {
		return script;
	}

	public void setScript(BehaviourScript script) {
		this.script = script;
	}

	public boolean isInitialized() {
		return script.isInitialized();
	}

	public Object clone() {
		return new ScriptComp(this);
	}

	
}
