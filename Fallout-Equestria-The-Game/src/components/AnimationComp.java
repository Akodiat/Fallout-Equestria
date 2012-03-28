package components;

import java.util.Map;
import entityFramework.IComponent;
import graphics.Animation;

public class AnimationComp implements IComponent {

	private Map<String, Animation> animations;
	private Animation activeAnimation;

	public AnimationComp(Map<String, Animation> animations, String activeAnimationName){
		this.animations = animations;
		this.activeAnimation = animations.get(activeAnimationName);
	}

	public AnimationComp(AnimationComp toBeCloned){
		this.animations = toBeCloned.animations;
		this.activeAnimation = toBeCloned.activeAnimation;
	}

	public AnimationComp clone(){
		return new AnimationComp(this);
	}	
	
	public Animation getActiveAnimation() {
		return activeAnimation;
	}

	public void setActiveAnimation(String newActiveAnimation) {
		Animation animation = this.animations.get(newActiveAnimation);
		if(animation != null){
			if (this.activeAnimation != animation){
				this.activeAnimation.Complete();
				this.activeAnimation = animation;
				this.activeAnimation.Start();
			}
		}
	}

	public Map<String, Animation> getAnimations() {
		return animations;
	}

	public void setAnimations(Map<String, Animation> animations) {
		this.animations = animations;
	}

	public String toString() {
		String s = "AnimationComp: \n";
		if(this.activeAnimation != null) {
			s += "CurrentAnimation:" + this.activeAnimation.toString() + "\n";
		}
		
		for (Animation animation : this.animations.values()) {
			s += animation.toString() + "\n";
		}
		
		return s;
	}

}
