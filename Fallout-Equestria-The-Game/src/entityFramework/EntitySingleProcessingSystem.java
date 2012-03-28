package entityFramework;

import com.google.common.collect.ImmutableSet;


public abstract class EntitySingleProcessingSystem extends EntityProcessingSystem {

	@SafeVarargs
	protected EntitySingleProcessingSystem(IEntityWorld world,
			Class<? extends IComponent>... componentsClasses) {
		super(world, componentsClasses);
	}


	@Override
	protected final void processEntities(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			this.processEntity(entity);
		}
	}
	
	protected abstract void processEntity(IEntity entity);

}
 