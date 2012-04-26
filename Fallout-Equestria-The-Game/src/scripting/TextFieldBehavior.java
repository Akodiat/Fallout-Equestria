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
		return new TextFieldBehavior();
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
			if(Keyboard.getEventKeyState()) {
				
				String text = entity.getComponent(GUIComp.class).getText();
				
				if(Keyboard.getEventKey() == Keyboard.KEY_BACK) {
					text = text.substring(0, text.length() - 1);
				}
				
				
				char c = Keyboard.getEventCharacter();
				if(("!\"#¤%@ &/()=?\\`^*'~¨_.,:;-|><").contains(c + "") ||  Character.isLetterOrDigit(c)) {
					text += c;
				}
				entity.getComponent(GUIComp.class).setText(text);
			}
		}
	}
}
