package components;

import math.Vector2;
import entityFramework.IComponent;

public class PhysicsComponent implements IComponent{
	private Vector2 velocity;
	private float mass;
	
	public PhysicsComponent(){
		this.velocity = Vector2.Zero;
		this.mass = 1f;
	}
	public PhysicsComponent(Vector2 velocity){
		this.velocity = velocity;
		this.mass = 1f;
	}
	public PhysicsComponent(Vector2 velocity, float mass){
		this.velocity = velocity;
		this.mass = mass;
	}	
	private PhysicsComponent(PhysicsComponent physComp){
		this.velocity = physComp.velocity;
		this.mass = physComp.mass;
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
	
	public String toString(){
		return "PhysicsComponent\nVelocity: "+this.velocity+"\nMass: "+this.mass;
	}
}
