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

	public Animation getActiveAnimation() {
		return activeAnimation;
	}

	public void setActiveAnimation(String newActiveAnimation) {
		if(animations.containsKey(newActiveAnimation)){
			if (this.activeAnimation != this.animations.get(newActiveAnimation)){
				this.activeAnimation.Complete();
				this.activeAnimation = this.animations.get(newActiveAnimation);
				this.activeAnimation.Start();
			}
			System.out.println("Tried to set animation which was already in place.");
			return;
		}
		System.out.println("Tried to set animation with invalid key.");
		//TODO: Remove this annoying debugmessage
	}

	public Map<String, Animation> getAnimations() {
		return animations;
	}

	public void setAnimations(Map<String, Animation> animations) {
		this.animations = animations;
	}

	public AnimationComp clone(){
		return new AnimationComp(this);
	}

}
