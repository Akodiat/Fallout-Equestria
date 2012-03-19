package entitySystems;


import com.google.common.collect.ImmutableSet;

import entityFramework.EntityProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class RegenSystem extends EntityProcessingSystem {

	protected RegenSystem(IEntityWorld world,
			Class<? extends IComponent>[] componentsClasses) {
		super(world, componentsClasses);
	}

	HealthRegenSubsystem hRS;
	ActionPointsRegenSubsystem aPRS;
	
	@Override
	public void initialize() {
		HealthRegenSubsystem hRS;
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {

	}
}
