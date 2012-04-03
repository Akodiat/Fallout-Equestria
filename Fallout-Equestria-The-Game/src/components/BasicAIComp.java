package components;

import math.Vector2;
import entityFramework.IComponent;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BasicAI")
@EditableComponent
public class BasicAIComp implements IComponent {
	private Vector2 target; //Location of the target to shoot at (and walk against)
	
	public BasicAIComp(){
		this.target = Vector2.Zero;
	}
	public BasicAIComp(Vector2 target){
		this.target = target;
	}
	private BasicAIComp(BasicAIComp other){
		this.target = other.target;
	}
	
	public Object clone() {
		return new BasicAIComp(this);
	}
	/**
	 * @return the target
	 */
	public Vector2 getTarget() {
		return target;
	}
	/**
	 * @param target the target to set
	 */
	public void setTarget(Vector2 target) {
		this.target = target;
	}
	
	public String toString() {
		return "BasicAI: \n" 
			+  "Target: " + this.target.toString();
	}

}
