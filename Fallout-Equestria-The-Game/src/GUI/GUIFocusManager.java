package GUI;

import java.util.ArrayList;
import java.util.List;

import GUI.controls.GUIControl;

import misc.IEventListener;

public class GUIFocusManager{
	private GUIControl rootControl;
	private IEventListener<ControlEventArgs> controlAddedListener;
	private IEventListener<ControlEventArgs> controlRemovedListener;
	private IEventListener<MouseEventArgs> mouseClickListener;	
	
	public GUIFocusManager(GUIControl rootControl) {
		this.createListeners();
		this.setRootComponent(rootControl);
	}
	private void createListeners() {
		controlAddedListener = new IEventListener<ControlEventArgs>() {
			@Override
			public void onEvent(Object sender, ControlEventArgs e) {
				controlAdded(sender, e);
			}
		};
		
		controlRemovedListener = new IEventListener<ControlEventArgs>() {
			
			@Override
			public void onEvent(Object sender, ControlEventArgs e) {
				controlRemoved(sender, e);
			}
		};
		mouseClickListener = new IEventListener<MouseEventArgs>() {
			
			@Override
			public void onEvent(Object sender, MouseEventArgs e) {
				mouseClick(sender,e);
			}
		};
	}
	public GUIControl getRootComponent() {
		return rootControl;
	}
	public void setRootComponent(GUIControl control) {
		if(this.rootControl != null) {
			unhookListeners(this.rootControl);
		}
		this.rootControl = control;
		if(this.rootControl != null)
			this.hookListeners(this.rootControl);
		
	}
	private void hookListeners(GUIControl control) {
		control.addControlAddedListener(controlAddedListener);
		control.addControlRemovedListener(controlRemovedListener);
		control.addClicked(mouseClickListener);
		for (GUIControl child : control.getChildren()) {
			this.hookListeners(child);
		}
	}
	private void unhookListeners(GUIControl control) {
		control.removeControlAddedListener(controlAddedListener);
		control.removeControlRemovedListener(controlRemovedListener);
		control.removeMouseClicked(mouseClickListener);
		for (GUIControl child : control.getChildren()) {
			this.unhookListeners(child);
		}
	}
	public void controlRemoved(Object sender, ControlEventArgs e) {
		this.unhookListeners(e.getControl());
	}
	public void controlAdded(Object sender, ControlEventArgs e) {
		this.hookListeners(e.getControl());
	}
	public void mouseClick(Object sender, MouseEventArgs e) {
		List<GUIControl> focused = new ArrayList<>();
		
		GUIControl control = (GUIControl)sender;
		focused.add(control);
		control.setFocused(true);
		while(control.getParent() != null) {
			control = control.getParent();
			control.setFocused(true);
			focused.add(control);
		}
		this.unfocusAllNotInFocusList(focused, this.rootControl);
	}
	private void unfocusAllNotInFocusList(List<GUIControl> focused, GUIControl control) {
		if(control.isFocused()) {
			if(!focused.contains(control)) {
				control.setFocused(false);
			}
		}
		for (GUIControl child : control.getChildren()) {
			this.unfocusAllNotInFocusList(focused, child);
		}
		
		
	}
	
}
