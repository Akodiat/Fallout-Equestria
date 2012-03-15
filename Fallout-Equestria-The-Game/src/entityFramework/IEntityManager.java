package entityFramework;

import com.google.common.collect.ImmutableSet;

/** A class that manages entities.
 * This deals with all communication with the database 
 * and the EntitySystem. Anything related to entities. 
 * @author Lukas Kurtyan
 *
 */
public interface IEntityManager {
	
	/**Creates an empty entity.
	 * 
	 * @return a empty entity.
	 */
	public IEntity createEmptyEntity();
	
	/**Creates an entity from the supplied archetype.
	 * 
	 * @param archetype the archetype of the entity.
	 * @return a entity.
	 */
	public IEntity createEntity(IEntityArchetype archetype);
	
	/** Creates an entity populated with the list of components.
	 * 
	 * @param components the components of the entity.
	 * @return a entity.
	 */
	public IEntity createEntity(ImmutableSet<IComponent> components);
	
	/**Gets the entity with the specified id.
	 * 
	 * @param entityID the id of the entity to get.
	 * @return an entity.
	 */
	public IEntity getEntity(int entityID);
	
	/**Gets the entity that has the specified entityLabel.
	 * 
	 * @param entityLabel the label of the entity we are looking for.
	 * @return an entity.
	 */
	public IEntity getEntity(String entityLabel);
	
	/**Gets a group of entities.
	 * 
	 * @param groupID the groupID of the entities.
	 * @return a group of entities.
	 */
	public ImmutableSet<IEntity> getEntityGroup(String groupID);
	
	/**Labels a specific entity with the supplied label.
	 * 
	 * @param entity the entity to label.
	 * @param label the label.
	 */
	public void labelEntity(IEntity entity, String label);
	
	/**Add an entity to the specified group.
	 * 
	 * @param entity the entity
	 * @param entityGroup the group.
	 */
	public void groupEntity(IEntity entity, String entityGroup);
	
	/**Removes the label of an entity.
	 * 
	 * @param entity the entity.
	 */
	public void unLabelEntity(IEntity entity);
	
	/**Removes the entity for the specified group. 
	 * 
	 * @param entity the entity.
	 * @param entityGroup the group.
	 */
	public void ungroupEntity(IEntity entity, String entityGroup);
	
	/**Gets the label of an entity.
	 * 
	 * @param entity the entity 
	 * @return the label of an entity.
	 */
	public String getEntityLabel(IEntity entity);
	
	/**Gets all the groups of a specific entity.
	 * 
	 * @param entity the entity.
	 * @return a set of group-names.
	 */
	public ImmutableSet<String> getGroupsOfEntity(IEntity entity);
	
	/**Deletes all of the killed entities. 
	 */
	public void destoryKilledEntities();
	
	/**Adds a listener for the EntityChangedEvent.
	 * This event gets called if refreshEntity or
	 * refreshDatabase get's called.
	 * 
	 * @param changedListener the lister to add.
	 */
	public void addEntityChangedListener(EntityChangedListener changedListener);
	
	/**Removes a listener for the EntityChangedEvent.
	 * This event gets called if refreshEntity or
	 * refreshDatabase get's called.
	 * 
	 * @param changedListener the lister to remove.
	 */
	
	public void removeEntityChangedListener(EntityChangedListener changedListener);
	
	/**Adds a listener for the EntityDestroyedEvent.
	 * This event gets called whenever a entity gets destroyed.
	 * 
	 * @param destroyedListener the listener to add.
	 */
	public void addEntityDestoryedListener(EntityDestroyedListener destroyedListener);
	
	/**Removes a listener for the EntityDestroyedEvent
	 * 
	 * @param destroyedListener the listener to remove.
	 */
	public void removeEntityDestoryedListener(EntityDestroyedListener destroyedListener);
	
	/**Refreshes the entity.
	 * This get's the EntitySystem in sync with the refreshed entity.
	 * 
	 * @param entity the entity to refresh.
	 */
	public void refreshEntity(IEntity entity);
	
	/**Refreshes all the entities in the database.
	 */
	public void refreshDatabase();
	
	/**Kills the specified entity. 
	 * This will be removed when the next destroyEntities
	 * gets called.
	 * @param entity the entity to kill.
	 */
	public void killEntity(IEntity entity);	
}
