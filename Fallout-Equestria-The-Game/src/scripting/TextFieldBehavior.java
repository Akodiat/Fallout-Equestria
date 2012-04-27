package scripting;

import org.lwjgl.input.Keyboard;

import anotations.Editable;

import components.GUIComp;

import utils.GameTime;
import utils.StringHelper;

@Editable
public class TextFieldBehavior extends GUIBehaviour {
	private int position;

	@Override
	public void start() {
		onFocusGained();
	}

	@Override
	public Object clone() {
		return new TextFieldBehavior();
	}

	@Override
	public void onFocusGained() {
		super.onFocusGained();
		this.setFocused(true);

		Keyboard.enableRepeatEvents(true);
		this.position = entity.getComponent(GUIComp.class).getText().length();
	}

	@Override
	public void onFocusLost() {
		super.onFocusLost();
		this.setFocused(false);
		Keyboard.enableRepeatEvents(false);
	}

	@Override 
	public void update(GameTime time) {
		if(this.isFocused()) {

			while(Keyboard.next()) {
				if(Keyboard.getEventKeyState()) {

					String text = entity.getComponent(GUIComp.class).getText();

					if(Keyboard.getEventKey() == Keyboard.KEY_BACK && position > 0) {
						text = StringHelper.backspace(text, position);

						position -= 1;
					}

					if(Keyboard.getEventKey() == Keyboard.KEY_LEFT && this.position != 0) {
						this.position -= 1;
					}

					if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT && this.position != text.length()) {
						this.position += 1;
					}

					char c = Keyboard.getEventCharacter();
					if(("!\"#§%@ &/()=?\\`^*'~®_.,:;-|>< {}£$А[]®^~").contains(c + "") ||  Character.isLetterOrDigit(c) && !("едц≈ƒ÷").contains(c + "")) {

						text = StringHelper.insert(text, position, c + "");
						position += 1;
					}

					entity.getComponent(GUIComp.class).setText(text);
				}
			}
		}
	}
}
