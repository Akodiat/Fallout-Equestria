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
		System.out.println("Mouse entered button!");
	}
	
	@Override
	protected void onMouseExit(Mouse mouse) {
		super.onMouseExit(mouse);
		this.mouseHover = false;
		System.out.println("Mouse exited button!");
	}
	
	@Override
	protected void onMouseDown(Mouse mouse, MouseButton button) {
		super.onMouseDown(mouse, button);
		this.pressed = true;
		System.out.println("Mouse was pressed! " + button);
	}
	
	@Override
	protected void onMouseOver(Mouse mouse) {
		super.onMouseOver(mouse);
		System.out.println("Mouse is over!");
	}	

	@Override
	protected void onMouseUp(Mouse mouse, MouseButton button){
		super.onMouseUp(mouse, button);
		this.pressed = false;
		System.out.println("Mouse was released! " + button);
	}
	@Override
	protected void onMouseClick(Mouse mouse, MouseButton button){
		super.onMouseClick(mouse, button);
		System.out.println("Mouse was released! as button" + button);
		
	}	
	
	@Override
	protected void onMouseDrag(Mouse mouse){
		super.onMouseOver(mouse);
		System.out.println("Mouse is draged! crazy i know");
	}
}
