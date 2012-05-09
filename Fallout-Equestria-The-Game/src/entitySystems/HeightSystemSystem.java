package entitySystems;

import components.PhysicsComp;
import components.TransformationComp;

import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class HeightSystemSystem extends EntitySingleProcessingSystem{

	public HeightSystemSystem(IEntityWorld world) {
		super(world, TransformationComp.class, PhysicsComp.class);
	}

	@Override
	public void initialize() {
	}

	@Override
	protected void processEntity(IEntity entity) {
				
		
	}

}
