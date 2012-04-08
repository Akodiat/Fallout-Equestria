package components;

import math.Vector2;
import entityFramework.IComponent;

import anotations.Editable;
import anotations.EditableComponent;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Physics")
@EditableComponent
public class PhysicsComp implements IComponent{
	@Editable
	private boolean immovable;
	private Vector2 velocity;
	@Editable
	private float mass;
	private float torque;
	
	/**
	 * @return the mass
	 */
	public float getMass() {
		return mass;
	}
	/**
	 * @param mass the mass to set
	 */
	public void setMass(float mass) {
		this.mass = mass;
	}

	//TODO add constructor chaining 
	public PhysicsComp(){
		this.velocity = Vector2.Zero;
		this.mass = 1f;
		this.immovable = (false);
		this.torque = 0;
	}
	public PhysicsComp(Vector2 velocity){
		this.velocity = velocity;
		this.mass = 1f;
		this.immovable = (false);
		this.torque = 0;
	}
	public PhysicsComp(Vector2 velocity, float mass, float torque, boolean immovable){
		this.velocity = velocity;
		this.mass = mass;
		this.immovable = false;
		this.torque = torque;
	}	
	private PhysicsComp(PhysicsComp physComp){
		this.velocity = physComp.velocity;
		this.mass = physComp.mass;
		this.immovable = physComp.immovable;
		this.torque = physComp.torque;
	}
	
	public Object clone(){
		return new PhysicsComp(this);
	}

	public float getTorque() {
		return torque;
	}
	public void setTorque(float torque) {
		this.torque = torque;
	}
	public boolean isImmovable() {
		return immovable;
	}
	public void setImmovable(boolean immovable) {
		this.immovable = immovable;
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
