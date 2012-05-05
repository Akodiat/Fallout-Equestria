package utils;
import static org.lwjgl.input.Keyboard.*;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {

	final List<KeyboardKey> pressedKeys = new ArrayList<>();
	final List<KeyboardKey> releasedKeys = new ArrayList<>();
	
	public void poll() {
		this.pressedKeys.clear();
		this.releasedKeys.clear();
		while(next()) {
			char c = getEventCharacter();
			int key = getEventKey();
			boolean pressed = getEventKeyState();
			if(pressed) {
				this.pressedKeys.add(new KeyboardKey(c, key));
			} else {
				this.releasedKeys.add(new KeyboardKey(c,key));
			}
		}	
	}

	
	public boolean isKeyDown(int lwjglKeyCode) {
		return isKeyDown(lwjglKeyCode);
	}
	public boolean isKeyUp(int lwjglKeyCode) {
		return !isKeyDown(lwjglKeyCode);
	}
	
	public List<KeyboardKey> getPressedKeys() {
		return this.pressedKeys;
	}
	
	public List<KeyboardKey> getReleasedKeys() {
		return this.releasedKeys;
	}

}
