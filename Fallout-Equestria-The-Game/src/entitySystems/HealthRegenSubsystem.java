package entitySystems;

import com.google.common.collect.ImmutableSet;

import entityFramework.EntityProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class HealthRegenSubsystem extends EntityProcessingSystem {

	protected HealthRegenSubsystem(IEntityWorld world,
			Class<? extends IComponent>[] componentsClasses) {
		super(world, componentsClasses);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		// TODO Auto-generated method stub
		
	}

}
