package components;

import entityFramework.IComponent;
import math.Vector2;

public class TransformationComp implements IComponent {
	private Vector2 	 position;
	private Vector2 	 scale;
	private Vector2 	 origin;
	private float 		 rotation;
	
	public TransformationComp() {
		this.position = Vector2.Zero;
		this.scale = Vector2.One;
		this.origin = Vector2.Zero;
		this.rotation = 0;
	}
	
	public TransformationComp(Vector2 position, Vector2 scale, float rotation, Vector2 origin) {
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
		this.origin = origin;
	}
	
	private TransformationComp(TransformationComp other) {
		this.position = other.position;
		this.scale = other.scale;
		this.rotation = other.rotation;
		this.origin = other.origin;
	}
	
	public TransformationComp clone(){
		return new TransformationComp(this);
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public void setPosition(float x, float y) {
		this.position = new Vector2(x,y);
	}
	
	public Vector2 getScale() {
		return scale;
	}
	
	public void setScale(Vector2 scale) {
		this.scale = scale;
	}
	
	public void setScale(float x, float y) {
		this.scale = new Vector2(x,y);
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public Vector2 getOrigin() {
		return origin;
	}

	public void setOrigin(Vector2 origin) {
		this.origin = origin;
	}
}
