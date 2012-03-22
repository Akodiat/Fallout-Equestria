package entitySystems;

import com.google.common.collect.ImmutableSet;
import components.ActionPointsComponent;

import entityFramework.ComponentMapper;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import entityFramework.TimedEntityProcessingSystem;

public class ActionPointsRegenSubsystem extends TimedEntityProcessingSystem {

	protected ActionPointsRegenSubsystem(IEntityWorld world) {
		super(world, 60, ActionPointsComponent.class);
	}

	private ComponentMapper<ActionPointsComponent> aPCM;

	@Override
	public void initialize() {
		aPCM = ComponentMapper.create(this.getWorld().getDatabase(),
				ActionPointsComponent.class);

	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			ActionPointsComponent actiRegCom = aPCM.getComponent(entity);
			actiRegCom.regenAbilityPoints();
		}

	}

}
