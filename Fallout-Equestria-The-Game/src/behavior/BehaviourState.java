package behavior;
import animation.KeyframeTriggerEventArgs;
import entityFramework.IEntity;
import utils.State;
import utils.time.GameTime;

public class BehaviourState extends State {
	public void update(GameTime time) {}
	
	public void onCollisionOver(IEntity entity){}
	public void onCollisionEnter(IEntity entity){}
	public void onCollisionExit(IEntity entity){}
	
	public void onTriggerOver(IEntity entity){}
	public void onTriggerEnter(IEntity entity){}
	public void onTriggerExit(IEntity entity){}
	public void onGroundCollision() { }
	public void onMapCollision() { }

	public void onDestroy(){}
	
	public void onKeyframeTrigger(Object sender, KeyframeTriggerEventArgs e){}

	protected void enter() { }
	protected void exit() { }

}
