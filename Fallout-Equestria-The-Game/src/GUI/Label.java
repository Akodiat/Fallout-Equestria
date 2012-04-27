package GUI;

import graphics.TextureFont;

public class Label extends GUIControl {
	private String text;
	private TextureFont font;
	
	public Label() {
		super();
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public TextureFont getFont() {
		return font;
	}
	public void setFont(TextureFont font) {
		this.font = font;
	}
	
}
