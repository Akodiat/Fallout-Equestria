package entityFramework;

/**The interface that makes stuff happen.
 * In the everything that implements this is 
 * responsible for processing entities.  Entities
 * and their components do not change their state at ¨
 * all. This is the job of the entity systems.
 * 
 * 
 * @author Lukas Kurtyan
 *
 */
public interface IEntitySystem extends EntityChangedListener
								      ,EntityDestroyedListener		{
	/**Initializes the entity-system
	 * 
	 */
	public void initialize();
	
	/**Makes the entity-system process all of it's 
	 * entities.
	 */
	public void process();
	
	/**Gets the enabled state of the entitSystem.
	 * Disabled systems do not process entities.
	 * 
	 * @return the enabled state of the subsystem.
	 */
	public boolean getEnabled();
	
	/**Sets the enabled state of the entitySystem.
	 * Disabled systems do not process entities.
	 * @param value the new state of the entity-system.
	 */
	public void setEnabled(boolean value);
	
	/**Gets the processing order of the entity system.
	 * This is only reliable in synchronized environments.
	 * 
	 * @return the processing order.
	 */
	public int getProcessingOrder();
	
	
	/**Sets the processing order of the entity system.
	 * This is only reliable in synchronized environments.
	 * @param order the processing order.
	 */
	public void setProcessingOrder(int order);
}
