package entityFramework;

import utils.GameTime;

public interface IEntityWorld {

	public abstract IEntityManager getEntityManager();
	public abstract IEntitySystemManager getSystemManager();
	public abstract IEntityDatabase getDatabase();
	public abstract GameTime getTime();
	

	public abstract void initialize();
	
	public abstract void update(GameTime time);
	public abstract void render();

}