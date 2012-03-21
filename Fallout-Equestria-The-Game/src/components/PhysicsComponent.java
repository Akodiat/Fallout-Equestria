package components;

import math.Vector2;
import entityFramework.IComponent;

public class PhysicsComponent implements IComponent{
	private Vector2 velocity;
	
	public PhysicsComponent(){
		this.velocity = Vector2.Zero;
	}
	
	public PhysicsComponent(Vector2 velocity){
		this.velocity=velocity;
	}
	
	private PhysicsComponent(PhysicsComponent physComp){
		this.velocity=physComp.velocity;
	}
	
	public Object clone(){
		return new PhysicsComponent(this);
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
