package entityFramework;

import com.google.common.collect.ImmutableSet;

public abstract class TimedEntityProcessingSystem extends EntitySystem {

	private int delta;
	private int counter;

	protected TimedEntityProcessingSystem(IEntityWorld world,
			Class<? extends IComponent>[] componentsClasses, int delta) {
		super(world, componentsClasses);
		this.delta = delta;
		this.counter = 0;
	}

	@Override
	public void initialize() {

	}

	@Override
	public final void process() {
		this.counter++;
		if(this.counter >= this.delta){
			ImmutableSet<IEntity> entities = ImmutableSet.copyOf(this.getEntities().values());
			this.processEntitys(entities);
			this.counter = 0;
		}
	}
	
	protected abstract void processEntitys(ImmutableSet<IEntity> entities);
}
