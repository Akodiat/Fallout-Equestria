package entityFramework;

public interface IEntityWorld {

	public abstract IEntityManager getEntityManager();

	public abstract IEntitySystemManager getSystemManager();

	public abstract IEntityDatabase getDatabase();

}