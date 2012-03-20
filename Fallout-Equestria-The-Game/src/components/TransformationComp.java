package components;

import entityFramework.IComponent;
import math.Vector2;

public class TransformationComp implements IComponent {

	private Vector2 position;
	private Vector2 scale;
	private float rotation;
	
	public TransformationComp() {
		this.position = new Vector2(0,0);
		this.scale = new Vector2(1,1);
		this.rotation = 0;
	}
	
	public TransformationComp(Vector2 position, Vector2 scale, float rotation) {
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
	}
	
	public TransformationComp(TransformationComp other) {
		this.position = other.getPosition();
		this.scale = other.getScale();
		this.rotation = other.getRotation();
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
	
	
}
