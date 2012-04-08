package death;

import java.util.HashMap;
import java.util.Map;

import misc.IProcessor;

import components.DeathComp;

import entityFramework.EntitySystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class DeathResolveSystem extends EntitySystem{
	
private Map<DeathActions, AbstractDeathProcessor> deathActions;
	
	public DeathResolveSystem(IEntityWorld world) {
		super(world, DeathComp.class);
		deathActions = new HashMap<>();
	}
	
	public DeathResolveSystem(IEntityWorld world,Map<DeathActions, AbstractDeathProcessor> deathActions) {
		super(world, DeathComp.class);
		this.deathActions = new HashMap<>(deathActions);
	}

	public void addDeathProcessor(AbstractDeathProcessor processor){
		deathActions.put(processor.deathAction, processor);
	}
	public void addDeathProcessors(Map<DeathActions, AbstractDeathProcessor> deathActions){
		this.deathActions.putAll(deathActions);
	}
	
	@Override
	public void initialize() {
		for (IProcessor processor : deathActions.values()) {
			processor.initialize(this.getEntityManager());
		}
	}
	

	@Override
	public void process() {
		//Do nothing at all.
	}
	
	public void entityDestroyed(IEntity entity) {
		if(this.getEntities().containsKey(entity.getUniqueID())) {
			DeathComp deathComp = entity.getComponent(DeathComp.class);
			processDeath(entity, deathComp);
		}
	}

	private void processDeath(IEntity entity, DeathComp deathComp) {
		for (DeathActions deathAction : deathComp.getDeathActions()) {
			this.deathActions.get(deathAction).processEntity(entity, getEntityManager(), null);
		}
	}
}
