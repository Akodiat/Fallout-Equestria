package scripting;

import entityFramework.IEntity;
import entityFramework.IEntityManager;
import utils.GameTime;

public abstract class HitScript{
	
	protected IEntityManager entityManager;
	protected IEntity entity;
	private boolean initialized;
	
	public final void initialize(IEntityManager manager, IEntity entity) {
		this.entityManager = manager;
		this.entity = entity;
		this.initialized = true;
	}
	
	public boolean isInitialized() {
		return initialized;
	}
	
	public abstract void start();
	public abstract void onHit(IEntity targetEntity);
	public abstract HitScript createNew();
}
