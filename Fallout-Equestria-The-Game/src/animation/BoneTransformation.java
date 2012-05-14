package animation;

import math.Vector2;

public class BoneTransformation {
	
	public static final BoneTransformation Identity = new BoneTransformation(Vector2.Zero, Vector2.One, 0f);
	
	private Vector2 position;
	private Vector2 scale;
	private float rotation;
	
	/**
	 * Returns the identity
	 */
	public BoneTransformation() {
		this(BoneTransformation.Identity);
	}
	
	public BoneTransformation(Vector2 position, Vector2 scale, float rotation) {
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
	}
	
	private BoneTransformation(BoneTransformation other) {
		this.position = other.position;
		this.scale = other.scale;
		this.rotation = other.rotation;
	}
	public BoneTransformation clone(){
		return new BoneTransformation(this);
	}
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public Vector2 getScale() {
		return scale;
	}
	public void setScale(Vector2 scale) {
		this.scale = scale;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
}
