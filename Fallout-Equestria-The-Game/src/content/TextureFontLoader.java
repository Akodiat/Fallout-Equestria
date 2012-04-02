package content;

import graphics.Texture2D;
import graphics.TextureFont;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import utils.Rectangle;

public class TextureFontLoader implements IContentLoader<TextureFont>{

	private static final String assetAttrib = "Asset";
	private static final String lineSpacingAttrib = "LineSpacing";
	private static final String characterSpacingAttrib = "CharacterSpacing";
	
	private static final String glyphAttrib = "Glyph";
	private static final String charAttrib = "Char";
	private static final String boundsAttrib = "Bounds";
	
	@Override
	public TextureFont loadContent(InputStream in) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document document = (Document)builder.build(in);
		
		Element rootNode = document.getRootElement();
		
		Texture2D texture = extractTexture(rootNode);	
		float lineSpacing = extractLineSpacing(rootNode);
		float charSpacing = extractCharacterSpacing(rootNode);
		
		Map<Character, Rectangle> sourceRectangleMap = extractCharacterMap(rootNode);
		
		return new TextureFont(texture, lineSpacing, charSpacing,sourceRectangleMap);
	}

	
	private float extractLineSpacing(Element rootNode) throws DataConversionException {
		Attribute lineSpacingAttribute = rootNode.getAttribute(lineSpacingAttrib);
		return lineSpacingAttribute.getFloatValue();
	}
	
	private float extractCharacterSpacing(Element rootNode) throws DataConversionException {
		Attribute charSpacingAttribute = rootNode.getAttribute(characterSpacingAttrib);
		return charSpacingAttribute.getFloatValue();
	}
	
	private Texture2D extractTexture(Element rootNode) {	
		Attribute assetAttribute = rootNode.getAttribute(assetAttrib);
		String assetName = "fonts/" + assetAttribute.getValue();
		
		Texture2D texture = ContentManager.load(assetName, Texture2D.class);
		return texture;
	}

	private Map<Character, Rectangle> extractCharacterMap(Element rootNode) {
		
		@SuppressWarnings("unchecked")
		List<Element> glyphs = rootNode.getChildren(glyphAttrib);
		
		Map<Character, Rectangle> sourceRectangleMap = new HashMap<>();
		for (Element glyph : glyphs) {
			Attribute charAttribute = glyph.getAttribute(charAttrib);
			Attribute boundsAttribute = glyph.getAttribute(boundsAttrib);
			
			char character = charAttribute.getValue().charAt(0);
			Rectangle sourceRect = parseRectangle(boundsAttribute);
			
			sourceRectangleMap.put(character, sourceRect);
		}
		return sourceRectangleMap;
	}
	
	private Rectangle parseRectangle(Attribute boundsAttribute) {
		String boundsValue = boundsAttribute.getValue();			
		String[] boundElementsValue = boundsValue.split(" ");
		int x = Integer.parseInt(boundElementsValue[0].substring(2));
		int y = Integer.parseInt(boundElementsValue[1].substring(2));
		int width = Integer.parseInt(boundElementsValue[2].substring(2));
		int height = Integer.parseInt(boundElementsValue[3].substring(2));
		return new Rectangle(x,y,width,height);
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
