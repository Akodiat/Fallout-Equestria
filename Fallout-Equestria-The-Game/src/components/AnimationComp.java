package components;

import animation.AnimationPlayer;
import anotations.Editable;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import entityFramework.IComponent;
import graphics.Color;


@XStreamAlias("Animation") @Editable
public class AnimationComp implements IComponent {

	private @Editable AnimationPlayer animationPlayer;
	
	private @Editable Color tint;
	
	public AnimationComp() {
		this.animationPlayer = new AnimationPlayer();
	}
	
	public AnimationComp(AnimationPlayer animationPlayer) {
		this.animationPlayer = animationPlayer;
		this.tint = Color.White;
	}
	
	public AnimationComp(AnimationComp other) {
		this.animationPlayer = other.animationPlayer.clone();
	}
	
	public AnimationComp clone() {
		return new AnimationComp(this);
	}

	public AnimationPlayer getAnimationPlayer() {
		return animationPlayer;
	}

	public void setAnimationPlayer(AnimationPlayer animationPlayer) {
		this.animationPlayer = animationPlayer;
	}
	
	public void changeAnimation(String animationName, boolean restartIfSame) {
		if(animationName != null) {
			this.animationPlayer.startAnimation(animationName,restartIfSame);
		}
	}

	public Color getTint() {
		return tint;
	}

	public void setTint(Color tint) {
		this.tint = tint;
	}

}