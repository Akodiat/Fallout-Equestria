package entityFramework;

import utils.time.GameTime;

import com.google.inject.Inject;

public class EntityWorld implements IEntityWorld {
	private final IEntityManager entityManager;
	private final IEntitySystemManager systemManager;
	private final IEntityDatabase database;
	private GameTime gameTime;
	
	
	@Inject
	public EntityWorld(IEntityManager entityManager, IEntitySystemManager systemManager, IEntityDatabase database) {
		this.entityManager = entityManager;
		this.systemManager = systemManager;
		this.database = database;
		this.gameTime = GameTime.Zero;
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
	
	public GameTime getTime() {
		return this.gameTime;
	}
	
	@Override
	public void initialize() {
		this.systemManager.initialize();
	}
	
	@Override
	public void render() {
		this.systemManager.render();
		
	}

	@Override
	public void update(GameTime time) {
		this.gameTime = time;
		this.systemManager.logic();
	}
}
