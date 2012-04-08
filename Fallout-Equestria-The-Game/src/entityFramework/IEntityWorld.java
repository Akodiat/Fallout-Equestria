package entityFramework;

public interface IEntityWorld {

	public abstract IEntityManager getEntityManager();

	public abstract IEntitySystemManager getSystemManager();

	public abstract IEntityDatabase getDatabase();

	public abstract float getDelta();
	

	public abstract void initialize();
	
	public abstract void update(float deltha);
	public abstract void render();

}