package ability;

import utils.time.GameTime;
import entityFramework.IEntity;
import entityFramework.IEntityManager;

public abstract class Ability {
	private final boolean blocking;
	protected IEntityManager EntityManager;
	protected IEntity SorceEntity;
	protected CreationFactory CreationFactory;
	
	public Ability(boolean blocking) {
		this.blocking = blocking;
	}
	
	public boolean isBlocking() {
		return blocking;
	}
	
	public void initialize(IEntityManager manager, IEntity sourceEntity) {
		this.EntityManager = manager;
		this.SorceEntity = sourceEntity;
		this.CreationFactory = new CreationFactory(manager);
	}
	
	public abstract void start();
	public abstract void update(GameTime time);
	public abstract void stop();
}
