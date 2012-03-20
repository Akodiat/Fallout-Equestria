package entitySystems;

import com.google.common.collect.ImmutableSet;

import entityFramework.EntityProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class RegenSystem extends EntityProcessingSystem {

	HealthRegenSubsystem hRS;
	ActionPointsRegenSubsystem aPRS;

	public RegenSystem(IEntityWorld world,
			Class<? extends IComponent>[] componentsClasses) {
		super(world, componentsClasses);
		hRS = new HealthRegenSubsystem(world);
		aPRS = new ActionPointsRegenSubsystem(world);
	}

	@Override
	public void initialize() {

	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		hRS.process();
		aPRS.process();
	}
}
