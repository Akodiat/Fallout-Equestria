package GUI;

import utils.EventArgs;
import GUI.controls.GUIControl;

public class ControlEventArgs extends EventArgs{
	private final GUIControl control;

	public ControlEventArgs(GUIControl control) {
		this.control = control;
	}
	
	public GUIControl getControl() {
		return control;
	}
	
}
