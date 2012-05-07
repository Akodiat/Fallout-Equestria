package components;

import math.Vector2;
import entityFramework.IComponent;
import anotations.Editable;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * @author Joakim Johansson
 *
 */
@XStreamAlias("Physics") @Editable
public class PhysicsComp implements IComponent{
	
	private @Editable boolean immovable;
	private @Editable float mass;
	
	private Vector2 velocity;
	private float torque;
	private float heightVelocity;
	
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
		this(Vector2.Zero,1f,0,false);
	}
	public PhysicsComp(Vector2 velocity){
		this(velocity,1f,0,false);
	}	
	private PhysicsComp(PhysicsComp other){
		this(other.velocity, other.mass, other.torque, other.immovable);
	}
	public PhysicsComp(Vector2 velocity, float mass, float torque, boolean immovable){
		this.velocity = velocity;
		this.mass = mass;
		this.immovable = false;
		this.torque = torque;
	}
	public void setAllFieldsToBeLike(PhysicsComp other){
		this.velocity = other.velocity;
		this.mass = other.mass;
		this.immovable = other.immovable;
		this.torque = other.torque;
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
	public float getHeightVelocity() {
		return heightVelocity;
	}
	public void setHeightVelocity(float heightVelocity) {
		this.heightVelocity = heightVelocity;
	}
}
