package death;

import entityFramework.IEntity;
import entityFramework.IEntityManager;

/**
 * 
 * @author Lukas Kurtyan
 *
 */
public interface IDeathAction {
	
	public abstract void excecute(IEntity deadEntity, IEntityManager entityManager);
}
