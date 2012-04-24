package entitySystems;

import utils.GameTime;
import components.BehaviourComp;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class ScriptSystem extends EntitySingleProcessingSystem {

	public ScriptSystem(IEntityWorld world) {
		super(world, BehaviourComp.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processEntity(IEntity entity) {
		BehaviourComp scriptComp = entity.getComponent(BehaviourComp.class);
		if(scriptComp.isInitialized()) {
			GameTime time = this.getWorld().getTime();
			scriptComp.updateScript(time);
		} else {
			scriptComp.startScript(getEntityManager(), entity);
		}
	}
}
