package content;

import java.io.InputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import animation.Animation;
import animation.AnimationPlayer;

public class AnimationSetLoader extends ContentLoader<AnimationPlayer> {

	private ContentManager contentManager;
	
	public AnimationSetLoader(ContentManager manager, String folderPath) {
		super(folderPath);
		this.contentManager = manager;
	}

	@Override
	public Class<AnimationPlayer> getClassAbleToLoad() {
		return AnimationPlayer.class;
	}

	@Override
	public AnimationPlayer loadContent(InputStream in) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document document = (Document)builder.build(in);
		
		Element rootNode = document.getRootElement();
		
		return extractAnimationSet(rootNode);
	}

	private AnimationPlayer extractAnimationSet(Element rootNode) {
		AnimationPlayer player = new AnimationPlayer();
		@SuppressWarnings("unchecked")
		List<Element> textureEntryElements = rootNode.getChildren("Animation");
		for (Element element : textureEntryElements) {
			String animationKey = element.getAttributeValue("key");
			String animationPath = element.getAttributeValue("value");
			Animation animation = this.contentManager.load(animationPath, Animation.class);
			player.addAnimation(animationKey, animation);
		}
		return player;
	}

}
