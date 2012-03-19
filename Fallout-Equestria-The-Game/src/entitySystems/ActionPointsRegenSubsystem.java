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

public class ActionPointsRegenSubsystem extends EntityProcessingSystem {

	protected ActionPointsRegenSubsystem(IEntityWorld world,
			Class<? extends IComponent>[] componentsClasses) {
		super(world, componentsClasses);
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
