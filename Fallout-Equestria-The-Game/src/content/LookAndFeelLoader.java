package content;

import graphics.Texture2D;
import java.io.InputStream;
import animation.TextureBounds;
import animation.TextureDictionary;
import GUI.graphics.LookAndFeel;
import GUI.graphics.VisibleElement;

public class LookAndFeelLoader implements IContentLoader<LookAndFeel>{

	private TextureDictionaryLoader dictionaryLoader = new TextureDictionaryLoader();
	
	@Override
	public Class<LookAndFeel> getClassAbleToLoad() {
		return LookAndFeel.class;
	}

	@Override
	public LookAndFeel loadContent(InputStream in) throws Exception {
		TextureDictionary dictionary = dictionaryLoader.loadContent(in);
		Texture2D texture = dictionary.getTexture();
		LookAndFeel feel = new LookAndFeel();
		for (String key : dictionary.getTextureCollection().keySet()) {
			TextureBounds srcRect = dictionary.getTextureCollection().get(key);
			VisibleElement element = new VisibleElement(texture,srcRect.getLocation());
			feel.setElement(key, element);
		}
		
		return feel;
	}

	@Override
	public String getFolder() {
		return "lookAndFeels";
	}

}
