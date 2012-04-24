package components;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import anotations.Editable;
import scripting.Behaviour;
import utils.GameTime;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityManager;

@Editable
@XStreamAlias("Behaviour")
@SuppressWarnings("serial")
public class BehaviourComp implements IComponent {
	@Editable
	private Behaviour script;
	
	public BehaviourComp() {
		this((Behaviour)null);
	}
	
	public BehaviourComp(Behaviour script) {
		this.setBehaviour(script);
	}
	
	public BehaviourComp(BehaviourComp other) {
		this.setBehaviour((Behaviour)other.getBehaviour().clone());		
	}
	
	public void startScript(IEntityManager entityManager, IEntity entity) {
		script.initialize(entityManager, entity);
		script.start();
	}
	
	public void updateScript(GameTime time) {
		script.update(time);
	}

	public Behaviour getBehaviour() {
		return script;
	}

	public void setBehaviour(Behaviour script) {
		this.script = script;
	}

	public boolean isInitialized() {
		return script.isInitialized();
	}

	public Object clone() {
		return new BehaviourComp(this);
	}

	
}
