package GUI.controls;

import GUI.graphics.PonyBoxRenderer;
import animation.AnimationPlayer;

public class PonyBox extends GUIControl {

	private PonyBoxRenderer DEFAULT_RENDERER = new PonyBoxRenderer();
	private AnimationPlayer ponyPlayer;
	private String ponyName;

	public String getPonyName() {
		return ponyName;
	}

	public void setPonyName(String ponyName) {
		this.ponyName = ponyName;
	}

	public AnimationPlayer getPonyPlayer() {
		return ponyPlayer;
	}

	public void setPonyPlayer(AnimationPlayer ponyPlayer) {
		this.ponyPlayer = ponyPlayer;
	}
		
	public PonyBox() {
		this.setRenderer(DEFAULT_RENDERER);
	}	
}
