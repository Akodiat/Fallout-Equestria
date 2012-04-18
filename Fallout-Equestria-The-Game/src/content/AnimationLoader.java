package content;

import graphics.Texture2D;
import static content.XMLAnimHelper.*;

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
import animation.TextureDictionary;
import animation.TextureEntry;

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
		int frameRate = extractInt(rootNode.getChild("FrameRate"));
		int loopFrame = extractInt(rootNode.getChild("LoopFrame"));
	    boolean loop = loopFrame != -1;
		

		List<Keyframe> keyframes = exctractKeyframes(rootNode);
		List<TextureEntry> textures = extractTextures(rootNode);
		
		Animation anim = new Animation();
		anim.setFrameRate(frameRate);
		anim.setLoopFrame(loopFrame);
		anim.setTextures(textures);
		anim.setKeyframes(keyframes);
		anim.setLoop(loop);
		
		return anim;
	}

	private List<TextureEntry> extractTextures(Element rootNode) {
		String dictPath = rootNode.getAttributeValue("dictionary");
		TextureDictionary dictionary = ContentManager.load(dictPath, TextureDictionary.class);
		
		List<TextureEntry> entries = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		List<Element> textureEntryElements = rootNode.getChildren("Texture");
		for (Element textureEntryElement : textureEntryElements) {
			TextureEntry entry = extractTextureEntry(textureEntryElement, dictionary);
			entries.add(entry);
		}	
		
		return entries;
	}

	private TextureEntry extractTextureEntry(Element textureEntryElement,
			TextureDictionary dictionary) {
		
		TextureEntry entry = new TextureEntry();
		entry.setTexture(dictionary.getTexture());
		
		String boundsID = textureEntryElement.getValue();
		entry.setTextureBounds(dictionary.getTextureBounds(boundsID));
		return entry;
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
<<<<<<< HEAD
		keyframe.setMirrored(mirror);
		
=======
		keyframe.setFlipHorizontally(mirror);
>>>>>>> Fixed and tested animations
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
		boolean hidden = extractBool(boneElement.getChild("Hidden"));
		boolean mirror = extractBool(boneElement.getChild("TextureFlipHorizontal"));
		int parentIndex = extractInt(boneElement.getChild("ParentIndex"));
		int textureIndex = extractInt(boneElement.getChild("TextureIndex"));
		Vector2 position = extractVector2(boneElement.getChild("Position"));
		float rotation = extractFloat(boneElement.getChild("Rotation"));
		Vector2 scale = extractVector2(boneElement.getChild("Scale"));
		
		bone.setName(boneName);
		bone.setHidden(hidden);
		bone.setMirrored(mirror);
		bone.setParentIndex(parentIndex);
		bone.setTextureIndex(textureIndex);
		bone.setPosition(position);
		bone.setRotation(rotation);
		bone.setScale(scale);
		
		return bone; 
	}

	

	@Override
	public String getFoulder() {
		// TODO Auto-generated method stub
		return null;
	}

}
