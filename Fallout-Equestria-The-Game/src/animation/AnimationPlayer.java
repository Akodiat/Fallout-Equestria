package animation;

import graphics.Color;
import graphics.SpriteBatch;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import math.Vector2;

import utils.GameTime;

import com.google.common.collect.HashBasedTable;

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

	private Dictionary<String, Animation> animations = new Hashtable<String, Animation>();
	
	private Set<KeyframeTriggerListener> listeners = new HashSet<KeyframeTriggerListener>();
	private KeyframeTriggerEventArgs eventArgs = new KeyframeTriggerEventArgs();
	
	public AnimationPlayer()
	{
		this.playSpeedMultiplier = 1.0f;
	}

	public void AddAnimation(String name, Animation animation)
	{
		animations.put(name, animation);

		if (boneTransformations == null || animation.getKeyframes().get(0).getBones().size() > boneTransformations.length)
		{
			boneTransformations = new BoneTransformation[animation.getKeyframes().get(0).getBones().size()];
			transitionStates = new BoneTransitionState[animation.getKeyframes().get(0).getBones().size()];
		}
	}
	
	public void AddListener(KeyframeTriggerListener listenerToAdd){
		listeners.add(listenerToAdd);
	}
	
	private void invokeTrigger(){
		for (KeyframeTriggerListener listener : listeners) {
			listener.OnTrigger(this, this.eventArgs);
		}
	}

	public void StartAnimation(String animation)
	{
		StartAnimation(animation, false);
	}

	public void StartAnimation(String animation, boolean allowRestart)
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
		}

		Update(0);
	}

	public void ForceAnimationSwitch(String animation)
	{
		currentAnimation = animation;
	}

	public void TransitionToAnimation(String animation, float time)
	{
		if (transitioning)
		{
			Animation.UpdateBoneTransitions(transitionStates, animations.get(currentAnimation), animations.get(transitionAnimation), transitionTime / transitionTotalTime);
		}

		transitioning = true;
		transitionTime = 0;
		transitionTotalTime = time;
		transitionAnimation = animation;
	}

	public int GetBoneTransformIndex(String boneName)
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

	public boolean Update(float deltaSeconds)
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
				if (currentAnimationTime > animation.getKeyframes().get(currentKeyframeIndex + 1).getFrameTime())
					currentKeyframeIndex++;
			}

			animation.GetBoneTransformations(boneTransformations, 
											 transitionStates, 
											 currentKeyframeIndex, 
											 currentAnimationTime - animation.getKeyframes().get(currentKeyframeIndex).getFrameTime());

			returnValue = reachedEnd;
		}

		if (currentKeyframeIndex != startKeyframeIndex && !listeners.isEmpty() &&
				!(animations.get(currentAnimation).getKeyframes().get(currentKeyframeIndex).getTrigger() == null ||
				  animations.get(currentAnimation).getKeyframes().get(currentKeyframeIndex).getTrigger() == ""))
		{
			eventArgs.triggerString = animations.get(currentAnimation).getKeyframes().get(currentKeyframeIndex).getTrigger();
			invokeTrigger();
		}

		return returnValue;
	}

	public boolean Update(GameTime gameTime)
	{
		return Update((float)gameTime.DeltaTime);
	}

//	public void Draw(SpriteBatch spriteBatch, Vector2 position)
//	{
//		Draw(spriteBatch, position, false, false, 0, Color.White, new Vector2(1, 1), Matrix.Identity);
//	}

	/// <summary>
	/// Draw the given animation using the passed in spritebatch. Use this option if you have an existing spritebatch you would like to pass in.
	/// Make sure that you have backface culling disabled if you want to use bone texture flipping.
	/// </summary>
	/// <param name="spriteBatch">Existing active spritebatch. Begin/end will not be called.</param>
	/// <param name="tintColor">Color to tint the animation.</param>
	/// <param name="position">Position of the animation to draw.</param>
	public void Draw( SpriteBatch spriteBatch, Color tintColor, Vector2 position)
	{
		Animation animation = animations.get(currentAnimation);

		for (int boneIndex = 0; boneIndex < animation.getKeyframes().get(0).getBones().size(); boneIndex++)
		{
			Bone bone = animation.getKeyframes().get(currentKeyframeIndex).getBones().get(boneIndex);
			if (bone.isHidden())
				continue;
	
			spriteBatch.draw(animation.getTextures().get(bone.getTextureIndex()).getTexture(), Vector2.add(position, boneTransformations[boneIndex].getPosition()), 
					tintColor, animation.getTextures().get(bone.getTextureIndex()).getTextureBounds().getLocation(), 
					animation.getTextures().get(bone.getTextureIndex()).getTextureBounds().getOrigin(), 
					boneTransformations[boneIndex].getScale(), boneTransformations[boneIndex].getRotation(), bone.isMirrored());		
		}
	}

//	public void Draw(SpriteBatch spriteBatch, Vector2 position, bool flipHorizontal, bool flipVertical, float rotation, Color tintColor, Vector2 scale, Matrix cameraTransform)
//	{
//		if (string.IsNullOrEmpty(currentAnimation))
//			return;
//
//		Animation animation = animations[currentAnimation];
//
//		flipHorizontal |= animation.Keyframes[currentKeyframeIndex].FlipHorizontally;
//		flipVertical |= animation.Keyframes[currentKeyframeIndex].FlipVertically;
//
//		spriteBatch.Begin(SpriteSortMode.Immediate, BlendState.AlphaBlend, SamplerState.LinearClamp, DepthStencilState.Default, RasterizerState.CullNone, null,
//				Matrix.CreateScale(scale.X * (flipHorizontal ? -1 : 1), scale.Y * (flipVertical ? -1 : 1), 1) *
//				Matrix.CreateRotationZ(rotation) *
//				Matrix.CreateTranslation(position.X, position.Y, 0) *
//				cameraTransform);
//
//		Draw(ref spriteBatch, tintColor, new Vector2(0,0));
//
//		spriteBatch.End();
//	}

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


}