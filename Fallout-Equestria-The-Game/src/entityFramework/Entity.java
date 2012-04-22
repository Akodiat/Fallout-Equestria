package entityFramework;

import java.util.BitSet;

import com.google.common.collect.ImmutableSet;


/** The class representing all the objects in a game.
 * This class is the whole idea of an entity framework 
 * it is basically a unique integer identification with 
 * a bunch of helper methods making it easy to work with.
 * @author Lukas Kurtyan
 */
public final class Entity extends IEntity {

	//The unique identification of this entity
	private final int uniqueID;
	//A set representing the diffrent components this entity has.
	private final BitSet componentBits;
	
	//The manager of this entity.
	private final IEntityManager manager;
	//The data-source of this entity.
	private final IEntityDatabase database;
	
	
	/**The Constructor of the entity. 
	 * This is protected since an entity should only be created using the EntityFactory.
	 * 
	 * @param uniqueID the unique id of the entity.
	 * @param manager the manager that manages the entity.
	 * @param database the database containing the components of the entity.
	 */
	protected Entity(int uniqueID, IEntityManager manager, IEntityDatabase database) {
		this.uniqueID = uniqueID;
		this.manager = manager;
		this.database = database;
		this.componentBits = new BitSet(100);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImmutableSet<IComponent> getComponents() {
		return this.database.getComponents(this.uniqueID);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends IComponent> T getComponent(Class<T> componentClass) {
		return this.database.getComponent(this.uniqueID, componentClass);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getUniqueID() {
		return this.uniqueID;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BitSet getComponentBits() {
		return this.componentBits;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImmutableSet<String> getGroups() {
		return this.manager.getGroupsOfEntity(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addToGroup(String groupName) {
		this.manager.groupEntity(this, groupName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeFromGroup(String groupName) {
		this.manager.ungroupEntity(this, groupName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLabel(String label) {
		this.manager.labelEntity(this, label);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLabel() {
		return this.manager.getEntityLabel(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void refresh() {
		this.manager.refreshEntity(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void kill() {
		this.manager.killEntity(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addComponent(IComponent component) {
		this.database.setComponent(uniqueID, component);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void addComponentBit(BitSet componentBit) {
		this.componentBits.or(componentBit);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void removeComponentBit(BitSet componentBit) {
		this.componentBits.andNot(componentBit);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void clearBits() {
		this.componentBits.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeComponent(IComponent component) {
		this.removeComponent(component.getClass());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeComponent(Class<? extends IComponent> componentClass) {
		this.database.deleteComponent(uniqueID, componentClass);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearComponents() {
		this.database.clearEntity(uniqueID);
	}
	
	public String toString() {
//		ImmutableSet<IComponent> components = this.getComponents();
//		StringBuilder builder = new StringBuilder();
//		String label = this.getLabel();
//		builder.append("Entity: " + "ID: " + this.getUniqueID() + " Name: " + label + " NumComponents: " + components.size()+ "\n");
//		for (IComponent component : components) {
//			builder.append(component.toString() + "\n");
//		}
//		return builder.toString();
		
		return this.uniqueID + " " + this.getLabel();
	}

	@Override
	public boolean isInGroup(String groupName) {
		return this.getGroups().contains(groupName);
	}
}