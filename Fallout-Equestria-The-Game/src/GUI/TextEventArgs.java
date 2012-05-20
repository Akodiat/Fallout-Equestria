package GUI;

import utils.EventArgs;

public class TextEventArgs extends EventArgs {
	private final String text;
	
	public TextEventArgs(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
