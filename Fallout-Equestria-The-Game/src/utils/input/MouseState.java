package utils.input;

import math.Vector2;

public class MouseState {
	public final Vector2 ViewCoords;
	public final Vector2 ViewDeltaCoords;
	public final int DelthaScroll;
	
	public final ButtonState LeftButtonState;
	public final ButtonState RightButtonState;
	public final ButtonState MiddleButtonState;
	
	public MouseState(){ //Default constructor required for network
		this(new Vector2(), new Vector2(), 0, ButtonState.Depressed, ButtonState.Depressed, ButtonState.Depressed);
	}
	
	public MouseState(Vector2 viewCoords,
					  Vector2 viewDelta, 
					  int delthaScroll, 
					  ButtonState leftButtonState,
					  ButtonState rightButtonState, 
					  ButtonState middleButtonState) {
		super();
		
		ViewCoords = viewCoords;
		ViewDeltaCoords = viewDelta;
		DelthaScroll = delthaScroll;
		LeftButtonState = leftButtonState;
		RightButtonState = rightButtonState;
		MiddleButtonState = middleButtonState;
	}
	
	
}
