package utils;

public class KeyboardKey {
	private final char character;
	private final int keyCode;
	
	public KeyboardKey(char character, int keyCode) {
		this.character = character;
		this.keyCode = keyCode;
	}

	public int getKey() {
		return keyCode;
	}

	public char getCharacter() {
		return character;
	}
}
