package components;
import org.lwjgl.input.Keyboard;

import math.Vector2;

import entityFramework.IComponent;
import utils.Key;
import anotations.Editable;
import anotations.Editable;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 
 * @author Joakim Johansson
 *
 */
@XStreamAlias("Input")
@Editable
public class InputComp implements IComponent{
	private Vector2 mousePosition;
	//TODO implement this.
	private Vector2 mouseDisplacement;
	
	private boolean leftMouseButtonDown;
	private boolean rightMouseButtonDown;

	@Editable
	private Key backButton;
	@Editable	
	private Key	leftButton;
	@Editable	
	private Key	rightButton;
	@Editable
	private Key forwardButton;
	
	@Editable
	private Key	pipBuckButton;
	@Editable
	private Key	gallopButton; //Meaning sprint
	
	//TODO add ability buttons!. 

	//TODO chain constructors;
	public InputComp(){
		this(Keyboard.KEY_S, Keyboard.KEY_A, Keyboard.KEY_W, Keyboard.KEY_D, Keyboard.KEY_LSHIFT, Keyboard.KEY_TAB);
	}
	
	public InputComp(int backButtonID, int leftButtonID, int forwardButtonID, int rightButtonID, int gallopButtonID, int pipBuckButtonID){
		this(new Key(backButtonID), new Key(leftButtonID), new Key(forwardButtonID), new Key(rightButtonID), new Key(gallopButtonID), new Key(pipBuckButtonID));
	}
	
	public InputComp(Key backButton, Key leftButton, Key forwardButton, Key rightButton, Key gallopButton, Key pipBuckButton){
		this.backButton	=backButton;
		this.leftButton=	leftButton;
		this.rightButton=rightButton;
		this.forwardButton=forwardButton;
		this.gallopButton=gallopButton;
		this.pipBuckButton=pipBuckButton;
	}
	private InputComp(InputComp other){
		this(
				new Key(other.backButton.getKeyID()),
				new Key(other.leftButton.getKeyID()),
				new Key(other.forwardButton.getKeyID()),
				new Key(other.rightButton.getKeyID()),
				new Key(other.gallopButton.getKeyID()),
				new Key(other.pipBuckButton.getKeyID()));
		
		this.mousePosition = other.mousePosition;
		this.leftMouseButtonDown = other.leftMouseButtonDown;
		this.rightMouseButtonDown = other.leftMouseButtonDown;
	}
	
	/**
	 * @return the forwardButton
	 */
	public Object clone(){
		return new InputComp(this);
	}
	
	public void setAllToBeLike(InputComp other){
		this.mousePosition = other.mousePosition;
		this.leftMouseButtonDown = other.leftMouseButtonDown;
		this.rightMouseButtonDown = other.leftMouseButtonDown;
		
		this.backButton 	= new Key(other.backButton.getKeyID());
		this.leftButton 	= new Key(other.leftButton.getKeyID());
		this.rightButton	= new Key(other.rightButton.getKeyID());
		this.forwardButton  = new Key(other.forwardButton.getKeyID());
		this.pipBuckButton	= new Key(other.pipBuckButton.getKeyID());
		this.gallopButton	= new Key(other.gallopButton.getKeyID());
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
	
	public Vector2 getMouseDisplacement() {
		return mouseDisplacement;
	}

	public void setMouseDisplacement(Vector2 mouseDisplacement) {
		this.mouseDisplacement = mouseDisplacement;
	}

	public String toString() {
		return "Input Component: \n" +
			   "Mouse Pos: " + this.mousePosition.toString() + "\n" +
			   "Keys: \n" + "Left = " + this.leftButton.toString() + "\n" 
			   			  + "Right = " + this.rightButton.toString() + "\n"
			   			  + "Forward = " + this.forwardButton.toString() + "\n"
			   			  + "Back = " + this.backButton.toString() + "\n"
			   			  + "Fast = " + this.gallopButton.toString() + "\n"
			   			  + "PickBuck = " + this.pipBuckButton.toString();
	}
}
