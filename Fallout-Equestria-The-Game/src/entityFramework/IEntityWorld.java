package entityFramework;

public interface IEntityWorld {

	public abstract IEntityManager getEntityManager();

	public abstract IEntitySystemManager getSystemManager();

	public abstract IEntityDatabase getDatabase();

	public abstract void render();

	public abstract void update(float deltha);

}