package entitySystems;

import com.google.common.collect.ImmutableSet;
import components.ActionPointsComponent;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class ActionPointsRegenSubsystem extends EntityProcessingSystem {

	protected ActionPointsRegenSubsystem(IEntityWorld world,
			Class<? extends IComponent>[] componentsClasses) {
		super(world, ActionPointsComponent.class);
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
