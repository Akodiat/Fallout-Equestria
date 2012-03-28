package entitySystems;

import com.google.common.collect.ImmutableSet;

import components.HealthComp;

import entityFramework.ComponentMapper;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import entityFramework.TimedEntityProcessingSystem;

public class HealthRegenSubsystem extends TimedEntityProcessingSystem {

	
	public HealthRegenSubsystem(IEntityWorld world, float regenTimer) {
		super(world, regenTimer, HealthComp.class);
	}

	private ComponentMapper<HealthComp> hCM;

	@Override
	public void initialize() {
		hCM = ComponentMapper.create(this.getWorld().getDatabase(),
				HealthComp.class);
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			HealthComp healCom = hCM.getComponent(entity);
			healCom.regenHealthPoints();
		}
	}

}
