package utils;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {

	private final List<Keys> upKeys = new ArrayList<>();
	private final List<Keys> downKeys = new ArrayList<>();
		
	private final List<KeyboardKey> pressedKeys = new ArrayList<>();
	private final List<KeyboardKey> releasedKeys = new ArrayList<>();
	
	public void poll() {
		this.pressedKeys.clear();
		this.releasedKeys.clear();
		while(org.lwjgl.input.Keyboard.next()) {
			char c = org.lwjgl.input.Keyboard.getEventCharacter();
			int key = org.lwjgl.input.Keyboard.getEventKey();
			boolean pressed = org.lwjgl.input.Keyboard.getEventKeyState();
			if(pressed) {
				this.pressedKeys.add(new KeyboardKey(c, key));
			} else {
				this.releasedKeys.add(new KeyboardKey(c,key));
			}
		}	
		
		for (Keys key : Keys.values()) {
			if(this.isKeyDown(key)) {
				this.downKeys.add(key);
			} else {
				this.upKeys.add(key);
			}
		}
	}

	
	public boolean isKeyDown(Keys key) {
		return org.lwjgl.input.Keyboard.isKeyDown(key.getLwjglKeyCode());
	}
	
	public boolean isKeyUp(Keys key) {
		return !isKeyDown(key);
	}
	
	public boolean wasKeyPressed(Keys key) {
		for (KeyboardKey keyboardKey : this.pressedKeys) {
			if(keyboardKey.getKey() == key.getLwjglKeyCode()) {
				return true;
			}
		}
		return false;
	}
	

	
	public boolean wasKeyReleased(Keys key) {
		for (KeyboardKey keyboardKey : this.releasedKeys) {
			if(keyboardKey.getKey() == key.getLwjglKeyCode()) {
				return true;
			}
		}
		return false;
	}
	
	public List<KeyboardKey> getPressedKeys() {
		return this.pressedKeys;
	}
	
	public List<KeyboardKey> getReleasedKeys() {
		return this.releasedKeys;
	}

}
