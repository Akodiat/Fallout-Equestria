package utils;

import math.Vector2;

public class MouseState {
	public final Vector2 WorldCoords;
	public final Vector2 ViewCoords;
	public final Vector2 DeltaCoords;
	public final int DelthaScroll;
	public final ButtonState LeftButtonState;
	public final ButtonState RightButtonState;
	public final ButtonState MiddleButtonState;
	
	public MouseState(Vector2 worldCoords, Vector2 viewCoords, Vector2 delta,
			int delthaScroll, ButtonState leftButtonState,
			ButtonState rightButtonState, ButtonState middleButtonState) {
		super();
		
		WorldCoords = worldCoords;
		ViewCoords = viewCoords;
		DeltaCoords = delta;
		DelthaScroll = delthaScroll;
		LeftButtonState = leftButtonState;
		RightButtonState = rightButtonState;
		MiddleButtonState = middleButtonState;
	}
	
	
}
