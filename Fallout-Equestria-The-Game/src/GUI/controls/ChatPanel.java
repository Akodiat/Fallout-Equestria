package GUI.controls;

import utils.Rectangle;
import misc.EventArgs;
import misc.IEventListener;
import GUI.Event;
import GUI.MouseEventArgs;
import GUI.TextEventArgs;
import graphics.Color;
import graphics.TextureFont;

public class ChatPanel extends Panel {
	private static final int textBoxSize = 25;
	private static final int buttonWidth = 35;
	private static final int scrollBar   = 15;
	private final TextArea area;
	private final Button sendButton;
	private final Textfield inputField;
	private final Event<TextEventArgs> sendTextEvent;

	public ChatPanel() {
		super();
		this.area = new TextArea();
		this.sendButton = new Button();
		this.inputField = new Textfield();
		this.sendTextEvent = new Event<>();
		this.setBgColor(new Color(0,0,0,0));
		this.initializeComponents();
	}

	private void initializeComponents() {
		this.sendButton.addMouseClicked(new IEventListener<MouseEventArgs>() {
			@Override
			public void onEvent(Object sender, MouseEventArgs e) {
				sendPressed();
			}
		});
		
		this.inputField.addClicked(new IEventListener<EventArgs>() {
			@Override
			public void onEvent(Object sender, EventArgs e) {
				sendPressed();
			}
		});
		
		
		
		this.addChild(this.area);
		this.addChild(this.inputField);
		this.addChild(this.sendButton);
	}
	
	@Override
	public void onResized(Rectangle bounds) {
		super.onResized(bounds);
		
		this.area.setBounds(0, 0, bounds.Width, bounds.Height - textBoxSize);
		this.inputField.setBounds(0,this.area.getBounds().Height,this.bounds.Width - buttonWidth, textBoxSize);
		this.sendButton.setBounds(inputField.bounds.Width,area.bounds.Height,buttonWidth,textBoxSize);
		
	}

	public void setFont(TextureFont font) {
		this.area.setFont(font);
		this.inputField.setFont(font);
		this.sendButton.setFont(font);
	}
	
	public void addText(String text) {
		this.area.appendText("\n");
		this.area.appendText(text);
		this.area.setScrollOffset(this.area.getMaxScrollOffset());
	}

	protected void sendPressed() {
		String text = this.inputField.getText();
		if(text.length() > 0) {
			this.inputField.setText("");
			this.addText(text);
			onTextAdded(text);
		}
	}
	
	private void onTextAdded(String text) {
		this.sendTextEvent.invoke(this, new TextEventArgs(text));
	}
	
	public void addTextSendListener(IEventListener<TextEventArgs> iEventListener) {
		this.sendTextEvent.addListener(iEventListener);
	}
	
	public void removeTextSendListener(IEventListener<TextEventArgs> listener) {
		this.sendTextEvent.addListener(listener);
	}
	
}
