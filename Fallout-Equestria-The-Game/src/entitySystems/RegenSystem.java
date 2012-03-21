package entitySystems;

import com.google.common.collect.ImmutableSet;

import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class RegenSystem extends EntityProcessingSystem {

	HealthRegenSubsystem hRS;
	ActionPointsRegenSubsystem aPRS;

	public RegenSystem(IEntityWorld world) {
		super(world);
		hRS = new HealthRegenSubsystem(world);
		aPRS = new ActionPointsRegenSubsystem(world);
	}

	@Override
	public void initialize() {
		hRS.initialize();
		aPRS.initialize();
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		hRS.process();
		aPRS.process();
	}

	@Override
	public void entityChanged(IEntity entity) {
		hRS.entityChanged(entity);
		aPRS.entityChanged(entity);
	}

	@Override
	public void entityDestroyed(IEntity entity) {
		hRS.entityDestroyed(entity);
		aPRS.entityDestroyed(entity);
	}
}
