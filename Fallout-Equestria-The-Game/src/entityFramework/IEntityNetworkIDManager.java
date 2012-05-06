package entityFramework;

/**A class that makes it possible to label entities.
 * 
 * @author Lukas Kurtyan
 *
 */
public interface IEntityNetworkIDManager {
	
	/** Sets a label to a specified entity.
	 * 
	 * @param entity the entity.
	 * @param labelt the label.
	 */
	public void setNetworkIDToEntity(IEntity entity, int networkID);
	
	/** Gets the entity of a given label.
	 * 
	 * @param entityLabel the label
	 * @return the entity associated with the supplied label.
	 */
	public IEntity getEntityFromNetworkID(int networkID);
	
	/**Gets the label of a given entity.
	 * 
	 * @param entity the entity.
	 * @return gets the label of the entity.
	 */
	public int getNetworkIDFromEntity(IEntity entity);
	
	/** Removes a label from an entity.
	 * 
	 * @param entity the entity whose label we are removing.
	 */
	public void removeNetworkIDEntity(IEntity entity);
}
