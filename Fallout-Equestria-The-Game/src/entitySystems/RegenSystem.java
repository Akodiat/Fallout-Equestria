package entitySystems;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import components.ActionPointsComponent;
import components.AttackComponent;
import components.HealthComponent;
import components.PositionComponent;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class RegenSystem extends EntityProcessingSystem {

	protected RegenSystem(IEntityWorld world,
			Class<? extends IComponent>[] componentsClasses) {
		super(world, componentsClasses);

	}

	private ComponentMapper<ActionPointsComponent> aPCM;
	private ComponentMapper<HealthComponent> hCM;

	@Override
	public void initialize() {

		aPCM = ComponentMapper.create(this.getWorld().getDatabase(),
				ActionPointsComponent.class);
		hCM = ComponentMapper.create(this.getWorld().getDatabase(),
				HealthComponent.class);
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			ActionPointsComponent actiRegCom = aPCM.getComponent(entity);
			HealthComponent healCom = hCM.getComponent(entity);

			ImmutableList<IEntity> gEntities = ImmutableList.of();

		}
	}
}
