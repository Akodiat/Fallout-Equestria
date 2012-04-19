package content;

import graphics.Texture2D;
import static content.XMLAnimHelper.*;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import math.Vector2;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import animation.TextureBounds;
import animation.TextureDictionary;

public class TextureDictionaryLoader implements IContentLoader<TextureDictionary>{

	@Override
	public Class<TextureDictionary> getClassAbleToLoad() {
		return TextureDictionary.class;
	}

	@Override
	public TextureDictionary loadContent(InputStream in) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document document = (Document)builder.build(in);
		
		Element rootNode = document.getRootElement();
		
		Texture2D spriteSheet = extractSpriteSheet(rootNode);
		Map<String, TextureBounds> boundsMap = extractTextures(rootNode);
		TextureDictionary dictionary = new TextureDictionary();
		dictionary.setTexture(spriteSheet);
		dictionary.setTextureCollection(boundsMap);
		
		return dictionary;
	}

	private Texture2D extractSpriteSheet(Element rootNode) {
		String path = rootNode.getChildText("TexturePath");
		return ContentManager.loadTexture("animationsheets/" + path);
	}

	private Map<String, TextureBounds> extractTextures(Element rootNode) {
		List<Element> boundElements = rootNode.getChildren("Texture");
		Map<String, TextureBounds> images = new HashMap<>();
		for (Element element : boundElements) {
			TextureBounds bound = extractTextureBound(element);
			String name = extractTextureName(element);
			images.put(name, bound);
		}
		
		return images;
	}

	private String extractTextureName(Element element) {
		return element.getAttributeValue("name");
	}

	private TextureBounds extractTextureBound(Element element) {
		TextureBounds bounds = new TextureBounds();
		bounds.setLocation(extractRectangle(element));
		bounds.setOrigin(new Vector2(extractInt(element.getChild("OriginX")),
						 			 extractInt(element.getChild("OriginY"))));
		return bounds;
	}

	@Override
	public String getFolder() {
		return "animations" + File.separator + "dictionaries";
	}

}
