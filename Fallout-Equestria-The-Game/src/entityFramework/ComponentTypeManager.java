package entityFramework;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**A helper class used to manage the types of components.
 * This class maps each component type to a unique integer identification
 * and a unique bit in a bitvector.
 * @author Lukas Kurtyan
 */
public class ComponentTypeManager {
	
	//The unique integer of the next component-type.
	private int nextID = 0;
	
	//The unique bit of the next component-type.
	private BitSet nextTypeBit;
	
	//A map from a class of component to a specific component-type.
	private final Map<Class<? extends IComponent>, ComponentType> componentTypes;
	//A map from an integer value to it's corresponding component-type.
	private final Map<Integer, ComponentType> intToomponentTypes;
	
	/**Gets the number of types encountered.
	 * @return the count of different component classes processed.
	 */
	public int getTypeCount() {
		return componentTypes.size();
	}
	
	/**Constructor of ComponentTypeManager
	 */
	public ComponentTypeManager() {
		nextTypeBit = new BitSet();
		nextTypeBit.set(0);
		intToomponentTypes = new HashMap<Integer,ComponentType>();
		componentTypes 	= new HashMap<Class<? extends IComponent>,
												 ComponentType>();
	}
	
	/**Gets the bit associated with the specified component-class
	 * @param componentClass the type of component whose bit we are after.
	 * @returns a bit in the form of a BitSet.
	 */
	public BitSet getTypeBit(Class<? extends IComponent> componentClass) {
		
		ComponentType type = componentTypes.get(componentClass);
		if(type == null) {
			type = this.createComponentType(componentClass);
		}
		return type.bit;
	}
	
	/**Gets the bit associated with the specific componentID.
	 * This method should not be used unless you are sure you 
	 * know what you are doing. It is an optimization.
	 * @param componentID the Id of the component-bit we are after.
	 * @returns a bit in the form of a BitSet.
	 */
	protected BitSet getTypeBit(int componentID) {
		return this.intToomponentTypes.get(componentID).bit;
	}
	
	/**Gets the integer id associated with the specified component.
	 * @param componentClass the type of component whose bit we are after.
	 * @returns an integer id.
	 */
	public int getTypeID(Class<? extends IComponent> componentClass) {
		ComponentType type = componentTypes.get(componentClass);
		if(type == null) {
			type = this.createComponentType(componentClass);
		}
		return type.id;
	}
	
	private ComponentType createComponentType(Class<? extends IComponent> componentClass) {

		//Creates the next component type.	
		ComponentType type = new ComponentType(nextID, nextTypeBit);
		//Setups the bit and id for the next component-type
		nextTypeBit.set(nextID++, false);
		nextTypeBit.set(nextID);
		
		//Adds the component-type to the maps.
		this.intToomponentTypes.put(type.id, type);
		this.componentTypes.put(componentClass, type);
		
		return type;
	}

	/**A helper class reducing the need to have many different maps.
	 */
	private class ComponentType {
		final int id;
		final BitSet bit;
		
		public ComponentType(int typeID, BitSet typeBit) {
			this.id = typeID;
			this.bit = (BitSet)typeBit.clone();
		}
	}
}
