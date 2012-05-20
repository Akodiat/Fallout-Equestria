package GUI;

import utils.EventArgs;

public class KeyboardEventArgs extends EventArgs{
	private final char keyChar;
	private final int key;
	
	public KeyboardEventArgs(char keyChar, int key) {
		this.keyChar = keyChar;
		this.key = key;
	}

	public char getKeyChar() {
		return keyChar;
	}

	public int getKey() {
		return key;
	}
	
}
