package entitySystems;

import components.DeathComp;
import death.IDeathAction;

import entityFramework.ComponentMapper;
import entityFramework.EntitySystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class DeathResolveSystem extends EntitySystem{
	
	public DeathResolveSystem(IEntityWorld world) {
		super(world, DeathComp.class);
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
			processDeath(entity, deathComp);
		}
	}

	private void processDeath(IEntity entity, DeathComp deathComp) {
		for (IDeathAction deathAction : deathComp.getDeathActions()) {
			deathAction.excecute(entity, this.getWorld().getEntityManager());
		}
	}
}
