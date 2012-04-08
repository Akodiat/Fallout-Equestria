package components;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import anotations.EditableComponent;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;
import graphics.Animation;

@XStreamAlias("Animation")
@EditableComponent
public class AnimationComp implements IComponent {

	private Map<String, Animation> animations;
	private Animation activeAnimation;
	
	public AnimationComp() {
		this.animations = new HashMap<>();
		this.activeAnimation = null;
	}
	
	public AnimationComp(Map<String, Animation> animations, String activeAnimationName){
		this.animations = animations;
		this.activeAnimation = animations.get(activeAnimationName);
	}

	public AnimationComp(AnimationComp toBeCloned){
		this.animations = new HashMap<String, Animation>();
		Set<String> keys = toBeCloned.animations.keySet();
		for (String key : keys) {
			this.animations.put(key, toBeCloned.animations.get(key).clone());
		}
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
				this.activeAnimation.reset();
				this.activeAnimation = animation;
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
