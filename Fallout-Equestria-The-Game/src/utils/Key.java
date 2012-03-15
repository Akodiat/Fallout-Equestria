package utils;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class Key {
	private final int keyID;
	private boolean pressed;

	public Key(int keyID){
		this.keyID=keyID;
	}

	/**
	 * @return the keyID
	 */
	public int getKeyID() {
		return keyID;
	}

	/**
	 * @return the isPressed
	 */
	public boolean isPressed() {
		return pressed;
	}

	/**
	 * @param isPressed the isPressed to set
	 */
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	
}
