package entityFramework;

import java.util.BitSet;

import com.google.common.collect.ImmutableSet;


/**The whole idea of behind an entity system the entity.
 * An entity is basically a unique id that maps into a row
 * in a Relative Database. This interface is just that
 * however this contains allot of helper methods for
 * making the IEntyt-IEntityDatabase easy to manage.
 * 
 * @author Lukas Kurtyan
 */
public abstract class IEntity {
	
	/** Gets the list of components contained of the entity.
	 * @return a list of all the components in the entity.
	 */
	public abstract ImmutableSet<IComponent> getComponents();
	
	/**Gets a specific component of the entity
	 * 
	 * @param componentClass the specific class of component to get.
	 * @return a component.
	 */
	public abstract  <T extends IComponent> T getComponent(Class<T> componentClass);
	
	/** Adds a component to the entity.
	 * If a component of the particular entity type already exists then
	 * it is overridden.
	 * @param component the component to add.
	 */
	public abstract void addComponent(IComponent component);
	
	/** Removes a specific component from the entity.
	 * @param component the component to remove.
	 */
	public abstract void removeComponent(IComponent component);
	
	/**Removes a particular type of component from the entity.
	 * @param componentClass the class of the component removing.
	 */
	public abstract void removeComponent(Class<? extends IComponent> componentClass);
	
	/** Removes all the components from the entity.
	 */
	public abstract void clearComponents();
	
	/** Gets the unique id of the entity.
	 * 
	 * @return an integer representing the id of the entity.
	 */
	public abstract int getUniqueID();
	
	/**Gets all the names of the groups this entity is currently in.
	 * @return the groups of the entity.
	 */
	public abstract ImmutableSet<String> getGroups();
	
	/**
	 * Adds this entity to a particular group.
	 * @param groupName the name of the group.
	 */
	public abstract void addToGroup(String groupName);
	
	/**
	 * Removes this entity from the specified group.
	 * @param groupName the name of the group.
	 */
	public abstract void removeFromGroup(String groupName);

	/**
	 * Sets a label to this entity.
	 * The label can be used to retrieve this entity through
	 * the IEntityManager. 
	 * @param label the label we wish to set.
	 */
	public abstract void setLabel(String label);
	
	/**
	 * Gets the label of this entity.
	 * @return the label of the entity.
	 */
	public abstract String getLabel();
	
	/**
	 * Gets the bitset representing what components this entity contains.
	 * @return a bitset representing what components this entity contains.
	 */
	public abstract BitSet getComponentBits();
	
	/**
	 * Adds a component bit to this entity.
	 * @param bitSet the component-bit to add.
	 */
	protected abstract void addComponentBit(BitSet bitSet);
	
	/**
	 * Removes a component bit from this entity.
	 * @param bitSet the component bit to remove.
	 */
	protected abstract void removeComponentBit(BitSet bitSet);
	
	/**
	 * Clears the bits in the component bitset.
	 */
	protected abstract void clearBits();
	
	/** Refreshes the entity making it broadcast any changes.
	 * This method must be called after any new components 
	 * are added or removed or the EntitySystems will
	 * not stay in sync.
	 */
	public abstract void refresh();
	
	/** Kills the entity.
	 * This will destroy the entity at the end of a logic/render cycle.
	 */
	public abstract void kill();
	
 }
