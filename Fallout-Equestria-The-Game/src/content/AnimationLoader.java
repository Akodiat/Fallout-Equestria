package content;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
		
		
		List<Keyframe> bones = exctractKeyframes(rootNode);
		
		return null;
	}

	private List<Keyframe> exctractKeyframes(Element rootNode) {
		List<Keyframe> keyframes = new ArrayList<>();
		
		List<Element> keyframeElements = rootNode.getChildren("Keyframe");
		for (Element keyframeElement : keyframeElements) {
			
		}
		
		
		return keyframes;
	}

	private List<Bone> exctractBones(Element rootNode) {
		List<Bone> bones = new ArrayList<>();
		
		List<Element> boneElements = rootNode.getChildren("Bone");
		for (Element boneElement : boneElements) {
			Bone bone = extractBone(boneElement);
			bones.add(bone);
		}
		
		return bones;
	}
	
	private Bone exctractBone(Element boneElement) {
		Bone bone = new Bone();
	}

	@Override
	public String getFoulder() {
		// TODO Auto-generated method stub
		return null;
	}

}
