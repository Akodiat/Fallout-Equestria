package components;


import behavior.Behavior;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import content.ContentManager;

import anotations.Editable;
import sounds.SoundManager;
import utils.input.MouseButton;
import utils.input.MouseState;
import utils.time.GameTime;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityManager;

@Editable @XStreamAlias("Behaviour")
public class BehaviourComp implements IComponent {
	@Editable
	private Behavior behavior;
	
	public BehaviourComp() {
		this((Behavior)null);
	}
	
	public BehaviourComp(Behavior behavior) {
		this.behavior = behavior;
	}
	
	public BehaviourComp(BehaviourComp other) {
		this.setBehavior((Behavior)other.getBehavior().clone());		
	}	
	
	/**Initializes the active behavior.
	 * 
	 * @param entityManager manager in use.
	 * @param entity 
	 */
	public void start(IEntityManager entityManager, 
					  ContentManager contentManager, 
					  SoundManager soundManager, 
					  IEntity entity) {
		behavior.initialize(entityManager, contentManager, soundManager, entity);
		behavior.start();
		behavior.setEnabled(true);
	}
	
	/** Delegates to the active behavior, updateBehaviour method.
	 * This is identical to calling getBehaviour.update
	 * @param state the current state of the mouse.
	 */
	public void update(GameTime time) {
		behavior.update(time);
	}

	/** Delegates to the active behavior, onCollisionOver method.
	 * This is identical to calling getBehaviour.onCollisionOver
	 * @param the entity colliding with.
	 */
	public void onCollisionOver(IEntity entity){
		this.behavior.onCollisionOver(entity);
	}
	
	/** Delegates to the active behavior, onCollisionEnter method.
	 * This is identical to calling getBehaviour.onCollisionEnter
	 * @param the entity colliding with.
	 */
	public void onCollisionEnter(IEntity entity){
		this.behavior.onCollisionEnter(entity);
	}
	
	/** Delegates to the active behavior, onCollisionExit method.
	 * This is identical to calling getBehaviour.onCollisionExit
	 * @param entity the entity colliding with.
	 */
	public void onCollisionExit(IEntity entity){
		this.behavior.onCollisionExit(entity);
	}
	
	/** Delegates to the active behavior, onTriggerOver method.
	 * This is identical to calling getBehaviour.onTriggerOver
	 * @param entity the entity interacting with.
	 */
	public void onTriggerOver(IEntity entity){
		this.behavior.onTriggerOver(entity);
	}
	
	/** Delegates to the active behavior, onTriggerEnter method.
	 * This is identical to calling getBehaviour.onTriggerEnter
	 * @param entity the entity interacting with.
	 */
	public void onTriggerEnter(IEntity entity){
		this.behavior.onTriggerEnter(entity);
	}
	
	/** Delegates to the active behavior, onTriggerExit method.
	 * This is identical to calling getBehaviour.onTriggerExit
	 * @param entity the entity interacting with.
	 */
	public void onTriggerExit(IEntity entity){
		this.behavior.onTriggerExit(entity);
	}
	
	/** Delegates to the active behavior, onTriggerExit method.
	 * This is identical to calling getBehaviour.onTriggerExit
	 */
	public void onDestroy(){
		this.behavior.onDestroy();
	}
	
	/** Delegates to the active behavior, setEnabled method.
	 * This is identical to calling getBehaviour.onCollisionOver
	 * @param enabled the enabled state.
	 */
	public void setEnabled(boolean enabled) {
		this.behavior.setEnabled(enabled);
	}
	
	/** Delegates to the active behavior, isEnabled method.
	 * This is identical to calling getBehaviour.isEnabled
	 */
	public boolean isEnabled() {
		return this.behavior.isEnabled();
	}
	
	/** Delegates to the active behavior, isInitialized method.
	 * This is identical to calling getBehaviour.isInitialized
	 */
	public boolean isInitialized() {
		return behavior.isInitialized();
	}
	
	public void onMapCollision() {
		this.behavior.onMapCollision();
	}
	
	/** Gets the Behavior of the component.
	 * @param the entity colliding with.
	 */
	public Behavior getBehavior() {
		return behavior;
	}
	
	/**Sets the new active behavior. 
	 * @param behaviour
	 */
	public void setBehavior(Behavior behaviour) {
		this.behavior = behaviour;
	}

	
	public Object clone() {
		return new BehaviourComp(this);
	}

	
}
