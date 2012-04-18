package components;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import animation.AnimationPlayer;
import anotations.Editable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;
import graphics.Animation;

@XStreamAlias("Animation")
@Editable
public class AnimationComp implements IComponent {

	private AnimationPlayer animationPlayer;
	
	public AnimationComp() {
		this.animationPlayer = new AnimationPlayer();
	}
	
	public AnimationComp(AnimationPlayer animationPlayer) {
		this.animationPlayer = animationPlayer;
	}
	
	//TODO Proper cloning
	/**
	 * WARNING: SHALLOW CLONING
	 * @param other
	 */
	public AnimationComp(AnimationComp other) {
		this.animationPlayer = other.animationPlayer;
	}
	
	//TODO Proper cloning
	/**
	 * WARNING: SHALLOW CLONING
	 * @param other
	 */
	public AnimationComp clone() {
		return new AnimationComp(this);
	}

	public AnimationPlayer getAnimationPlayer() {
		return animationPlayer;
	}

	public void setAnimationPlayer(AnimationPlayer animationPlayer) {
		this.animationPlayer = animationPlayer;
	}

}
