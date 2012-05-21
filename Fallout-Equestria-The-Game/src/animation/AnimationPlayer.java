package animation;

import graphics.Color;
import graphics.SpriteBatch;

import java.util.Hashtable;
import java.util.Map;

import math.Vector2;

import utils.Event;
import utils.IEventListener;
import utils.time.GameTime;


public class AnimationPlayer
{

	private String currentAnimation;
	private float currentAnimationTime;
	private int currentKeyframeIndex;
	private float playSpeedMultiplier;

	private boolean transitioning = false;
	private String transitionAnimation;
	private float transitionTime;
	private float transitionTotalTime;

	private BoneTransitionState[] transitionStates;
	private BoneTransformation[] boneTransformations;

	private Map<String, Animation> animations = new Hashtable<String, Animation>();

	private KeyframeTriggerEventArgs keyframeTriggerEventArgs = new KeyframeTriggerEventArgs();
	private Event<KeyframeTriggerEventArgs> keyframeTriggerEvent = new Event<KeyframeTriggerEventArgs>();
	
	private AnimationChangeEventArgs animationChangeEventArgs = new AnimationChangeEventArgs();
	private Event<AnimationChangeEventArgs> animationChangeEvent = new Event<AnimationChangeEventArgs>();

	public Animation getCurrentAnimationAnimation() {
		return this.animations.get(currentAnimation);
	}

	public AnimationPlayer()
	{
		this.playSpeedMultiplier = 1f;
	}

	private AnimationPlayer(AnimationPlayer other)
	{
		this.currentAnimation = other.currentAnimation;
		this.currentAnimationTime = other.currentAnimationTime;
		this.currentKeyframeIndex = other.currentKeyframeIndex;
		this.playSpeedMultiplier = other.playSpeedMultiplier;
		this.transitioning = other.transitioning;
		this.transitionAnimation = other.transitionAnimation;
		this.transitionTime = other.transitionTime;
		this.transitionTotalTime = other.transitionTotalTime;

		for (String key : other.animations.keySet()) {
			this.addAnimation(key, other.animations.get(key).clone());
		}
	}
	/**
	 * Does not add ANY listeners!
	 */
	public AnimationPlayer clone(){
		return new AnimationPlayer(this);
	}

	public void addAnimation(String name, Animation animation)
	{
		animations.put(name, animation);

		if (boneTransformations == null || animation.getKeyframes().get(0).getBones().size() > boneTransformations.length)
		{
			int length = animation.getKeyframes().get(0).getBones().size();
			boneTransformations = new BoneTransformation[length];
			transitionStates = new BoneTransitionState[length];

			for (int i=0; i< length; i++){
				boneTransformations[i] = BoneTransformation.Identity;
				transitionStates[i] = BoneTransitionState.Identity;
			}
		}
	}

	public void addKeyframeTriggerListener(IEventListener<KeyframeTriggerEventArgs> listenerToAdd){
		keyframeTriggerEvent.addListener(listenerToAdd);
	}
	public void removeKeyframeTriggerListener(IEventListener<KeyframeTriggerEventArgs> listenerToRemove){
		keyframeTriggerEvent.removeListener(listenerToRemove);
	}
	
	public void addAnimationChangeListener(IEventListener<AnimationChangeEventArgs> listenerToAdd){
		animationChangeEvent.addListener(listenerToAdd);
	}
	public void removeAnimationChangeListener(IEventListener<AnimationChangeEventArgs> listenerToRemove){
		animationChangeEvent.removeListener(listenerToRemove);
	}

	public void startAnimation(String animation)
	{
		startAnimation(animation, false);
	}

	public void startAnimation(String animation, boolean allowRestart)
	{
		transitioning = false;

		if (currentAnimation != animation || allowRestart)
		{
			currentAnimation = animation;
			currentAnimationTime = 0;
			currentKeyframeIndex = 0;

			for (Bone b : animations.get(currentAnimation).getKeyframes().get(0).getBones())
			{
				transitionStates[b.getUpdateIndex()].setPosition(b.getPosition());
				transitionStates[b.getUpdateIndex()].setRotation(b.getRotation());
			}
			
			invokeAnimationChangeEvent();
		}

		update(0);
	}

	private void invokeAnimationChangeEvent() {
		this.animationChangeEventArgs.newAnimation = this.currentAnimation;
		this.animationChangeEvent.invoke(this, this.animationChangeEventArgs);
	}

	public void forceAnimationSwitch(String animation)
	{
		currentAnimation = animation;
		invokeAnimationChangeEvent();
	}

	public void transitionToAnimation(String animation, float time)
	{
		if (transitioning)
		{
			Animation.UpdateBoneTransitions(transitionStates, animations.get(currentAnimation), animations.get(transitionAnimation), transitionTime / transitionTotalTime);
		}

		transitioning = true;
		transitionTime = 0;
		transitionTotalTime = time;
		transitionAnimation = animation;
		invokeAnimationChangeEvent();
	}

