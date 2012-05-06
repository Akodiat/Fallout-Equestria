package content;

import static content.XMLAnimHelper.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import math.Vector2;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import animation.Animation;
import animation.AnimationPlayer;
import animation.Bone;
import animation.Keyframe;
import animation.TextureDictionary;
import animation.TextureEntry;

public class AnimationLoader extends ContentLoader<Animation>{

	
	private ContentManager ContentManager;
	
	public AnimationLoader(ContentManager manager, String folderPath) {
		super(folderPath);
		this.ContentManager = manager;
	}
	
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
		

		List<Keyframe> keyframes = exctractKeyframes(rootNode, frameRate);
		List<TextureEntry> textures = extractTextures(rootNode);
		
		
		Animation anim = new Animation();
		anim.setFrameRate(frameRate);
		anim.setLoopFrame(loopFrame);
		anim.setTextures(textures);
		anim.setKeyframes(keyframes);
		anim.setLoop(loop);
		anim.setLoopTime(loopFrame * (1.0f / frameRate));
		
		return anim;
	}

	private List<TextureEntry> extractTextures(Element rootNode) {
		String dictPath = rootNode.getAttributeValue("dictionary") + ".tdict";
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
<<<<<<< HEAD
		int i = boundsID.lastIndexOf('\\');
		if(i != -1) {
			boundsID = boundsID.substring(i + 1);
		}
		
		boundsID = boundsID.substring(0, boundsID.length() - 4);
				
		TextureBounds bounds = dictionary.getTextureBounds(boundsID);
		entry.setTextureBounds(bounds);
=======
		boundsID = boundsID.substring(0, boundsID.length() - 4);
				
				
		entry.setTextureBounds(dictionary.getTextureBounds(boundsID));
>>>>>>> Beginning to make new animationloadermethod.
		return entry;
	}

	private List<Keyframe> exctractKeyframes(Element rootNode, int frameTime) {
		List<Keyframe> keyframes = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		List<Element> keyframeElements = rootNode.getChildren("Keyframe");
		for (Element keyframeElement : keyframeElements) {
			Keyframe keyframe = extractKeyFrame(keyframeElement, frameTime);
			keyframes.add(keyframe);
		}
		
		
		return keyframes;
	}

	private Keyframe extractKeyFrame(Element keyframeElement, int frameRate) {
		List<Bone> bones = extractBones(keyframeElement);
		int frameNumber = Integer.parseInt(keyframeElement.getAttribute("frame").getValue());
		String triggerString = keyframeElement.getAttributeValue("trigger");
		boolean mirror = Boolean.parseBoolean(keyframeElement.getAttributeValue("vflip"));
		Keyframe keyframe = new Keyframe();
		keyframe.setBones(bones);
		keyframe.setFrameNumber(frameNumber);
		keyframe.setTrigger(triggerString);
		keyframe.setMirrored(mirror);
		keyframe.setFrameTime(frameNumber * ( 1.0f / frameRate));
		keyframe.sortBones();
		
		return keyframe;
	}

	@SuppressWarnings("unchecked")
	private List<Bone> extractBones(Element keyFrameElement) {
		List<Bone> bones = new ArrayList<>();
		
		List<Element> boneElements = keyFrameElement.getChildren("Bone");
		int i = 0;
		for (Element boneElement : boneElements) {
			Bone bone = extractBone(boneElement, i++);
			bones.add(bone);
		}
		
		return bones;
	}
	
	private Bone extractBone(Element boneElement,int drawIndex) {
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
		bone.setSelfIndex(drawIndex);
		
		return bone; 
	}
	
	public AnimationPlayer loadDefaultPlayerAnimations(){
		Animation idleAnimation;
		Animation walkAnimation;
		Animation jumpAnimation;
		Animation liftedAnimation;
		
		return new AnimationPlayer();
	}
}
