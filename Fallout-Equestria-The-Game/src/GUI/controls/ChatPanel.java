package GUI.controls;

import utils.Rectangle;
import misc.Event;
import misc.EventArgs;
import misc.IEventListener;
import GUI.MouseEventArgs;
import GUI.TextEventArgs;
import graphics.Color;
import graphics.TextureFont;

public class ChatPanel extends Panel {
	private static final int textBoxSize = 25;
	private static final int buttonWidth = 100;
	private final TextArea area;
	private final Button sendButton;
	private final Textfield inputField;
	private final Event<TextEventArgs> sendTextEvent;

	public ChatPanel() {
		super();
		this.area = new TextArea();
		this.sendButton = new Button();
		this.sendButton.setText("Send");
		this.sendButton.setBgColor(new Color(1.0f,1.0f,1.0f,0.9f));
		this.inputField = new Textfield();
		this.area.setBgColor(Color.Black);
		this.area.setFgColor(Color.Cyan);
		
		
		this.sendTextEvent = new Event<>();
		this.setBgColor(new Color(255,255,255,0));
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
		this.inputField.setBounds(12,this.area.getBounds().Height,this.bounds.Width - buttonWidth - 12, textBoxSize);
		this.sendButton.setBounds(this.bounds.Width - buttonWidth,area.bounds.Height,buttonWidth,textBoxSize);
		
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