	public int getBoneTransformIndex(String boneName)
	{
		Animation animation = animations.get(currentAnimation);

		for (int boneIndex = 0; boneIndex < animation.getKeyframes().get(0).getBones().size(); boneIndex++)
		{
			Bone bone = animation.getKeyframes().get(0).getBones().get(boneIndex);
			if (bone.getName() == boneName)
				return boneIndex;
		}

		return -1;
	}

	public boolean update(float deltaSeconds)
	{
		if (currentAnimation == null || currentAnimation == "")
			return false;

		boolean returnValue = false;
		int startKeyframeIndex = currentKeyframeIndex;

		deltaSeconds *= playSpeedMultiplier;

		if (transitioning)
		{
			transitionTime += deltaSeconds;

			if (transitionTime > transitionTotalTime)
			{
				transitioning = false;

				currentAnimation = transitionAnimation;
				currentAnimationTime = transitionTime - transitionTotalTime;
				currentKeyframeIndex = 0;

				Animation animation = animations.get(currentAnimation);
				animation.GetBoneTransformations(boneTransformations, 
						transitionStates, 
						currentKeyframeIndex, 
						currentAnimationTime - animation.getKeyframes().get(currentKeyframeIndex).getFrameTime());
			}
			else
			{
				Animation.GetBoneTransformationsTransition(boneTransformations, 
						transitionStates, 
						animations.get(currentAnimation), 
						animations.get(transitionAnimation), 
						transitionTime / transitionTotalTime);
			}
		}
		else
		{
			boolean reachedEnd = false;

			currentAnimationTime += deltaSeconds;

			Animation animation = animations.get(currentAnimation);
			if (currentKeyframeIndex == animation.getKeyframes().size() - 1)
			{
				if (animation.isLoop())
				{
					if (currentAnimationTime > animation.getLoopTime())
					{
						currentAnimationTime -= animation.getLoopTime();
						currentKeyframeIndex = 0;
					}
				}
				else
				{
					currentAnimationTime = animation.getKeyframes().get(currentKeyframeIndex).getFrameTime();
					reachedEnd = true;
				}
			}
			else
			{
				if (currentAnimationTime > animation.getKeyframes().get(currentKeyframeIndex + 1).getFrameTime()) {
					currentKeyframeIndex++;
				}
			}

			animation.GetBoneTransformations(boneTransformations, 
					transitionStates, 
					currentKeyframeIndex, 
					currentAnimationTime - animation.getKeyframes().get(currentKeyframeIndex).getFrameTime());

			returnValue = reachedEnd;
		}

		if (currentKeyframeIndex != startKeyframeIndex &&
				!(animations.get(currentAnimation).getKeyframes().get(currentKeyframeIndex).getTrigger() == null ||
				animations.get(currentAnimation).getKeyframes().get(currentKeyframeIndex).getTrigger() == ""))
		{
			this.keyframeTriggerEventArgs.triggerString = animations.get(currentAnimation).getKeyframes().get(currentKeyframeIndex).getTrigger();
			this.keyframeTriggerEvent.invoke(this, this.keyframeTriggerEventArgs);
		}

		return returnValue;
	}

	public boolean update(GameTime gameTime)
	{
		return update((float)gameTime.getElapsedTime().getTotalSeconds());
	}

	//	public void Draw(SpriteBatch spriteBatch, Vector2 position)
	//	{
	//		Draw(spriteBatch, position, false, false, 0, Color.White, new Vector2(1, 1), Matrix.Identity);
	//	}

	/** Draw the given animation using the passed in spritebatch. Use this option if you have an existing spritebatch you would like to pass in.
	/* Make sure that you have backface culling disabled if you want to use bone texture flipping.
	/* @param spriteBatch Existing active spritebatch. Begin/end will not be called.
	/* @param tintColor Color to tint the animation.
	/* @param position Position of the animation to draw.
	 */
	public void draw(SpriteBatch spriteBatch, Color tintColor, Vector2 position)
	{
		Animation animation = animations.get(currentAnimation);

		for (int boneIndex = 0; boneIndex < animation.getKeyframes().get(0).getBones().size(); boneIndex++)
		{
			Bone bone = animation.getKeyframes().get(currentKeyframeIndex).getBones().get(boneIndex);
			if (bone.isHidden())
				continue;

			this.draw(spriteBatch, position, false, 0, tintColor, Vector2.One);
			//			spriteBatch.draw(animation.getTextures().get(bone.getTextureIndex()).getTexture(), Vector2.add(position, boneTransformations[boneIndex].getPosition()), 
			//					tintColor, animation.getTextures().get(bone.getTextureIndex()).getTextureBounds().getLocation(), 
			//					animation.getTextures().get(bone.getTextureIndex()).getTextureBounds().getOrigin(), 
			//					boneTransformations[boneIndex].getScale(), boneTransformations[boneIndex].getRotation(), bone.isMirrored());		
		}
	}

