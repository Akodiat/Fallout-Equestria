package components;

import graphics.TextureFont;

import org.lwjgl.input.Keyboard;

import scripting.Behavior;
import utils.MouseButton;
import utils.MouseState;

public class TextFieldBehavior extends Behavior {
	private String text = "";
	private TextureFont textFont;
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void onMouseDown(MouseState state, MouseButton button) {
		this.setEnabled(true);
		
		while(Keyboard.next()) {
			
			if(Keyboard.getEventKey() == Keyboard.KEY_BACKSLASH) {
				this.text.substring(0, this.text.length() - 2);
			}
			
			char c = Keyboard.getEventCharacter();
			this.text += c;
		}
	}
}
