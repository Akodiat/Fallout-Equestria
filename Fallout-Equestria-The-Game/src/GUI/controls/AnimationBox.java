package GUI.controls;

import GUI.graphics.AnimationBoxRenderer;
import animation.AnimationPlayer;

public class AnimationBox extends GUIControl {

	private AnimationBoxRenderer DEFAULT_RENDERER = new AnimationBoxRenderer();
	private AnimationPlayer player;
	
	public AnimationPlayer getAnimationPlayer() {
		return player;
	}

	public void setAnimationPlayer(AnimationPlayer player) {
		this.player = player;
	}
		
	public AnimationBox() {
		this.setRenderer(DEFAULT_RENDERER);
	}	
}
