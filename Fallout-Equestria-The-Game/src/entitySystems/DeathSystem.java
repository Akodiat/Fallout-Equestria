package entitySystems;

import components.HealthComp;

import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class DeathSystem extends EntitySingleProcessingSystem{

	public DeathSystem(IEntityWorld world) {
		super(world, HealthComp.class);
		// TODO Auto-generated constructor stub
	}

	private ComponentMapper<HealthComp> healthCM;
	
	@Override
	public void initialize() {
		healthCM = ComponentMapper.create(this.getWorld().getDatabase(), HealthComp.class);
	}

	@Override
	protected void processEntity(IEntity entity) {
		HealthComp healthComp = healthCM.getComponent(entity);		
		if(healthComp.getHealthPoints() <= 0.0f) {
			entity.kill();
		}
	}


}
