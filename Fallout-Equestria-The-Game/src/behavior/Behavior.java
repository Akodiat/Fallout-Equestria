package behavior;

import animation.KeyframeTriggerEventArgs;
import components.AnimationComp;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
import sounds.SoundManager;
import utils.IEventListener;
import utils.time.GameTime;
import content.ContentManager;

public abstract class Behavior implements IEventListener<KeyframeTriggerEventArgs>{
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
		
		
		AnimationComp comp = entity.getComponent(AnimationComp.class);
		if(comp != null){
			comp.getAnimationPlayer().addKeyframeTriggerListener(this);
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
	public void onEvent(Object sender, KeyframeTriggerEventArgs e){
		this.StateMachine.onKeyframeTrigger(sender, e);
	}
	public void onMapCollision() {
		this.StateMachine.onMapCollision();
	}
		
	public void onEnable(){}
	public void onDisable(){}
	
	public <T extends IComponent> T getComponent(Class<T> componentClass) {
		return this.Entity.getComponent(componentClass);
	}
	

}
