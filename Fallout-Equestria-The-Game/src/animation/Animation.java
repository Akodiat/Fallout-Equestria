package animation;

import java.util.ArrayList;
import java.util.List;

import math.MathHelper;
import math.Vector2;

public class Animation {
	private String version;

	private int frameRate ;

	private boolean loop;
	private int loopFrame;
	private float loopTime;

	private List<TextureEntry> textures;
	private List<Keyframe> keyframes;

	public Animation(){

	}

	private Animation(Animation other){
		this.version = other.version;
		this.frameRate = other.frameRate;
		this.loop = other.loop;
		this.loopFrame = other.loopFrame;
		this.loopTime = other.loopTime;

		this.textures = new ArrayList();
		this.keyframes = new ArrayList();

		for (int i = 0; i < other.textures.size(); i++) {
			this.textures.add(other.textures.get(i).clone());
		}
		for (int i = 0; i < other.keyframes.size(); i++) {
			this.keyframes.add(other.keyframes.get(i).clone());
		}
	}

	public Animation clone(){
		return new Animation(this);
	}

	public void GetBoneTransformations(BoneTransformation[] transforms, BoneTransitionState[] transitions, int keyframeIndex, float time)
	{
		Keyframe currentKeyframe = keyframes.get(keyframeIndex);
		Keyframe nextKeyframe;
		float t;


		if (keyframeIndex == keyframes.size() - 1)
		{
			nextKeyframe = keyframes.get(0);

			if (loop)
				t = time / (loopTime - currentKeyframe.getFrameTime());
			else
				t = 0;
		}
		else
		{
			nextKeyframe = keyframes.get(keyframeIndex + 1);
			t = time / (nextKeyframe.getFrameTime() - currentKeyframe.getFrameTime());
		}

		for (int boneIndex = 0; boneIndex < keyframes.get(0).getUpdateOrderBones().size(); boneIndex++)
		{
			Vector2 position = Vector2.lerp(currentKeyframe.getUpdateOrderBones().get(boneIndex).getPosition(),
					nextKeyframe.getUpdateOrderBones().get(boneIndex).getPosition(), t);
			Vector2 scale = Vector2.lerp(currentKeyframe.getUpdateOrderBones().get(boneIndex).getScale(),
					nextKeyframe.getUpdateOrderBones().get(boneIndex).getScale(), t);
			float rotation = MathHelper.lerp(currentKeyframe.getUpdateOrderBones().get(boneIndex).getRotation(), 
					nextKeyframe.getUpdateOrderBones().get(boneIndex).getRotation(), t);


			transitions[boneIndex].setPosition(position);
			transitions[boneIndex].setRotation(rotation);

			BoneTransformation parentTransform = currentKeyframe.getUpdateOrderBones().get(boneIndex).getParentIndex() == -1 ? 
					BoneTransformation.Identity :
						transforms[currentKeyframe.getUpdateOrderBones().get(boneIndex).getParentIndex()];

			BoneTransformation transformation = new BoneTransformation();

			float single = MathHelper.sin(parentTransform.getRotation()), cosgle = MathHelper.cos(parentTransform.getRotation());
			Vector2 s = parentTransform.getScale();
			Vector2 p = parentTransform.getPosition();

			float x = s.X*position.X*cosgle - s.Y*position.Y*single + p.X;
			float y = s.X*position.X*single + s.Y*position.Y*cosgle + p.Y;

			Vector2 realPosition = new Vector2(x,y);


			transformation.setPosition(realPosition);
			transformation.setRotation(rotation + parentTransform.getRotation());
			transformation.setScale(Vector2.mul(scale, parentTransform.getScale()));

			int drawIndex = currentKeyframe.getUpdateOrderBones().get(boneIndex).getSelfIndex();

			transforms[drawIndex] = transformation;
		}
	}

	public static void GetBoneTransformationsTransition(BoneTransformation[] transforms, BoneTransitionState[] transitionState, Animation currentAnimation, Animation stopAnimation, float transitionPosition)
	{

		for (int boneIndex = 0; boneIndex < currentAnimation.keyframes.get(0).getUpdateOrderBones().size(); boneIndex++)
		{
			Bone currentBone = currentAnimation.keyframes.get(0).getUpdateOrderBones().get(boneIndex);
			Bone transitionBone = null;

			for (Bone b : stopAnimation.keyframes.get(0).getUpdateOrderBones())
			{
				if (currentBone.getName() == b.getName())
				{
					transitionBone = b;
					break;
				}
			}

			if (transitionBone == null)
				continue;

			Vector2 position = Vector2.lerp(transitionState[boneIndex].getPosition(), 
					transitionBone.getPosition(), 
					transitionPosition);
			Vector2 scale = new Vector2(1, 1);
			float rotation = MathHelper.lerp(transitionState[boneIndex].getRotation(), 
					transitionBone.getRotation(), 
					transitionPosition);

			BoneTransformation parentTransform = currentBone.getParentIndex() == -1 ? 
					BoneTransformation.Identity : 
						transforms[currentBone.getParentIndex()];

			int drawIndex = currentBone.getSelfIndex();

			transforms[drawIndex].setPosition(Vector2.add(position, parentTransform.getPosition()));
			transforms[drawIndex].setRotation(rotation + parentTransform.getRotation());
			transforms[drawIndex].setScale(Vector2.mul(scale, parentTransform.getScale()));
		}
	}

	public static void UpdateBoneTransitions(BoneTransitionState[] transitionState, Animation currentAnimation, Animation stopAnimation, float transitionPosition)
	{
		for (int boneIndex = 0; boneIndex < currentAnimation.keyframes.get(0).getUpdateOrderBones().size(); boneIndex++)
		{
			Bone currentBone = currentAnimation.keyframes.get(0).getUpdateOrderBones().get(boneIndex);
			Bone transitionBone = null;

			for (Bone b : stopAnimation.keyframes.get(0).getUpdateOrderBones())
			{
				if (currentBone.getName() == b.getName())
				{
					transitionBone = b;
					break;
				}
			}

			if (transitionBone == null)
				continue;

			transitionState[boneIndex].setPosition(Vector2.lerp(transitionState[boneIndex].getPosition(), 
					transitionBone.getPosition(), 
					transitionPosition));
			transitionState[boneIndex].setRotation(MathHelper.lerp(transitionState[boneIndex].getRotation(), 
					transitionBone.getRotation(), 
					transitionPosition));
		}
	}


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getFrameRate() {
		return frameRate;
	}

	public void setFrameRate(int frameRate) {
		this.frameRate = frameRate;
	}

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public int getLoopFrame() {
		return loopFrame;
	}

	public void setLoopFrame(int loopFrame) {
		this.loopFrame = loopFrame;
	}

	public float getLoopTime() {
		return loopTime;
	}

	public void setLoopTime(float loopTime) {
		this.loopTime = loopTime;
	}

	public List<TextureEntry> getTextures() {
		return textures;
	}

	public void setTextures(List<TextureEntry> textures) {
		this.textures = textures;
	}

	public List<Keyframe> getKeyframes() {
		return keyframes;
	}

	public void setKeyframes(List<Keyframe> keyframes) {
		this.keyframes = keyframes;
	}
}
