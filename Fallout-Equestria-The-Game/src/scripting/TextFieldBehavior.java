package scripting;

import org.lwjgl.input.Keyboard;

import anotations.Editable;

import components.GUIComp;

import utils.GameTime;

@Editable
public class TextFieldBehavior extends GUIBehaviour {
	
	@Override
	public void start() {
	}

	@Override
	public Object clone() {
		return null;
	}
	
	@Override
	public void onFocusGained() {
		super.onFocusGained();
		this.setFocused(true);
	}
	
	@Override
	public void onFocusLost() {
		super.onFocusLost();
		this.setFocused(false);
	}
	
	@Override 
	public void update(GameTime time) {
		while(Keyboard.next()) {
			
			String text = entity.getComponent(GUIComp.class).getText();
			
			if(Keyboard.getEventKey() == Keyboard.KEY_BACKSLASH) {
				text.substring(0, text.length() - 2);
			}
			
			char c = Keyboard.getEventCharacter();
			text += c;
			
			entity.getComponent(GUIComp.class).setText(text);
		}
	}
}
