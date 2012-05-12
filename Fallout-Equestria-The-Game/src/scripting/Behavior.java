package scripting;

import components.AnimationComp;
import animation.KeyframeTriggerEventArgs;
import animation.KeyframeTriggerListener;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
import utils.GameTime;
import utils.MouseButton;
import utils.MouseState;
import content.ContentManager;
import misc.SoundManager;

public abstract class Behavior implements KeyframeTriggerListener{
	protected static final String NULL_STATE_KEY = "NULL_STATE";
	
	
	protected IEntityManager EntityManager;
	protected IEntity Entity;
	protected ContentManager ContentManager;
	protected SoundManager   SoundManager;
	protected BehaviourStateMachine StateMachine;
	
	private boolean initialized;
	private boolean enabled;

	public final void initialize(IEntityManager manager, ContentManager contentManager, SoundManager soundManager, IEntity entity) {
		this.EntityManager = manager;
		this.ContentManager = contentManager;
		this.Entity = entity;
		this.SoundManager = soundManager;
		this.StateMachine = new BehaviourStateMachine();
		this.StateMachine.registerState(NULL_STATE_KEY, new NullBehaviourState());
		this.StateMachine.changeState(NULL_STATE_KEY);
		
		this.initialized = true;
		
		//This is kind of a hack :S
		AnimationComp comp = entity.getComponent(AnimationComp.class);
		if(comp != null){
			comp.getAnimationPlayer().addListener(this);
		}
	}
	
	public boolean isInitialized() {
		return initialized;
	}
		
	public final boolean isEnabled() {
		return enabled;
	}

	public final void setEnabled(boolean enabled) {
		if(enabled) {
			onEnable();
		} else {
			onDisable();
		}
		
		this.enabled = enabled;
	}
	
	public abstract void start();
	public abstract Object clone();

	public void update(GameTime time) {
		this.StateMachine.update(time);
	}
	public void onMouseOver(MouseState state){
		this.StateMachine.onMouseOver(state);
	}
	public void onMouseEnter(MouseState state){
		this.StateMachine.onMouseEnter(state);
	}
	public void onMouseExit(MouseState state){
		this.StateMachine.onMouseExit(state);
	}
	public void onMouseDown(MouseState state, MouseButton button){
		this.StateMachine.onMouseDown(state, button);
	}
	public void onMouseUp(MouseState state, MouseButton button){
		this.StateMachine.onMouseUp(state, button);
	}
	public void onMouseUpAsButton(MouseState state, MouseButton button){
		this.StateMachine.onMouseUpAsButton(state, button);
	}
	public void onMouseDrag(MouseState state){
		this.StateMachine.onMouseDrag(state);
	}
	public void onCollisionOver(IEntity entity){
		this.StateMachine.onCollisionOver(entity);
	}
	public void onCollisionEnter(IEntity entity){
		this.StateMachine.onCollisionEnter(entity);
	}
	public void onCollisionExit(IEntity entity){
		this.StateMachine.onCollisionExit(entity);
	}
	public void onTriggerOver(IEntity entity){
		this.StateMachine.onTriggerOver(entity);
	}
	public void onTriggerEnter(IEntity entity){
		this.StateMachine.onTriggerEnter(entity);
	}
	public void onTriggerExit(IEntity entity){
		this.StateMachine.onTriggerExit(entity);
	}
	public void onGroundCollision() { 
		this.StateMachine.onGroundCollision();
	}
	public void onDestroy(){
		this.StateMachine.onDestroy();
	}
	public void onKeyframeTrigger(Object sender, KeyframeTriggerEventArgs e){
		this.StateMachine.onKeyframeTrigger(sender, e);
	}
		
	public void onEnable(){}
	public void onDisable(){}
	
	public <T extends IComponent> T getComponent(Class<T> componentClass) {
		return this.Entity.getComponent(componentClass);
	}
	

}
