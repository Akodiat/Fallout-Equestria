package GUI;

import org.lwjgl.input.Keyboard;

import utils.StringHelper;

import math.Point2;
import misc.EventArgs;
import misc.IEventListener;
import graphics.Texture2D;
import graphics.TextureFont;

public abstract class GUITextBase extends GUIControl {

	private StringBuilder text;
	private TextureFont font;
	private int maxLength;
	
	private Texture2D background;
	private Texture2D foreground;
	
	public Event<EventArgs> textChanged;
	
	public GUITextBase() {
		this.text = new StringBuilder();
		this.font = null;
		this.background = null;
		this.foreground = null;
		this.maxLength = 1024;
		this.textChanged = new Event<EventArgs>();
	}	
	
	public String getText() {
		return text.toString();
	}
	public void setText(String text) {
		this.text = new StringBuilder(text);
		this.onTextChanged();
	}
	protected StringBuilder getInternalText() {
		return this.text;
	}
	
	public TextureFont getFont() {
		return font;
	}	
	public void setFont(TextureFont font) {
		this.font = font;
	}
	
	
	public Texture2D getBackground() {
		return background;
	}	
	public void setBackground(Texture2D background) {
		this.background = background;
	}
	public Texture2D getForeground() {
		return foreground;
	}
	public void setForeground(Texture2D foreground) {
		this.foreground = foreground;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	protected void onTextChanged() {
		textChanged.invoke(this, EventArgs.Empty);
	}	
	public void addTextChangedListener(IEventListener<EventArgs> listener) {
		this.textChanged.addListener(listener);
	}
	public void removeTextChangedListener(IEventListener<EventArgs> listener) {
		this.textChanged.removeListener(listener);
	}
	
}
