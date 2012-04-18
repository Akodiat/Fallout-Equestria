package animation;

import math.Vector2;

public class BoneTransitionState {
	
	public static final BoneTransitionState Identity = new BoneTransitionState(Vector2.Zero, 0f);

	private Vector2 position;
	private float rotation;
	
	public BoneTransitionState(Vector2 position, float rotation) {
		this.position = position;
		this.rotation = rotation;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
}
