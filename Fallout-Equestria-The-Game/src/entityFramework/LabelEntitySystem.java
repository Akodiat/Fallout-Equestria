package entityFramework;

import java.util.BitSet;

public abstract class LabelEntitySystem implements IEntitySystem {
	
	private int processingOrder;
	private final String label;
	private final IEntityWorld world;
	private final BitSet typeBits;
	protected IEntity Entity;
	private boolean enabled;
	
	@SafeVarargs
	public LabelEntitySystem(IEntityWorld world, String label, Class<? extends IComponent>... typeBits) {
		this.world = world;
		this.label = label;
		this.processingOrder = 0;
		this.typeBits = new BitSet();
		this.composeBits(typeBits);
		
		
	}
	
	private void composeBits(Class<? extends IComponent>[] componentsClasses) {
		IEntityDatabase database = this.world.getDatabase();
		for (Class<? extends IComponent> componentClass : componentsClasses) {
			BitSet componentBit = database.getComponentTypeBit(componentClass);
			this.typeBits.or(componentBit);
		}
	}
	
	protected IEntityWorld getWorld() {
		return this.world;
	}
	
	@Override
	public void entityChanged(IEntity entity) {
		if(entity.getLabel() == this.label) {
			BitSet changedSet = (BitSet)this.typeBits.clone();
			changedSet.and(entity.getComponentBits());
			if(changedSet.equals(this.typeBits)) {
				this.Entity = entity;
			}
		}
	}

	@Override
	public void entityDestroyed(IEntity entity) {
		if(entity.getLabel() == this.label) {
			this.Entity = null;
		}
		
	}


	@Override
	public final void process() {
		if(this.Entity != null) {
			processEntity(this.Entity);
		} else {
			processMissingEntity();
		}		
	}

	protected abstract void processEntity(IEntity entity);
	protected abstract void processMissingEntity();
	
	@Override
	public boolean getEnabled() {
		return this.enabled;
	}

	@Override
	public void setEnabled(boolean value) {
		this.enabled = value;
	}

	@Override
	public int getProcessingOrder() {
		return this.processingOrder;
	}

	@Override
	public void setProcessingOrder(int order) {
		this.processingOrder = order;
	}

}
