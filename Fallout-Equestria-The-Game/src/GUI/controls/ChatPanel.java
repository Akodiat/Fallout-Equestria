package GUI.controls;

import utils.Rectangle;
import misc.EventArgs;
import misc.IEventListener;
import GUI.Event;
import GUI.MouseEventArgs;
import graphics.TextureFont;

public class ChatPanel extends Panel {
	private final TextArea area;
	private final Button sendButton;
	private final Textfield inputField;
	private final Event<EventArgs> sendTextEvent;
		
	
	public ChatPanel() {
		super();
		this.area = new TextArea();
		this.sendButton = new Button();
		this.inputField = new Textfield();
		this.sendTextEvent = new Event<>();
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
		
		
		this.area.setBounds(0, 0, 250, 220);
		this.inputField.setBounds(0,220,200,30);
		this.sendButton.setBounds(200,220,50,30);
		
		this.addChild(this.area);
		this.addChild(this.inputField);
		this.addChild(this.sendButton);
	}
	
	@Override
	public void onResized(Rectangle bounds) {
		super.onResized(bounds);
		
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
			onTextAdded();
		}
	}
	
	private void onTextAdded() {
		this.sendTextEvent.invoke(this, EventArgs.Empty);
	}
	
	public void addTextSendListener(IEventListener<EventArgs> listener) {
		this.sendTextEvent.addListener(listener);
	}
	
	public void removeTextSendListener(IEventListener<EventArgs> listener) {
		this.sendTextEvent.addListener(listener);
	}
	
}
