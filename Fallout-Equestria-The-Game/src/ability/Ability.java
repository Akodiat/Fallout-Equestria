package ability;

import utils.GameTime;
import entityFramework.IEntity;
import entityFramework.IEntityManager;

public abstract class Ability {
	private final boolean blocking;
	protected IEntityManager EntityManager;
	protected IEntity Entity;
	
	public Ability(boolean blocking) {
		this.blocking = blocking;
	}
	
	public boolean isBlocking() {
		return blocking;
	}
	
	public void initialize(IEntityManager manager, IEntity entity) {
		this.EntityManager = manager;
		this.Entity = entity;
	}
	
	public abstract void start();
	public abstract void update(GameTime time);
	public abstract void stop();
}
