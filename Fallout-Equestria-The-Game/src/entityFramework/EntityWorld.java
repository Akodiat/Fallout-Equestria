package entityFramework;

import com.google.inject.Inject;

public class EntityWorld implements IEntityWorld {
	private final IEntityManager entityManager;
	private final IEntitySystemManager systemManager;
	private final IEntityDatabase database;
	private float delta;
	
	
	@Inject
	public EntityWorld(IEntityManager entityManager, IEntitySystemManager systemManager, IEntityDatabase database) {
		this.entityManager = entityManager;
		this.systemManager = systemManager;
		this.database = database;
		this.delta = 0f;
	}

	/* (non-Javadoc)
	 * @see entityFramework.IEntityWorld#getEntityManager()
	 */
	@Override
	public IEntityManager getEntityManager() {
		return this.entityManager;
	}
	
	/* (non-Javadoc)
	 * @see entityFramework.IEntityWorld#getSystemManager()
	 */
	@Override
	public IEntitySystemManager getSystemManager() {
		return this.systemManager;
	}
	
	/* (non-Javadoc)
	 * @see entityFramework.IEntityWorld#getDatabase()
	 */
	@Override
	public IEntityDatabase getDatabase() {
		return this.database;
	}
	
	public float getDelta() {
		return this.delta;
	}
	
	
	@Override
	public void render() {
		this.systemManager.render();
		
	}

	@Override
	public void update(float delta) {
		this.delta = delta;
		this.systemManager.logic();
	}
}