	/** Draw the given animation using the passed in spritebatch. Use this option if you have an existing spritebatch you would like to pass in.
	/* @param spriteBatch Existing active spritebatch. Begin/end will not be called.
	 */
	public void draw(SpriteBatch spriteBatch, Vector2 position, boolean mirrored, float rotation, Color tintColor, Vector2 scale)
	{
		Animation animation = animations.get(currentAnimation);

		for (int boneIndex = 0; boneIndex < animation.getKeyframes().get(0).getBones().size(); boneIndex++)
		{
			Bone bone = animation.getKeyframes().get(currentKeyframeIndex).getBones().get(boneIndex);
			if (bone.isHidden())
				continue;


			TextureBounds bounds = animation.getTextures().get(bone.getTextureIndex()).getTextureBounds();
			Vector2 origin = bounds.getOrigin();
			if(mirrored) {
				origin = new Vector2(bounds.getLocation().Width - bounds.getOrigin().X ,
						bounds.getOrigin().Y);
			}
			spriteBatch.draw(animation.getTextures().get(bone.getTextureIndex()).getTexture(), 
					getBonePosition(position, mirrored, scale, boneIndex), 
					Color.mul(tintColor, bone.getColor()), bounds.getLocation(), 
					origin, 
					Vector2.mul(scale, boneTransformations[boneIndex].getScale()), 
					getBoneRotation(mirrored, rotation, boneIndex), 
					bone.isMirrored() ^ mirrored);
		}
	}

	private float getBoneRotation(boolean mirrored, float rotation,
			int boneIndex) {
		return mirrored?-1*(boneTransformations[boneIndex].getRotation() + rotation):
			boneTransformations[boneIndex].getRotation() + rotation;
	}

	private Vector2 getBonePosition(Vector2 position, boolean mirrored,
			Vector2 scale, int boneIndex) {
		return mirrored?Vector2.add(position, Vector2.mul(scale, Vector2.mul(Vector2.MirrorYAxis, boneTransformations[boneIndex].getPosition()))):
			Vector2.add(position, Vector2.mul(scale, boneTransformations[boneIndex].getPosition()));
	}

	public String getCurrentAnimation() {
		return currentAnimation;
	}

	public float getCurrentAnimationTime() {
		return currentAnimationTime;
	}

	public void setCurrentAnimationTime(float currentAnimationTime) {
		this.currentAnimationTime = currentAnimationTime;
	}

	public int getCurrentKeyframe() {
		return animations.get(currentAnimation).getKeyframes().get(currentKeyframeIndex).getFrameNumber();
	}

	public boolean isTransitioning() {
		return transitioning;
	}

	public float getPlaySpeedMultiplier() {
		return playSpeedMultiplier;
	}

	public void setPlaySpeedMultiplier(float playSpeedMultiplier) {
		this.playSpeedMultiplier = playSpeedMultiplier;
	}

	public BoneTransformation[] getBoneTransformations() {
		return boneTransformations;
	}

	protected void setBoneTransformations(BoneTransformation[] boneTransformations) {
		this.boneTransformations = boneTransformations;
	}

	public void setBoneTexture(String boneName, TextureEntry entry){
		for (String key : this.animations.keySet()) {
			this.animations.get(key).getTextures().add(entry);
			int entryIndex = this.animations.get(key).getTextures().indexOf(entry);

			for (Keyframe frame : this.animations.get(key).getKeyframes()) {
				frame.getBone(boneName).setTextureIndex(entryIndex);
			}
		}
	}
	
	/**
	 * Gets bone color.
	 * Returns transparent if color isn't the same for all anims/keyframes.
	 * @param boneName
	 */
	public Color getBoneColor(String boneName){
		Color color = null;
		for (String key : this.animations.keySet()) {
			for (Keyframe frame : this.animations.get(key).getKeyframes()) {
				Color boneColor = frame.getBone(boneName).getColor();
				if (!boneColor.equals(color) && !(color == null))
					return Color.Transparent;
				color = boneColor;
			}
		}
		return color;
	}

	public void setBoneColor(String boneName, Color color){
		for (String key : this.animations.keySet()) {
			for (Keyframe frame : this.animations.get(key).getKeyframes()) {
				frame.getBone(boneName).setColor(color);
			}
		}
	}
	
	public void setBoneHidden(String boneName, boolean isHidden){
		for (String key : this.animations.keySet()) {
			for (Keyframe frame : this.animations.get(key).getKeyframes()) {
				frame.getBone(boneName).setHidden(isHidden);
			}
		}
	}
	
	public void setBoneMirrored(String boneName, boolean isMirrored){
		for (String key : this.animations.keySet()) {
			for (Keyframe frame : this.animations.get(key).getKeyframes()) {
				frame.getBone(boneName).setMirrored(isMirrored);
			}
		}
	}

	public void attachAnimationToBone(String boneName, Animation animation){
		for (String key : this.animations.keySet()) {
			Animation clonedTarget = this.animations.get(key).clone();
			Animation clonedAnim = animation.clone();

			clonedTarget.getTextures().addAll(clonedAnim.getTextures());

			for (Bone bone : clonedAnim.getKeyframes().get(0).getBones()) {
				bone.setTextureIndex(bone.getTextureIndex() + clonedTarget.getTextures().size() - 1);
			}
			for (Keyframe frame : clonedTarget.getKeyframes()) {
				frame.addKeyframeToAnimation(boneName, clonedAnim.getKeyframes().get(0));
			}
			this.addAnimation(key, clonedTarget);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}