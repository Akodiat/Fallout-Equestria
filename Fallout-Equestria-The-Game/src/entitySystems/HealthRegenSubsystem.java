package entitySystems;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import components.ActionPointsComponent;
import components.HealthComponent;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class HealthRegenSubsystem extends EntityProcessingSystem {

	protected HealthRegenSubsystem(IEntityWorld world,
			Class<? extends IComponent>[] componentsClasses) {
		super(world, componentsClasses);
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
