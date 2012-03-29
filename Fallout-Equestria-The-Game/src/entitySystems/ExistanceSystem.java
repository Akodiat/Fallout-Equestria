package entitySystems;

import components.ExistanceComp;

import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class ExistanceSystem extends EntitySingleProcessingSystem{

	public ExistanceSystem(IEntityWorld world) {
		super(world, ExistanceComp.class);
	}

	@Override
	public void initialize() {
	}

	@Override
	protected void processEntity(IEntity entity) {
		float delta = this.getWorld().getDelta();
		
		ExistanceComp comp = entity.getComponent(ExistanceComp.class);
		comp.incrementElapsedExistance(delta);
		if(comp.isExistanceOver()) {
			entity.kill();
		}
	}

}
