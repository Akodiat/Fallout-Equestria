package entitySystems;

import utils.GameTime;
import components.BehaviourComp;
import content.ContentManager;
import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class ScriptSystem extends EntitySingleProcessingSystem {

	private ContentManager contentManager;
	public ScriptSystem(IEntityWorld world, ContentManager contentManager) {
		super(world, BehaviourComp.class);
		this.contentManager = contentManager;
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
			behaviourComp.start(getEntityManager(),contentManager, entity);
		}
	}
}
