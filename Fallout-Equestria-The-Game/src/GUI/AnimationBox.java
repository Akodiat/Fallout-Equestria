package GUI;

import math.Vector2;
import animation.AnimationPlayer;

public class AnimationBox extends GUIControl {

	private AnimationBoxRenderer DEFAULT_RENDERER = new AnimationBoxRenderer();
	private AnimationPlayer player;
	private Vector2 scale;
	
	public AnimationPlayer getAnimationPlayer() {
		return player;
	}

	public void setAnimationPlayer(AnimationPlayer player) {
		this.player = player;
	}
		
	public AnimationBox() {
		this.setRenderer(DEFAULT_RENDERER);
	}

	public Vector2 getScale() {
		return scale;
	}

	public void setScale(Vector2 scale) {
		this.scale = scale;
	}	
}
