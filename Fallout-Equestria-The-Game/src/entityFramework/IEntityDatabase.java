package entityFramework;

import java.util.BitSet;
import com.google.common.collect.ImmutableSet;

/** A database where all the entities of the system is stored.
 * 
 * @author Lukas Kurtyan
 *
 */
public interface IEntityDatabase extends java.io.Serializable{
	
	/**Gets a component from the specified entity of the specified class.
	 * @param entity the specified entity.
	 * @param componentType the specified component class.
	 * @return a component of the specified type, or null.
	 */
	public <T extends IComponent> T getComponent(int entity, Class<T> componentType);
	
	/**
	 * Gets a component from the specified entity with the specified componentID.
	 * This is an optimization method, to get the correct componentID
	 * use the getComponentTypeID method. 
	 * @param entityID the specified entity.
	 * @param componentID the id of the componentClass you want.
	 * @return a component of the specified componentID, or null.
	 */
	public <T extends IComponent> T getComponent(int entityID, int componentID); 
	
	/** Sets the component of the specified entity. 
	 * 
	 * @param entityID the specified entity.
	 * @param component the component to set.
	 */
	public void setComponent(int entityID, IComponent component);
	
	/**Sets the component of the specified entity with the specified componentID.
	 * This is an optimization method, to get the correct componentID
	 * use the getComponentTypeID method. 
	 * 
	 * @param entityID the specified entity. 
	 * @param componentID the id of the componentClass you want.
	 * @param component the component to set.
	 */
	public void setComponent(int entityID, int componentID, IComponent component);
	
	/**Deletes the component from the database. 
	 * 
	 * @param entityID the specified entity.
	 * @param componentType the class of component.
	 */
	public void deleteComponent(int entityID,Class<? extends IComponent> componentType);
	
	/**Deletes the component from the database.
	 * This is an optimization method, to get the correct componentID
	 * use the getComponentTypeID method. 
	 * @param entityID the specified entity.
	 * @param componentTypeID the class of component.
	 */
	public void deleteComponent(int entityID, int componentTypeID);
	
	/**Gets the component-id of the specified component-class.
	 * 
	 * @param componentType the component-class.
	 * @return a componentID.
	 */
	public int getComponentTypeID(Class<? extends IComponent> componentType);
	
	/**Gets the bit of the specified component-class.
	 * 
	 * @param componentType the class of component. 
	 * @return the bit associated with the specified component-class.
	 */
	public BitSet getComponentTypeBit(Class<? extends IComponent> componentType);

	/**Gets all of the components of a given entity.
	 * 
	 * @param entity the entity.
	 * @return a Set of components.
	 */
	public ImmutableSet<IComponent> getComponents(IEntity entity);
	
	/**Gets all the components of a given entity.
	 * 
	 * @param entityID the specified entity.
	 * @return a Set of components.
	 */
	public ImmutableSet<IComponent> getComponents(int entityID);
	
	/**Gets all the entities that has a specific component.
	 * 
	 * @param component the specific component. 
	 * @return a Set of entities.
	 */
	public ImmutableSet<IEntity> getEntitysContainingComponent(Class<? extends IComponent> component);
	
	/**Gets all the entities that has all the specified components.
	 * 
	 * @param components the types of the components 
	 * @return a Set of entities.
	 */
	@SuppressWarnings("unchecked")
	public ImmutableSet<IEntity> getEntitysContainingComponents(Class<? extends IComponent>... components);
	
	
	/**Gets all the entities that has a specific component.
	 * This is an optimization method, to get the correct componentID
	 * use the getComponentTypeID method. 
	 * @param componentID the specific component.
	 * @return a Set of entities. 
	 */
	public ImmutableSet<IEntity> getEntitysContainingComponent(int componentID);
	
	/**Adds an entity to the database.
	 * 
	 * @param entity the entity to add.
	 */
	public void addEntity(IEntity entity);
	
	/** Removes an entity from the database.
	 * 
	 * @param entity the entity to remove.
	 */
	public void removeEntity(IEntity entity);
	
	/** Removes an entity from the database.
	 * 
	 * @param entityID the entity to remove.
	 */
	public void removeEntity(int entityID);
	
	/**Gets the entity that has the specified id. 
	 * 
	 * @param entityID the id of the entity to get.
	 * @return the entity with the specified id.
	 */
	public IEntity getEntity(int entityID);
	
	/**Removes all the components in the specified entity.
	 * 
	 * @param entityID the entity of the specified id.
	 */
	public void clearEntity(int entityID);
	
	/** Returns the number of entities inside the database.
	 * 
	 * @return the number of entities inside the database.
	 */
	public int getEntityCount();
	
	/**Returns all the entities in the database.
	 * 
	 * @return a Set of entities.
	 */
	public ImmutableSet<IEntity> getAllEntities();
	
	/**Clears the entire database.
	 */
	public void clear();
}
