package entitySystems;

import components.HealthComponent;

import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class DeathSystem extends EntitySingleProcessingSystem{

	public DeathSystem(IEntityWorld world) {
		super(world, HealthComponent.class);
		// TODO Auto-generated constructor stub
	}

	private ComponentMapper<HealthComponent> healthCM;
	
	@Override
	public void initialize() {
		healthCM = ComponentMapper.create(this.getWorld().getDatabase(), HealthComponent.class);
	}

	@Override
	protected void processEntity(IEntity entity) {
		HealthComponent healthComp = healthCM.getComponent(entity);		
		if(healthComp.getHealthPoints() <= 0.0f) {
			entity.kill();
		}
	}


}
