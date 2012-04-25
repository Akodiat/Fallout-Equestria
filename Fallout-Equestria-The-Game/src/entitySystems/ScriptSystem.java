package entitySystems;

import scripting.Behavior;
import utils.GameTime;
import components.BehaviourComp;
import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class ScriptSystem extends EntitySingleProcessingSystem {

	public ScriptSystem(IEntityWorld world) {
		super(world, BehaviourComp.class);
	}
	
	private ComponentMapper<BehaviourComp>	bCM;	

	@Override
	public void initialize() {
		bCM = ComponentMapper.create(this.getDatabase(), BehaviourComp.class);
	}

	@Override
	public void entityDestroyed(IEntity entity) { 
		super.entityDestroyed(entity);
		BehaviourComp behaviour = bCM.getComponent(entity);
		if(behaviour != null) {
			behaviour.onDestroy();
		}
	}
	
	@Override
	protected void processEntity(IEntity entity) {
		BehaviourComp behaviourComp = bCM.getComponent(entity);
		if(behaviourComp.isInitialized()) {
			GameTime time = this.getWorld().getTime();
			if(behaviourComp.isEnabled()) {
				behaviourComp.update(time);
			}
		} else {
			behaviourComp.start(getEntityManager(), entity);
		}
	}
}
