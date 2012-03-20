package entitySystems;

import com.google.common.collect.ImmutableSet;

import components.HealthComponent;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class HealthRegenSubsystem extends EntityProcessingSystem {

	public HealthRegenSubsystem(IEntityWorld world,
			Class<? extends IComponent>[] componentsClasses) {
		super(world, HealthComponent.class);
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
