package components;

import entityFramework.IComponent;
import utils.input.Keyboard;
import utils.input.Keys;
import utils.input.Mouse;
import anotations.Editable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author Joakim Johansson
 *
 */
@XStreamAlias("Input") @Editable
public class InputComp implements IComponent{
	private Mouse mouse;
	private Keyboard keyboard;
	
	private @Editable Keys backButton;
	private @Editable Keys leftButton;
	private @Editable Keys rightButton;
	private @Editable Keys forwardButton;
	
	private @Editable Keys pipBuckButton;
	private @Editable Keys gallopButton; //Meaning sprint
	
	//TODO add ability buttons!. 

	//TODO chain constructors;
	public InputComp(){
		this(Keys.S ,Keys.A, Keys.W, Keys.D, Keys.LeftShift, Keys.Tab);
	}
	

	public InputComp(Keys backButton, Keys leftButton, Keys forwardButton, Keys rightButton, Keys gallopButton, Keys pipBuckButton) {
		this.backButton = backButton;
		this.leftButton = leftButton;
		this.forwardButton = forwardButton;
		this.rightButton = rightButton;
		this.gallopButton = gallopButton;
		this.pipBuckButton = pipBuckButton;
	}


	public InputComp(InputComp other) {
		this.backButton = other.backButton;
		this.leftButton = other.leftButton;
		this.forwardButton = other.forwardButton;
		this.rightButton = other.rightButton;
		this.gallopButton = other.gallopButton;
		this.pipBuckButton = other.pipBuckButton;
	}


	/**
	 * @return the forwardButton
	 */
	public Object clone(){
		return new InputComp(this);
	}
	
	public void setAllToBeLike(InputComp other){
		this.backButton 	= other.backButton;
		this.leftButton 	= other.leftButton;
		this.rightButton	= other.rightButton;
		this.forwardButton  = other.forwardButton;
		this.pipBuckButton	= other.pipBuckButton;
		this.gallopButton	= other.gallopButton;
	}
	
	
	public String toString() {
		return "Input Component: \n" +
			   "Keys: \n" + "Left = " + this.leftButton.toString() + "\n" 
			   			  + "Right = " + this.rightButton.toString() + "\n"
			   			  + "Forward = " + this.forwardButton.toString() + "\n"
			   			  + "Back = " + this.backButton.toString() + "\n"
			   			  + "Fast = " + this.gallopButton.toString() + "\n"
			   			  + "PickBuck = " + this.pipBuckButton.toString();
	}

	public Keys getBackButton() {
		return backButton;
	}


	public void setBackButton(Keys backButton) {
		this.backButton = backButton;
	}


	public Keys getLeftButton() {
		return leftButton;
	}


	public void setLeftButton(Keys leftButton) {
		this.leftButton = leftButton;
	}


	public Keys getRightButton() {
		return rightButton;
	}


	public void setRightButton(Keys rightButton) {
		this.rightButton = rightButton;
	}


	public Keys getForwardButton() {
		return forwardButton;
	}


	public void setForwardButton(Keys forwardButton) {
		this.forwardButton = forwardButton;
	}


	public Keys getPipBuckButton() {
		return pipBuckButton;
	}


	public void setPipBuckButton(Keys pipBuckButton) {
		this.pipBuckButton = pipBuckButton;
	}


	public Keys getGallopButton() {
		return gallopButton;
	}


	public void setGallopButton(Keys gallopButton) {
		this.gallopButton = gallopButton;
	}


	public Mouse getMouse() {
		return mouse;
	}

	public void setMouse(Mouse mouse) {
		this.mouse = mouse;
	}

	public Keyboard getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(Keyboard keyboard) {
		this.keyboard = keyboard;
	}
}
