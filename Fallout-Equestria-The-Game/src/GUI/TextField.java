package GUI;

import misc.EventArgs;

import org.lwjgl.input.Keyboard;

import utils.Mouse;
import utils.MouseButton;
import utils.StringHelper;
import graphics.Texture2D;
import graphics.TextureFont;

public class TextField extends GUITextBase {	
	private int markerPosition;
	
	public TextField() {
		this.markerPosition = 0;
	}	

	public int getMarkerPosition() {
		return markerPosition;
	}
		
	@Override
	protected void onFocusGained() {
		super.onFocusGained();
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
