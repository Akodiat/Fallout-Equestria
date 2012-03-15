package entityFramework;

/*
 * A helper class for communicating the the database.
 * This class gets components out of the database, it's using 
 * the most optimized way of doing this. If you are ever using 
 * an entity system the preferred way of getting access to components
 * is through this class.
 * @Uses use this class if you need to get components out of the database.
 * @author Lukas Kurtyan
 */
public class ComponentMapper<T extends IComponent> {
	
	//The unique id of the specified componentType.
	private final int componentMappedID;
	//The database working on.
	private final IEntityDatabase database;
	
	/* Constructor for componentMapper.
	 * @param database the entityDatabase this componentMapper uses.
	 * @param componentType the type of component this can map to.
	 */
	private ComponentMapper(IEntityDatabase database, Class<T> componentType) {
		this.database = database;
		this.componentMappedID = database.getComponentTypeID(componentType);
	}
	
	/**
	 * Creates a new instance of ComponentMapper.
	 * Generic constructors in java are EVIL that is why we have this.
	 * @param database the database the component-mappper gets mapped to.
	 * @param componentClass the class of components this componentType maps to. 
	 * @return a new instance of a ComponentMapper.
	 */
	public static<C extends IComponent> ComponentMapper<C> create(IEntityDatabase database, Class<C> componentClass) {
		return new ComponentMapper<C>(database, componentClass);
	}
	
	/*Gets a component out of the database.
	 * @param entity the entity whose component we are after. 
	 * @returns a component if it exists or null if not. 
	 */
	public T getComponent(IEntity entity) {
		//Retrieves the component from the database.
		return this.database.getComponent(entity.getUniqueID(), componentMappedID);
	}
}
