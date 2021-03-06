package entityFramework;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public abstract class EntitySystem implements IEntitySystem{

	private final IEntityWorld world;
	private final Map<Integer, IEntity> entities;
	private final BitSet componentBits;
	private boolean enabled;
	private int processingOrder;
	
	
	protected final IEntityWorld getWorld() {
		return this.world;
	}
	
	protected final IEntityManager getEntityManager() {
		return this.world.getEntityManager();
	}
	
	protected final IEntityDatabase getDatabase() {
		return this.world.getDatabase();
	}
	
	protected final Map<Integer, IEntity> getEntities() {
		return this.entities;
	}
	
	@Override
	public final boolean getEnabled() {
		return this.enabled;
	}
	
	@Override
	public final void setEnabled(boolean value) {
		this.enabled = value;
	}
	
	@Override
	public final int getProcessingOrder() {
		return this.processingOrder;
	}
	
	@Override
	public final void setProcessingOrder(int order) {
		this.processingOrder = order;
	}
	
	@SafeVarargs
	protected EntitySystem(IEntityWorld world, Class<? extends IComponent> ...componentsClasses) {
		this.world = world;
		this.entities = new HashMap<Integer, IEntity>();
		this.componentBits = new BitSet();
		this.composeBits(componentsClasses);
		this.enabled = true;
	}
	
	private void composeBits(Class<? extends IComponent>[] componentsClasses) {
		IEntityDatabase database = this.world.getDatabase();
		for (Class<? extends IComponent> componentClass : componentsClasses) {
			BitSet componentBit = database.getComponentTypeBit(componentClass);
			this.componentBits.or(componentBit);
		}
	}

	@Override
	public void entityChanged(IEntity entity) {
		if(this.componentBits.isEmpty())
			return;
		
		BitSet changedSet = (BitSet)this.componentBits.clone();
		changedSet.and(entity.getComponentBits());

		if(changedSet.equals(this.componentBits)) {
			if(!this.entities.containsKey(entity.getUniqueID())) {
				this.entities.put(entity.getUniqueID(), entity);
				this.entityAdded(entity);
			}
		} else {
			if(this.entities.containsKey(entity.getUniqueID())) {
				this.entities.remove(entity.getUniqueID());
				this.entityRemoved(entity);
			}
		}
	}

	protected void entityAdded(IEntity entity) { }
	protected void entityRemoved(IEntity entity) { }

	@Override
	public void entityDestroyed(IEntity entity) {
		if(this.entities.containsKey(entity.getUniqueID())) {
			this.entities.remove(entity.getUniqueID());
			this.entityRemoved(entity);
		}
	}
	
	@Override
	public abstract void process();
}
