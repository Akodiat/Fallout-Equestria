package entityFramework;

import com.google.common.collect.ImmutableSet;

/**A class responsible for creating entities.
 * This class can create entities through a vararity of different 
 * methods.
 * @author Lukas Kurtyan
 *
 */
public interface IEntityFactory {
	
	/** Makes a removed entity available for reuse.
	 * 
	 * @param entity the entity that can be reused.
	 */
	public void makeEntityAvalible(IEntity entity);
	
	/** Creates an empty entity.
	 * 
	 * @param manager the manager the entities are managed by.
	 * @param dataBase the database that contains the data of the components.
	 * @return a entity.
	 */
	public IEntity createEmptyEntity(IEntityManager manager, IEntityDatabase dataBase);
	
	/** Creates an entity of the specified archetype.
	 * 
	 * @param archetype a blueprint of an entity. 
	 * @param manager the manager the entities are managed by.
	 * @param dataBase the database that contains the data of the components.
	 * @return a entity.
	 */
	public IEntity createEntity(IEntityArchetype archetype, IEntityManager manager, IEntityDatabase database);

	/** Creates an entity of the specified archetype.
	 * 
	 * @param archetype components the components that should be inserted into the entity. 
	 * @param manager the manager the entities are managed by.
	 * @param dataBase the database that contains the data of the components.
	 * @return a entity.
	 */
	public IEntity createEntity(ImmutableSet<IComponent> components,EntityManager manager, IEntityDatabase database);
}
