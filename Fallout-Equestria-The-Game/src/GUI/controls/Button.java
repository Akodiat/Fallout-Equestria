package GUI.controls;

import GUI.GUIImageControl;
import GUI.graphics.ButtonRenderer;
import GUI.graphics.IGUIRenderer;
import utils.Mouse;
import utils.MouseButton;
import graphics.Color;
import graphics.TextureFont;

public class Button extends GUIImageControl {
	private static IGUIRenderer<Button> DEFAULT_RENDERER = new ButtonRenderer();

	private Color textColor;
	private boolean mouseHover;
	private boolean pressed;
	private String text;
	private TextureFont font;
	
	public Button() {
		this.mouseHover = false;
		this.pressed = false;
		this.text = "";
		this.font = null;
		this.textColor = Color.White;
		this.setRenderer(DEFAULT_RENDERER);
	}
	
	public boolean isMouseHover() {
		return mouseHover;
	}
	
	public boolean isPressed() {
		return pressed;
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

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public void setFont(TextureFont font) {
		this.font = font;
	}
	
	@Override
	protected void onMouseEnter(Mouse mouse) {
		super.onMouseEnter(mouse);
		this.mouseHover = true;
	}
	
	@Override
	protected void onMouseExit(Mouse mouse) {
		super.onMouseExit(mouse);
		this.mouseHover = false;
	}
	
	@Override
	protected void onMouseDown(Mouse mouse, MouseButton button) {
		super.onMouseDown(mouse, button);
		this.pressed = true;
	}
	

	@Override
	protected void onMouseUp(Mouse mouse, MouseButton button){
		super.onMouseUp(mouse, button);
		this.pressed = false;
	}
}
