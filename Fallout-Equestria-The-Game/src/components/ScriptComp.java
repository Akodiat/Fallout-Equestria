package components;

import anotations.Editable;
import scripting.Script;
import utils.GameTime;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityManager;

@Editable
public class ScriptComp implements IComponent {
	@Editable
	private Script script;
	
	public ScriptComp() {
		this((Script)null);
	}
	
	public ScriptComp(Script script) {
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
