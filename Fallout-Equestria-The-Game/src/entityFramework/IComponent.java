package entityFramework;

/** All the data that is stored inside of a entity is a component.
 * This is the interface all components needs to implement to be 
 * able to exist in the system.
 * @author Lukas Kurtyan
 *
 */
public interface IComponent {
	
	/** Creates a shallow copy of the component.
	 * This is the preferred way of creating a component after the original 
	 * has been created.
	 * @return a copy of the component.
	 */
	public Object clone();
	
	/**Gets the string representation of the component.
	 * 
	 * @return the string representation of the component.
	 */
	public String toString();
}
