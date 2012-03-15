package components;

import math.Vector2;
import entityFramework.IComponent;

public class PhysicsComponent implements IComponent{
	private Vector2 velocity;
	
	
	public Object clone(){
		PhysicsComponent comp = new PhysicsComponent();
		comp.setVelocity(velocity);
		return comp;
	}


	/**
	 * @return the velocity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}


	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
}
