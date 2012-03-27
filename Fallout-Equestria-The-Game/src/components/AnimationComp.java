package components;

import java.util.Map;

import content.ContentManager;

import entityFramework.IComponent;
import graphics.Animation;
import graphics.Texture2D;

public class AnimationComp implements IComponent {
	
	private Map<String, Animation> animations;
	
	private Animation activeAnimation;
	private Texture2D spriteSheet;
	
	public AnimationComp(Map<String, Animation> animations, String spriteSheetName){
		this.animations = animations;
		activeAnimation = null;//TODO: dunnolol
		this.spriteSheet = ContentManager.loadTexture(spriteSheetName);
	}
	
	public AnimationComp(AnimationComp toBeCloned){
		this.animations = toBeCloned.animations;
		this.activeAnimation = toBeCloned.activeAnimation;
		this.spriteSheet = toBeCloned.spriteSheet;
	}
	
	public Texture2D getSpriteSheet() {
		return spriteSheet;
	}

	public Animation getActiveAnimation() {
		return activeAnimation;
	}

	public void setActiveAnimation(String activeAnimation) {
		this.activeAnimation = animations.get(activeAnimation);
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
