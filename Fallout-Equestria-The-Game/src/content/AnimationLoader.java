package content;

import graphics.Texture2D;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import math.Vector2;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import animation.Animation;
import animation.Bone;
import animation.Keyframe;

public class AnimationLoader implements IContentLoader<Animation>{

	@Override
	public Class<Animation> getClassAbleToLoad() {
		return Animation.class;
	}

	@Override
	public Animation loadContent(InputStream in) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document document = (Document)builder.build(in);
		
		Element rootNode = document.getRootElement();
		
		return extractAnimation(rootNode);
	}

	private Animation extractAnimation(Element rootNode) {
		int fameRate = extractInt(rootNode.getChild("FrameRate"));
		int loopFrame = extractInt(rootNode.getChild("LoopFrame"));
	    
		
		List<Texture2D> textures = extractTextures(rootNode);
		List<Keyframe> bones = exctractKeyframes(rootNode)
		
		throw new NullPointerException();
	}

	private List<Texture2D> extractTextures(Element rootNode) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Keyframe> exctractKeyframes(Element rootNode) {
		List<Keyframe> keyframes = new ArrayList<>();
		
		List<Element> keyframeElements = rootNode.getChildren("Keyframe");
		for (Element keyframeElement : keyframeElements) {
			Keyframe keyframe = extractKeyFrame(keyframeElement);
			keyframes.add(keyframe);
		}
		
		
		return keyframes;
	}

	private Keyframe extractKeyFrame(Element keyframeElement) {
		List<Bone> bones = extractBones(keyframeElement);
		int frameNumber = Integer.parseInt(keyframeElement.getAttribute("frame").getValue());
		String triggerString = keyframeElement.getAttributeValue("trigger");
		boolean mirror = Boolean.parseBoolean(keyframeElement.getAttributeValue("vflip"));
		Keyframe keyframe = new Keyframe();
		keyframe.setBones(bones);
		keyframe.setFrameNumber(frameNumber);
		keyframe.setTrigger(triggerString);
		keyframe.setFlipHorizontally(mirror);
		
		return keyframe;
	}

	@SuppressWarnings("unchecked")
	private List<Bone> extractBones(Element keyFrameElement) {
		List<Bone> bones = new ArrayList<>();
		
		List<Element> boneElements = keyFrameElement.getChildren("Bone");
		for (Element boneElement : boneElements) {
			Bone bone = extractBone(boneElement);
			bones.add(bone);
		}
		
		return bones;
	}
	
	private Bone extractBone(Element boneElement) {
		Bone bone = new Bone();
		String boneName = boneElement.getAttributeValue("name");
		boolean mirror = extractBool(boneElement.getChild("TextureFlipHorizontal"));
		int parentIndex = extractInt(boneElement.getChild("ParentIndex"));
		int textureIndex = extractInt(boneElement.getChild("TextureIndex"));
		Vector2 position = extractVector2(boneElement.getChild("Position"));
		float rotation = extractFloat(boneElement.getChild("Rotation"));
		Vector2 scale = extractVector2(boneElement.getChild("Scale"));
		
		bone.setName(boneName);
		bone.setTextureFlipHorizontal(mirror);
		bone.setParentIndex(parentIndex);
		bone.setTextureIndex(textureIndex);
		bone.setPosition(position);
		bone.setRotation(rotation);
		bone.setScale(scale);
		
		return bone; 
	}

	private Vector2 extractVector2(Element element) {
		float x,y;
		x = extractFloat(element.getChild("X"));
		y = extractFloat(element.getChild("Y"));
		
		return new Vector2(x,y);
	}

	private float extractFloat(Element element) {
		return Float.parseFloat(element.getValue());
	}
	
	private int extractInt(Element element) {
		return Integer.parseInt(element.getValue());
	}
	
	private boolean extractBool(Element element) {
		return Boolean.parseBoolean(element.getValue());
	}
	
	

	@Override
	public String getFoulder() {
		// TODO Auto-generated method stub
		return null;
	}

}
