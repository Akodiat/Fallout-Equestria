package components;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;
import math.Vector2;


@XStreamAlias("Transformation")
public class TransformationComp implements IComponent {
	
	private Vector2 	 position;
	private Vector2 	 scale;
	private Vector2 	 origin;
	private float 		 rotation;
	private Boolean		 mirror;
	
	public TransformationComp() {
		this.position = Vector2.Zero;
		this.scale = Vector2.One;
		this.origin = Vector2.Zero;
		this.rotation = 0;
		this.mirror = false;
	}
	
	public TransformationComp(Vector2 position, Vector2 scale, float rotation, Vector2 origin) {
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
		this.origin = origin;
		this.mirror = false;
	}
	
	private TransformationComp(TransformationComp other) {
		this.position = other.position;
		this.scale = other.scale;
		this.rotation = other.rotation;
		this.origin = other.origin;
		this.mirror = other.mirror;
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
	
	public Boolean getMirror() {
		return mirror;
	}

	public void setMirror(Boolean mirror) {
		this.mirror = mirror;
	}
	
	public String toString() {
		return "Transformation Comp" 
			+  "Position: " + this.position.toString() + "\n" 
			+  "Scale:    " + this.scale.toString() + "\n"
			+  "Origin:   "	+ this.origin.toString() + "n"
			+  "Rotation: " + this.rotation;
	}
}
