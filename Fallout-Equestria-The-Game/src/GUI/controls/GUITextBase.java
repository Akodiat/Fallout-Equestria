package GUI.controls;

import misc.Event;
import misc.EventArgs;
import misc.IEventListener;
import graphics.TextureFont;

public abstract class GUITextBase extends GUIControl {

	private StringBuilder text;
	private TextureFont font;
	private int maxLength;
	
	public Event<EventArgs> textChanged;
	
	public GUITextBase() {
		this.text = new StringBuilder();
		this.font = null;
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
	
	public void appendText(String text) {
		this.text.append(text);
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
