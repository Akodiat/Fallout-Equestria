package misc;

import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;

public interface IProcessor {

	public abstract void validate(IEntity entity);

	public abstract void initialize(IEntityManager manager);

	void processEntity(IEntity sourceEntity, IEntityManager manager,
			IEntityArchetype specialArchtype);

}