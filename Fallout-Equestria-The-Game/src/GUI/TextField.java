package GUI;

import utils.Mouse;
import utils.MouseButton;
import graphics.TextureFont;

public class TextField extends GUIControl {
	private String text;
	private TextureFont font;
	private int markerPosition;
	
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
	public int getMarkerPosition() {
		return markerPosition;
	}
	
	@Override
	protected void onMouseDown(Mouse mouse, MouseButton button) {
		super.onMouseDown(mouse, button);
		onFocusGained();
	}	
	
}
