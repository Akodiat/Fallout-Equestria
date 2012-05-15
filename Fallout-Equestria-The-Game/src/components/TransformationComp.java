package components;

import anotations.Editable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;
import math.Vector2;


@XStreamAlias("Transformation")
@Editable
/**
 * 
 * @author Joakim Johansson
 *
 */
public class TransformationComp implements IComponent {
	
	//Will use vector3 for new Rendering 
	
	private @Editable Vector2 	 position;
	private @Editable Vector2 	 scale;
	private @Editable Vector2 	 origin;
	private @Editable float 	 rotation;
	private	@Editable boolean	 mirror;
	private @Editable float 	 height;
	
	public TransformationComp() {
		this(Vector2.Zero,Vector2.One,0,Vector2.Zero);
	}
	
	public TransformationComp(Vector2 position, Vector2 scale, float rotation, Vector2 origin) {
		this(position,scale,rotation,origin, false);
	}
	
	public TransformationComp(Vector2 position, Vector2 scale, float rotation, Vector2 origin, boolean mirror) {
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
		this.origin = origin;
		this.height = 0;
		this.mirror = false;
	}
	
	private TransformationComp(TransformationComp other) {
		this(other.position,other.scale,other.rotation,other.origin,other.mirror);
	}

	public TransformationComp clone(){
		return new TransformationComp(this);
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void setAllFieldsToBeLike(TransformationComp other){
		this.position = other.position;
		this.scale = other.scale;
		this.rotation = other.rotation;
		this.origin = other.origin;
		this.mirror = other.mirror;
		this.height = other.height;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public void setPosition(float x, float y) {
		this.position = new Vector2(x,y);
	}
	
	public Vector2 getOriginPosition() {
		return Vector2.subtract(this.position, this.origin);
	}
	
	/**The actual position of the object with height and normal position.
	 * 
	 * @return
	 */
	public Vector2 getWorldPosition() {
		return new Vector2(this.position.X, this.position.Y - this.height);
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
	
	
	//TODO update this to reflect mirror!
	public String toString() {
		return "Transformation Comp" 
			+  "Position: " + this.position.toString() + "\n" 
			+  "Scale:    " + this.scale.toString() + "\n"
			+  "Origin:   "	+ this.origin.toString() + "n"
			+  "Rotation: " + this.rotation;
	}
	public float getHeight() {
		
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public void setOriginPosition(Vector2 position) {
		this.position = Vector2.add(position, this.origin);
	}
}
