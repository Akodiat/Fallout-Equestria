package entityFramework;

import com.google.inject.Inject;

public class EntityWorld implements IEntityWorld {
	private final IEntityManager entityManager;
	private final IEntitySystemManager systemManager;
	private final IEntityDatabase database;
	
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
	
	@Inject
	public EntityWorld(IEntityManager entityManager, IEntitySystemManager systemManager, IEntityDatabase database) {
		this.entityManager = entityManager;
		this.systemManager = systemManager;
		this.database = database;
	}
}
