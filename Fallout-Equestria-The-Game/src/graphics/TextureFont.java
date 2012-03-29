package graphics;

import java.util.Map;

import math.Vector2;

import utils.Rectangle;

public class TextureFont {
	
	private final Map<Character, Rectangle> charSourceMap;
	private final Texture2D fontTexture;
	private final float lineSpacing;
	private final float characterSpacing;
	
	public TextureFont(Texture2D fontTexture, float lineSpacing, float characterSpacing, Map<Character, Rectangle> charSourceMap) {
		this.fontTexture = fontTexture;
		this.charSourceMap = charSourceMap;
		this.lineSpacing = lineSpacing;
		this.characterSpacing = characterSpacing;
	}
	

	public Vector2 meassureString(String string) {
		float width = 0, height = this.lineSpacing, currWidth = 0;
		for (int i = 0; i < string.length() - 1; i++) {
			char c = string.charAt(i);
			
			if(c == '\n') {
				height += lineSpacing;
				currWidth = 0.0f;
				continue;
			} else if(c == '\t') {
				currWidth += 4 * this.charSourceMap.get(' ').Width;
			} else {	
				Rectangle srcRect = this.charSourceMap.get(c);
				currWidth += srcRect.Width + characterSpacing;
			}
			if(currWidth >= width) {
				width = currWidth;
			}
		}

		Rectangle srcRect = this.charSourceMap.get(string.charAt(string.length() - 1));
		return new Vector2(width + srcRect.Width, height);
	}
	
	protected float getLineSpacing(){
		return this.lineSpacing;
	}
	
	protected float getCharacterSpacing() {
		return this.characterSpacing;
	}
	
	public Texture2D getTexture() {
		return this.fontTexture;
	}
	
	protected Rectangle getCharacterSourceRect(char c) {
		return charSourceMap.get(c);
	}

}
