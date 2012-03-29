package entityFramework;

import com.google.common.collect.ImmutableSet;



/** A class used to represent a specific type of entity.
 * A useful way of thinking of this class is that it is 
 * a schematic of a type of entity. 
 * @author Lukas Kurtyan
 *
 */
public interface IEntityArchetype {
	
	/** Gets a Set of components.
	 * @return a set of components.
	 */
	public ImmutableSet<IComponent> getComponents();
	
	/**Gets a set of groups the component should be added to.
	 * 
	 * @return a set of groups.
	 */
	public ImmutableSet<String> getGroups();
	
	public String getLabel();
}
