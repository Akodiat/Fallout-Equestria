package GUI;

import misc.EventArgs;
import utils.Mouse;
import utils.MouseButton;
import graphics.Texture2D;
import graphics.TextureFont;

public class Button extends GUIImageControl {
	private Texture2D overTexture;
	private Texture2D downTexture;
	private boolean mouseHover;
	private boolean pressed;
	private String text;
	private TextureFont font;
	
	public Button() {
		this.overTexture = null;
		this.downTexture = null;
		this.mouseHover = false;
		this.pressed = false;
		this.text = "";
		this.font = null;
	}
	
	public Texture2D getOverTexture() {
		return overTexture;
	}
	public void setOverTexture(Texture2D overTexture) {
		this.overTexture = overTexture;
	}
	
	public Texture2D getDownTexture() {
		return downTexture;
	}
	public void setDownTexture(Texture2D downTexture) {
		this.downTexture = downTexture;
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
		System.out.println("Mouse was released! " + button);
	}
	@Override
	protected void onMouseUpAsButton(Mouse mouse, MouseButton button){
		super.onMouseUpAsButton(mouse, button);
		System.out.println("Mouse was released! as button" + button);
		
	}	
	
	@Override
	protected void onMouseDrag(Mouse mouse){
		super.onMouseOver(mouse);
		System.out.println("Mouse is draged! crazy i know");
	}
}
