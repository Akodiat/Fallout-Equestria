package utils;

import math.Vector2;

import static org.lwjgl.input.Mouse.*;

public class Mouse {

	private MouseState lastState;
	private MouseState currentState;

	public Mouse() {
		this.lastState = new MouseState(Vector2.Zero, Vector2.Zero, 0, 
										ButtonState.Depressed, ButtonState.Depressed, ButtonState.Depressed);
		this.currentState = new MouseState(Vector2.Zero, Vector2.Zero, 0, 
				ButtonState.Depressed, ButtonState.Depressed, ButtonState.Depressed);

	}
	
	
	public MouseState getMouseState() {
		return this.currentState;
	}
	
	public boolean wasButtonPressed(MouseButton button) {	
		switch (button) {
			case Left:
				return lastState.LeftButtonState == ButtonState.Depressed &&
				   currentState.LeftButtonState == ButtonState.Pressed;
			case Middle:
				return lastState.MiddleButtonState == ButtonState.Depressed &&
					currentState.MiddleButtonState == ButtonState.Pressed;
			case Right:
				return lastState.RightButtonState == ButtonState.Depressed &&
					currentState.RightButtonState == ButtonState.Pressed;
		default:
			throw new UnsupportedOperationException("button not supported " + button);
		}
	}
	
	public boolean wasButtonReleased(MouseButton button) {
		switch (button) {
			case Left:
				return lastState.LeftButtonState == ButtonState.Pressed &&
				   currentState.LeftButtonState == ButtonState.Depressed;
			case Middle:
				return lastState.MiddleButtonState == ButtonState.Pressed &&
				   currentState.MiddleButtonState == ButtonState.Depressed;
			case Right:
				return lastState.RightButtonState == ButtonState.Pressed &&
				   currentState.RightButtonState == ButtonState.Depressed;
		default:
			throw new UnsupportedOperationException("button not supported " + button);
		}
	}
	
	public void poll(Rectangle viewport) {
		Vector2 viewPos = new Vector2(getX(), viewport.Height - getY());
		this.updateCurrentMouseState(viewPos);
	}
	
	private void updateCurrentMouseState(Vector2 viewPos) {
		this.lastState = this.currentState;
		Vector2 viewDelta = Vector2.subtract(viewPos, 
											 this.lastState.ViewCoords);
		int scrollDeltha = getDWheel();	
		ButtonState leftButton = isButtonDown(0) ?
								 ButtonState.Pressed : ButtonState.Depressed;
		ButtonState rightButton = isButtonDown(1) ?
								 ButtonState.Pressed : ButtonState.Depressed;
		ButtonState middleButton = isButtonDown(2) ? 
								 ButtonState.Pressed : ButtonState.Depressed;
		
		this.currentState = new MouseState(viewPos,
										   viewDelta,
										   scrollDeltha, 
										   leftButton,
										   rightButton, 
										   middleButton);
	
	}
	
}
