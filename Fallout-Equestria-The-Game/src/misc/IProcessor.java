package misc;

import entityFramework.IEntity;
import entityFramework.IEntityManager;

public interface IProcessor {

	public abstract void validate(IEntity entity);

	public abstract void processEntity(IEntity entity, IEntityManager manager);

	public abstract void initialize(IEntityManager manager);

}