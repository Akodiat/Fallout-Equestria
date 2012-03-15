package components;
import math.Vector2;

import entityFramework.IComponent;
import utils.Key;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class InputComponent implements IComponent{
	private Vector2 mousePosition;
	private boolean leftMouseButtonDown;
	private boolean rightMouseButtonDown;

	private Key backButton;
	private Key	leftButton;
	private Key	rightButton;
	private Key	pipBuckButton;
	private Key	gallopButton; //Meaning sprint

	private Key forwardButton;
	/**
	 * @return the forwardButton
	 */
	public Object clone(){
		InputComponent component = new InputComponent();
		component.backButton 	= new Key(this.backButton.getKeyID());
		component.leftButton 	= new Key(this.leftButton.getKeyID());
		component.rightButton	= new Key(this.rightButton.getKeyID());
		component.pipBuckButton	= new Key(this.pipBuckButton.getKeyID());
		component.gallopButton	= new Key(this.gallopButton.getKeyID());
		
		return component;
	}

	public Key getForwardButton() {
		return forwardButton;
	}


	/**
	 * @param forwardButton the forwardButton to set
	 */
	public void setForwardButton(int forwardButtonID) {
		this.forwardButton = new Key(forwardButtonID);
	}


	/**
	 * @return the backButton
	 */
	public Key getBackButton() {
		return backButton;
	}


	/**
	 * @param backButton the backButton to set
	 */
	public void setBackButton(int backButtonID) {
		this.backButton = new Key(backButtonID);
	}


	/**
	 * @return the leftButton
	 */
	public Key getLeftButton() {
		return leftButton;
	}


	/**
	 * @param leftButton the leftButton to set
	 */
	public void setLeftButton(int leftButtonID) {
		this.leftButton = new Key(leftButtonID);
	}


	/**
	 * @return the rightButton
	 */
	public Key getRightButton() {
		return rightButton;
	}


	/**
	 * @param rightButton the rightButton to set
	 */
	public void setRightButton(int rightButtonID) {
		this.rightButton = new Key(rightButtonID);
	}


	/**
	 * @return the pipBuckButton
	 */
	public Key getPipBuckButton() {
		return pipBuckButton;
	}


	/**
	 * @param pipBuckButton the pipBuckButton to set
	 */
	public void setPipBuckButton(int pipBuckButtonID) {
		this.pipBuckButton = new Key (pipBuckButtonID);
	}


	/**
	 * @return the gallopButton
	 */
	public Key getGallopButton() {
		return gallopButton;
	}


	/**
	 * @param gallopButton the gallopButton to set
	 */
	public void setGallopButton(int gallopButtonID) {
		this.gallopButton = new Key(gallopButtonID);
	}


	public Vector2 getMousePosition() {
		return mousePosition;
	}


	public void setMousePosition(Vector2 mousePosition) {
		this.mousePosition = mousePosition;
	}

	public boolean isLeftMouseButtonDown() {
		return leftMouseButtonDown;
	}

	public void setLeftMouseButtonDown(boolean leftMouseButtonDown) {
		this.leftMouseButtonDown = leftMouseButtonDown;
	}

	/**
	 * @return the rightMouseButtonDown
	 */
	public boolean isRightMouseButtonDown() {
		return rightMouseButtonDown;
	}

	/**
	 * @param rightMouseButtonDown the rightMouseButtonDown to set
	 */
	public void setRightMouseButtonDown(boolean rightMouseButtonDown) {
		this.rightMouseButtonDown = rightMouseButtonDown;
	}

	/**
	 * @return the forwardButtonPressed
	 */
	public boolean isForwardButtonPressed() {
		return forwardButton.isPressed();
	}

	/**
	 * @param forwardButtonPressed the forwardButtonPressed to set
	 */
	public void setForwardButtonPressed(boolean forwardButtonPressed) {
		this.forwardButton.setPressed(forwardButtonPressed);
	}

	/**
	 * @return the backButtonPressed
	 */
	public boolean isBackButtonPressed() {
		return backButton.isPressed();
	}

	/**
	 * @param backButtonPressed the backButtonPressed to set
	 */
	public void setBackButtonPressed(boolean backButtonPressed) {
		this.backButton.setPressed(backButtonPressed);
	}

	/**
	 * @return the leftButtonPressed
	 */
	public boolean isLeftButtonPressed() {
		return leftButton.isPressed();
	}

	/**
	 * @param leftButtonPressed the leftButtonPressed to set
	 */
	public void setLeftButtonPressed(boolean leftButtonPressed) {
		this.leftButton.setPressed(leftButtonPressed);
	}

	/**
	 * @return the rightButtonPressed
	 */
	public boolean isRightButtonPressed() {
		return rightButton.isPressed();
	}

	/**
	 * @param rightButtonPressed the rightButtonPressed to set
	 */
	public void setRightButtonPressed(boolean rightButtonPressed) {
		this.rightButton.setPressed(rightButtonPressed);
	}

	/**
	 * @return the pipBuckButtonPressed
	 */
	public boolean isPipBuckButtonPressed() {
		return pipBuckButton.isPressed();
	}

	/**
	 * @param pipBuckButtonPressed the pipBuckButtonPressed to set
	 */
	public void setPipBuckButtonPressed(boolean pipBuckButtonPressed) {
		this.pipBuckButton.setPressed(pipBuckButtonPressed);
	}

	/**
	 * @return the gallopButtonPressed
	 */
	public boolean isGallopButtonPressed() {
		return gallopButton.isPressed();
	}

	/**
	 * @param gallopButtonPressed the gallopButtonPressed to set
	 */
	public void setGallopButtonPressed(boolean gallopButtonPressed) {
		this.gallopButton.setPressed(gallopButtonPressed);
	}
}