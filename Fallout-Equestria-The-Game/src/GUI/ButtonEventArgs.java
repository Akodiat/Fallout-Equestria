package GUI;

import utils.EventArgs;

public class ButtonEventArgs extends EventArgs{
	private final String text;
	
	public ButtonEventArgs(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
}
