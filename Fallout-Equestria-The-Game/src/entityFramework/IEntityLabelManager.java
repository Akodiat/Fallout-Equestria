package entityFramework;

/**A class that makes it possible to label entities.
 * 
 * @author Lukas Kurtyan
 *
 */
public interface IEntityLabelManager {
	
	/** Sets a label to a specified entity.
	 * 
	 * @param entity the entity.
	 * @param labelt the label.
	 */
	public void setLabelToEntity(IEntity entity, String label);
	
	/** Gets the entity of a given label.
	 * 
	 * @param entityLabel the label
	 * @return the entity associated with the supplied label.
	 */
	public IEntity getEntityFromLabel(String entityLabel);
	
	/**Gets the label of a given entity.
	 * 
	 * @param entity the entity.
	 * @return gets the label of the entity.
	 */
	public String getLabelFromEntity(IEntity entity);
	
	/** Removes a label from an entity.
	 * 
	 * @param entity the entity whose label we are removing.
	 */
	public void removeLabelEntity(IEntity entity);
}
