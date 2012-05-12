package scripting;
import animation.KeyframeTriggerEventArgs;
import entityFramework.IEntity;
import state.State;
import utils.GameTime;
import utils.MouseButton;
import utils.MouseState;

public class BehaviourState extends State {
	public void update(GameTime time) {}
	
	public void onMouseOver(MouseState state){}
	public void onMouseEnter(MouseState state){}
	public void onMouseExit(MouseState state){}
	
	public void onMouseDown(MouseState state, MouseButton button){}
	public void onMouseUp(MouseState state, MouseButton button){}
	public void onMouseUpAsButton(MouseState state, MouseButton button){}
	public void onMouseDrag(MouseState state){}
	
	public void onCollisionOver(IEntity entity){}
	public void onCollisionEnter(IEntity entity){}
	public void onCollisionExit(IEntity entity){}
	
	public void onTriggerOver(IEntity entity){}
	public void onTriggerEnter(IEntity entity){}
	public void onTriggerExit(IEntity entity){}
	public void onGroundCollision() { }
	
	public void onDestroy(){}
	
	public void onKeyframeTrigger(Object sender, KeyframeTriggerEventArgs e){}

	protected void enter() { }
	protected void exit() { }
}
