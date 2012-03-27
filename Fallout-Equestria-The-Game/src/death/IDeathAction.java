package death;

import entityFramework.IEntity;
import entityFramework.IEntityManager;

public interface IDeathAction {
	public abstract void excecute(IEntity deadEntity, IEntityManager entityManager);
}
