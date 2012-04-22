package scripting;

import components.AnimationComp;
import components.TransformationComp;

import animation.KeyframeTriggerEventArgs;
import animation.KeyframeTriggerListener;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
import utils.GameTime;
import utils.MouseState;

public abstract class BehaviourScript implements KeyframeTriggerListener{
	
	protected IEntityManager entityManager;
	protected IEntity entity;
	private boolean initialized;
	private boolean enabled;

	public final void initialize(IEntityManager manager, IEntity entity) {
		this.entityManager = manager;
		this.entity = entity;
		this.initialized = true;
		
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
		this.enabled = enabled;
	}
	
	public abstract void start();
	public abstract BehaviourScript createNew();
	public void onHit(IEntity other) {}
	public void update(GameTime time) {}
	
	public void onMouseOver(MouseState state){}
	public void onMouseEnter(MouseState state){}
	public void onMouseExit(MouseState state){}
	public void onMouseDown(MouseState state){}
	public void onMouseUp(MouseState state){}
	public void onMouseUpAsButton(MouseState state){}
	public void onMouseDrag(MouseState state){}
	
	public void onCollisionOver(IEntity entity){}
	public void onCollisionEnter(IEntity entity){}
	public void onCollisionExit(IEntity entity){}
	
	public void onTriggerOver(IEntity entity){}
	public void onTriggerEnter(IEntity entity){}
	public void onTriggerExit(IEntity entity){}
	
	public void onEnable(){}
	public void onDisable(){}
	public void onDestroy(){}
	
	public <T extends IComponent> T getComponent(Class<T> componentClass) {
		return this.entity.getComponent(componentClass);
	}
	
	public void onKeyframeTrigger(Object sender, KeyframeTriggerEventArgs e){}
}
