package entityFramework;

import com.google.common.collect.ImmutableSet;

/**A class used to manage groups of components.
 * 
 * @author Lukas Kurtyan
 *
 */
public interface IEntityGroupManager {
	
	/**Gets the group that has the specified name.
	 * 
	 * @param groupName the name of the group.
	 * @return a group of entities.
	 */
	public ImmutableSet<IEntity> getGroup(String groupName);
	
	/** Gets all the groups the entity is currently residing in.
	 * 
	 * @param entity the entity.
	 * @return a list of group-names.
	 */
	public ImmutableSet<String> getGroupsOfEntity(IEntity entity);
	
	/**Adds an entity to the specified group.
	 * 
	 * @param entity the entity to add.
	 * @param groupName the group that we are adding to.
	 */
	public void addEntityToGroup(IEntity entity, String groupName);

	/**Removes an entity from a specific group.
	 * 
	 * @param entity the entity to remove.
	 * @param groupName the name of the group.
	 */
	public void removeEntityFromGroup(IEntity entity, String groupName);
	
	/**Removes an entity from all groups.
	 * 
	 * @param entity the entity to remove.
	 */
	public void removeEntityFromAllGroups(IEntity entity);

}
