package entityFramework;

import com.google.common.collect.ImmutableSet;

public abstract class GroupedEntitySystem extends EntitySystem {

	private final String group;
	
	@SafeVarargs
	protected GroupedEntitySystem(IEntityWorld world, String group,
			Class<? extends IComponent>... componentsClasses) {
		super(world, componentsClasses);
		this.group = group;
	}

	@Override 
	public void entityChanged(IEntity entity) {
		if(entity.getGroups().contains(this.group)) {
			super.entityChanged(entity);
		}
	}

	@Override
	public void process() {
		ImmutableSet<IEntity> entities = ImmutableSet.copyOf(this.getEntities().values());
		if(!entities.isEmpty()) {
			this.processEntities(entities);
		}
	}

	protected String getGroup() {
		return this.group;
	}
	
	protected abstract void processEntities(ImmutableSet<IEntity> entities);
}
