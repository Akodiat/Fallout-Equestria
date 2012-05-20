package GUI;

import graphics.TextureFont;

import java.util.Map;
import java.util.HashMap;


public class LookAndFeel {
	private Map<String, VisibleElement> guiElements;
	private TextureFont defaultFont;
	
	public LookAndFeel() {
		this.guiElements = new HashMap<>();
		this.setDefaultFont(null);
	}
	
	public LookAndFeel(HashMap<String,VisibleElement> elements, TextureFont defaultFont) {
		this.guiElements = elements;
		this.setDefaultFont(defaultFont);
	}
	
	public VisibleElement getElement(String key) {
		if(!this.guiElements.containsKey(key))
			throw new NullPointerException("The key " + key  + " does not exist");
		return this.guiElements.get(key);
	}
	
	public void setElement(String key, VisibleElement element) {
		this.guiElements.put(key, element);
	}

	public TextureFont getDefaultFont() {
		return defaultFont;
	}

	public void setDefaultFont(TextureFont defaultFont) {
		this.defaultFont = defaultFont;
	}
	
	 
}
