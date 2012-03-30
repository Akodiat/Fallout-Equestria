package entitySystems;

import com.google.common.collect.ImmutableSet;

import components.AbilityComp;

import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import entityFramework.TimedEntityProcessingSystem;

public class APRegenSubsystem extends TimedEntityProcessingSystem {

	protected APRegenSubsystem(IEntityWorld world, float regentimer) {
		super(world, regentimer, AbilityComp.class);
	}

	@Override
	public void initialize() {
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			AbilityComp actiRegCom = entity.getComponent(AbilityComp.class);
			actiRegCom.regenAbilityPoints();
		}
	}

}
