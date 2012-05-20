package GUI;

import utils.EventArgs;
import utils.input.MouseButton;
import utils.input.MouseState;

public class MouseEventArgs extends EventArgs{
	private final MouseState ms;
	private final MouseButton trigger;
	
	public MouseEventArgs(MouseState ms, MouseButton trigger) {
		this.ms = ms;
		this.trigger = trigger;
	}
	
	public MouseState getMouseState() {
		return ms;
	}
	
	public MouseButton getTriggerButton() {
		return this.trigger;
	}
}
