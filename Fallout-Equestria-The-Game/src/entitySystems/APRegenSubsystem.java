package entitySystems;

import com.google.common.collect.ImmutableSet;
import components.AbilityPointsComp;

import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import entityFramework.TimedEntityProcessingSystem;

public class APRegenSubsystem extends TimedEntityProcessingSystem {

	protected APRegenSubsystem(IEntityWorld world, float regentimer) {
		super(world, regentimer, AbilityPointsComp.class);
	}

	@Override
	public void initialize() {
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			AbilityPointsComp actiRegCom = entity.getComponent(AbilityPointsComp.class);
			actiRegCom.regenAbilityPoints();
		}
	}

}
