package behavior;

import animation.KeyframeTriggerEventArgs;
import entityFramework.IEntity;
import utils.StateMachine;
import utils.time.GameTime;

public class BehaviourStateMachine extends StateMachine<BehaviourState>{
	public void update(GameTime time) {
		this.activeState.update(time);
	}
	public void onCollisionOver(IEntity entity){
		this.activeState.onCollisionOver(entity);
	}
	public void onCollisionEnter(IEntity entity){
		this.activeState.onCollisionEnter(entity);
	}
	public void onCollisionExit(IEntity entity){
		this.activeState.onCollisionExit(entity);
	}
	public void onTriggerOver(IEntity entity){
		this.activeState.onTriggerOver(entity);
	}
	public void onTriggerEnter(IEntity entity){
		this.activeState.onTriggerEnter(entity);
	}
	public void onTriggerExit(IEntity entity){
		this.activeState.onTriggerExit(entity);
	}
	public void onGroundCollision() { 
		this.activeState.onGroundCollision();
	}
	public void onDestroy(){
		this.activeState.onDestroy();
	}
	public void onKeyframeTrigger(Object sender, KeyframeTriggerEventArgs e){
		this.activeState.onKeyframeTrigger(sender, e);
	}
	public void onMapCollision() {
		this.activeState.onMapCollision();
	}
}
