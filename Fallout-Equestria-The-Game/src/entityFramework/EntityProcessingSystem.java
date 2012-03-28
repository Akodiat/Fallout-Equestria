package entityFramework;

import com.google.common.collect.ImmutableSet;


public abstract class EntityProcessingSystem extends EntitySystem{
	

	@SafeVarargs
	protected EntityProcessingSystem(IEntityWorld world,
			Class<? extends IComponent> ... componentsClasses) {
		super(world, componentsClasses);
		
	}

	@Override
	public void process() {
		ImmutableSet<IEntity> entities = ImmutableSet.copyOf(this.getEntities().values());
		this.processEntities(entities);
	}
	
	
	protected abstract void processEntities(ImmutableSet<IEntity> entities);

}
