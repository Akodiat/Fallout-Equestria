package entityFramework;

import com.google.common.collect.ImmutableSet;

public abstract class TimedEntityProcessingSystem extends EntitySystem {

	private float elapsedTime;
	private final float delayTime;

	@SafeVarargs
	protected TimedEntityProcessingSystem(IEntityWorld world, float delayTime,
			Class<? extends IComponent> ... componentsClasses) {
		super(world, componentsClasses);
		this.delayTime = delayTime;
		this.elapsedTime = 0.0f;
	}

	@Override
	public final void process() {
		if(this.shouldProcess()){
			ImmutableSet<IEntity> entities = ImmutableSet.copyOf(this.getEntities().values());
			this.processEntitys(entities);
		}
	}
	
	private boolean shouldProcess() {
		this.elapsedTime += this.getWorld().getDelta();
		if(this.elapsedTime >= this.delayTime) {
			this.elapsedTime -= this.delayTime;
			return true;
		}
		return false;
	}

	protected abstract void processEntitys(ImmutableSet<IEntity> entities);
}
