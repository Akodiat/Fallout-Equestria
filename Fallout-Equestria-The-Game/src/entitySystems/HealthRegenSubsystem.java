package entitySystems;

import com.google.common.collect.ImmutableSet;

import components.HealthComponent;

import entityFramework.ComponentMapper;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import entityFramework.TimedEntityProcessingSystem;

public class HealthRegenSubsystem extends TimedEntityProcessingSystem {

	@SuppressWarnings("unchecked")
	public HealthRegenSubsystem(IEntityWorld world) {
		super(world, 0.1f, HealthComponent.class);
	}

	private ComponentMapper<HealthComponent> hCM;

	@Override
	public void initialize() {
		hCM = ComponentMapper.create(this.getWorld().getDatabase(),
				HealthComponent.class);
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			HealthComponent healCom = hCM.getComponent(entity);
			healCom.regenHealthPoints();
		}
	}

}
