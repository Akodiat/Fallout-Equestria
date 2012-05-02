package GUI.controls;


import org.lwjgl.input.Keyboard;

import GUI.graphics.TextboxRenderer;


public class Textfield extends GUITextBase {	
	private static final TextboxRenderer DEFAULT_RENDERER = new TextboxRenderer();
	
	private int markerPosition;
	
	public Textfield() {
		this.markerPosition = 0;
		this.setRenderer(DEFAULT_RENDERER);
	}	

	public int getMarkerPosition() {
		return markerPosition;
	}
		
	@Override
	protected void onFocusGained() {
		super.onFocusGained();
		this.markerPosition = this.getText().length();
		Keyboard.enableRepeatEvents(true);		
	}
	@Override 
	protected void onFocusLost() {
		super.onFocusLost();
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	protected void onKeyDown(char keyChar, int key) {	
		super.onKeyDown(keyChar, key);
		if(key == Keyboard.KEY_BACK) {
			if(deleteChar(this.markerPosition - 1)) {
				markerPosition -= 1;
			}
		} else if(key == Keyboard.KEY_DELETE) {
			deleteChar(this.markerPosition);
		} else if(key == Keyboard.KEY_LEFT && this.markerPosition != 0) {
			this.markerPosition -= 1;
		} else if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT && this.markerPosition < this.getText().length()) {
			this.markerPosition += 1;
		} else if(("!\"#¤%@ &/()=?\\`^*'~¨_.,:;-|><").contains(keyChar + "") 
				||  Character.isLetterOrDigit(keyChar) && !("åäöÅÄÖ").contains(keyChar + "")) {

			if(this.insertChar(keyChar)) {
				markerPosition += 1;
			}
		}
	}

	private boolean insertChar(char keyChar) {
		StringBuilder builder = this.getInternalText();
		if(builder.length() < this.getMaxLength()) {
			builder.insert(this.markerPosition, keyChar);
			return true;
		}
		return false;
	}

	private boolean deleteChar(int position) {
		StringBuilder builder = this.getInternalText();
		if(position >= 0 && position < builder.length()) {
			builder.deleteCharAt(position);
			return true;
		} else {
			return false;
		}
	}	
	
	
		
}
