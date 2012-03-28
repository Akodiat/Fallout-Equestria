package entitySystems;

import com.google.common.collect.ImmutableSet;

import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class RegenSystem extends EntityProcessingSystem {

	HealthRegenSubsystem hRS;
	APRegenSubsystem aPRS;

	public RegenSystem(IEntityWorld world, float regenTimer) {
		super(world);
		hRS = new HealthRegenSubsystem(world, regenTimer);
		aPRS = new APRegenSubsystem(world,regenTimer);
	}

	@Override
	public void initialize() {
		hRS.initialize();
		aPRS.initialize();
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
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
