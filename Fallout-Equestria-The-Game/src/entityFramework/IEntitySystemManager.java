package entityFramework;
/**This is the interface of the system that manages entities.
 * 
 * @author LukasFiddle
 *
 */
public interface IEntitySystemManager {
	
	/**Adds a logic system to the System manager.
	 * 
	 * @param logicSystem a EntitySystem devoted to game logic.
	 */
	public void addLogicEntitySystem(IEntitySystem logicSystem);
	
	/**Removes a logic system form the SystemManager
	 * 
	 * @param logicSystem a EntitySystem devoted to game logic.
	 */
	public void removeLogicEntitySystem(IEntitySystem logicSystem);
	
	/**Adds a render system to the System manager.
	 * 
	 * @param renderSystem a entitySystem devoted to rendering.
	 */
	public void addRenderEntitySystem(IEntitySystem renderSystem);
	
	/**Removes a render system to the system manager.
	 * 
	 * @param renderSystem a entitySystem devoted to rendering.
	 */
	public void removeRenderEntitySystem(IEntitySystem renderSystem);
	
	/**Initializes the SystemManager and any entity-systems inside it.
	 * If you ad entitySystems after this point you will have to initialize them manually.
	 * 
	 */
	public void initialize();
	
	/**Makes all the enabled logicSystem to do logic.
	 * 
	 */
	public void logic();
	
	/**Makes all the enabled render-systems to render.
	 * 
	 */
	public void render();
}
