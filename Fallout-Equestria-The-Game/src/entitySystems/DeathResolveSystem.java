package entitySystems;

import scripting.ILineScriptProcessor;
import components.DeathComp;

import entityFramework.ComponentMapper;
import entityFramework.EntitySystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class DeathResolveSystem extends EntitySystem{
	
	ILineScriptProcessor processor;

	public DeathResolveSystem(IEntityWorld world, ILineScriptProcessor deathProcessor) {
		super(world, DeathComp.class);
		this.processor = deathProcessor;
	}
	
	private ComponentMapper<DeathComp> deathCM;

	@Override
	public void initialize() {
		deathCM = ComponentMapper.create(this.getWorld().getDatabase(), DeathComp.class);
		
	}

	@Override
	public void process() {
		//Do nothing at all.
	}
	
	public void entityDestroyed(IEntity entity) {
		if(this.getEntities().containsKey(entity.getUniqueID())) {
			DeathComp deathComp = this.deathCM.getComponent(entity);
			processDeath(deathComp);
			
		}
	}

	private void processDeath(DeathComp deathComp) {
		this.processor.processEntireScript(deathComp.getDeathScript());
	}

}
