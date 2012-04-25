package GUI;

import utils.MouseButton;
import utils.MouseState;
import misc.EventArgs;

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
