package animation;

import java.util.ArrayList;
import java.util.List;


public class Keyframe {
	private int frameNumber;
	private List<Bone> bones;
	private String trigger;
	private boolean mirrored;

	//TODO JAVA HOW DO
	//[Microsoft.Xna.Framework.Content.ContentSerializerIgnore]
	private float frameTime;
	//[Microsoft.Xna.Framework.Content.ContentSerializerIgnore]
	private List<Bone> updateOrderBones;

	public Keyframe()
	{
		this.updateOrderBones = new ArrayList<Bone>();
	}
	
	private Keyframe(Keyframe other)
	{
		this.frameNumber = other.frameNumber;
		this.trigger = other.trigger;
		this.mirrored = other.mirrored;
		this.frameTime = other.frameTime;
		
		this.bones = new ArrayList<Bone>();
		this.updateOrderBones = new ArrayList<Bone>();
		for (Bone bone : other.bones) {
			bones.add(bone.clone());
		}
		this.sortBones();
	}
	
	public Keyframe clone(){
		return new Keyframe(this);
	}

	public void sortBones()
	{
		updateOrderBones.clear();
		for (Bone bone : bones)
		{
			boneSortAdd(bone);
		}
	}

	protected void boneSortAdd(Bone b)
	{
		if (updateOrderBones.contains(b))
			return;

		if (b.getParentIndex() != -1)
			boneSortAdd(bones.get(b.getParentIndex()));

		updateOrderBones.add(b);
		b.setUpdateIndex(updateOrderBones.size() - 1);
	}

	public int getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}

	public List<Bone> getBones() {
		return bones;
	}

	public void setBones(List<Bone> bones) {
		this.bones = bones;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public boolean isMirrored() {
		return mirrored;
	}

	public void setMirrored(boolean mirrored) {
		this.mirrored = mirrored;
	}

	public float getFrameTime() {
		return frameTime;
	}

	public void setFrameTime(float frameTime) {
		this.frameTime = frameTime;
	}

	public List<Bone> getUpdateOrderBones() {
		return updateOrderBones;
	}

	public Bone getRootBone() {
		for (Bone bone : this.bones) {
			if(bone.getParentIndex() == -1) {
				return bone;
			}
		}
		return null;
	}	
	
	public Bone getBone(String boneName){
		for (Bone bone : this.bones) {
			if (bone.getName().equals(boneName)){
				return bone;
			}
		}
		throw new RuntimeException("Didn't find bone named " + boneName + " in keyframe " + this.toString());
	}
	
	private void addBone(Bone bone){
		bone.setSelfIndex(this.bones.size());
		this.bones.add(bone);
		this.boneSortAdd(bone);
	}

	public void addKeyframeToAnimation(String boneName, Keyframe keyframe) {
		Bone attachPoint = this.getBone(boneName);
		int attachIndex = attachPoint.getSelfIndex();
		Bone root = keyframe.getRootBone().clone();
		root.setParentIndex(attachIndex);
		
		this.addBone(root);
		
		int bonesSize = this.bones.size();
		
		//OBS!!!! i = 1 to skip root bone.
		for (int i = 1; i < keyframe.getBones().size(); i++) {
			
			Bone bone = keyframe.getBones().get(i).clone();
			bone.setParentIndex(bonesSize + bone.getParentIndex());
			this.addBone(bone);
		}
	}
}
