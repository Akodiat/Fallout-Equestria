package GUI.controls;

import GUI.GUIImageControl;
import graphics.Color;
import graphics.TextureFont;

public class ButtonBase extends GUIImageControl {

	private Color textColor;
	private String text;
	private TextureFont font;
	
	private boolean mouseHover;
	private boolean pressed;
	
	public ButtonBase() {
		this.textColor = Color.White;
		this.text = "";
		this.font = null;
	}
	
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TextureFont getFont() {
		return font;
	}

	public boolean isMouseHover() {
		return mouseHover;
	}

	public void setMouseHover(boolean mouseHover) {
		this.mouseHover = mouseHover;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public void setFont(TextureFont font) {
		this.font = font;
	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
}
