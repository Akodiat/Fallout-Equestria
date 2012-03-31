package content;

import graphics.Texture2D;
import graphics.TextureFont;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import utils.Rectangle;

public class TextureFontLoader implements IContentLoader<TextureFont>{

	private Rectangle getCharacterBounds(Attribute boundsAttribute) {
		String boundsValue = boundsAttribute.getValue();			
		String[] boundElementsValue = boundsValue.split(" ");
		int x = Integer.parseInt(boundElementsValue[0].substring(2));
		int y = Integer.parseInt(boundElementsValue[1].substring(2));
		int width = Integer.parseInt(boundElementsValue[2].substring(2));
		int height = Integer.parseInt(boundElementsValue[3].substring(2));
		return new Rectangle(x,y,width,height);
	}

	@Override
	public TextureFont loadContent(InputStream in) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document document = (Document)builder.build(in);
		
		Element rootNode = document.getRootElement();
		Attribute assetAttribute = rootNode.getAttribute("Asset");
		Attribute lineSpacingAttribute = rootNode.getAttribute("LineSpacing");
		Attribute charSpacingAttribute = rootNode.getAttribute("CharacterSpacing");
		
		String assetName = "fonts/" + assetAttribute.getValue();
		float lineSpacing = lineSpacingAttribute.getFloatValue();
		float charSpacing = charSpacingAttribute.getFloatValue();
		
		Texture2D texture = ContentManager.load(assetName, Texture2D.class);
		
		@SuppressWarnings("unchecked")
		List<Element> glyphs = rootNode.getChildren("Glyph");
		
		Map<Character, Rectangle> sourceRectangleMap = new HashMap<>();
		for (Element glyph : glyphs) {
			Attribute charAttribute = glyph.getAttribute("Char");
			Attribute boundsAttribute = glyph.getAttribute("Bounds");
			
			char character = charAttribute.getValue().charAt(0);
			Rectangle sourceRect = getCharacterBounds(boundsAttribute);
			
			sourceRectangleMap.put(character, sourceRect);
		}
		
		return new TextureFont(texture, lineSpacing, charSpacing,sourceRectangleMap);
	}

	@Override
	public Class<TextureFont> getClassAbleToLoad() {
		return TextureFont.class;
	}

	@Override
	public String getFoulder() {
		return "fonts";
	}
}
