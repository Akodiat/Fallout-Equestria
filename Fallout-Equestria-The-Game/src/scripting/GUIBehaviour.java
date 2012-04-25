package scripting;

import java.awt.dnd.MouseDragGestureRecognizer;

import misc.IEventListener;

import utils.MouseButton;
import utils.MouseState;
import GUI.Event;
import GUI.MouseEventArgs;

public abstract class GUIBehaviour extends Behavior {

	private Event<MouseEventArgs> mouseDownEvent;
	private Event<MouseEventArgs> mouseUpEvent;
	private Event<MouseEventArgs> mouseOverEvent;
	private Event<MouseEventArgs> mouseEnterEvent;
	private Event<MouseEventArgs> mouseExitEvent;
	private Event<MouseEventArgs> mouseClickedEvent;
	private Event<MouseEventArgs> mouseDragEvent;
	
	public GUIBehaviour() {
		this.mouseDownEvent = new Event<>();
		this.mouseUpEvent = new Event<>();
		this.mouseOverEvent = new Event<>();
		this.mouseEnterEvent = new Event<>();
		this.mouseExitEvent = new Event<>();
		this.mouseClickedEvent = new Event<>();
		this.mouseDragEvent = new Event<>();
	}

	@Override
	public void onMouseDown(MouseState state, MouseButton button) {
		MouseEventArgs args = new MouseEventArgs(state, button);
		mouseDownEvent.invoke(this.entity, args);
	}
	

	@Override
	public void onMouseOver(MouseState state) {
		MouseEventArgs args = new MouseEventArgs(state, null);
		mouseOverEvent.invoke(this.entity, args);
	}
	
	@Override
	public void onMouseEnter(MouseState state){
		MouseEventArgs args = new MouseEventArgs(state, null);
		mouseEnterEvent.invoke(this.entity, args);
	}
	
	@Override
	public void onMouseExit(MouseState state){
		MouseEventArgs args = new MouseEventArgs(state, null);
		mouseExitEvent.invoke(this.entity, args);
	}
	
	@Override
	public void onMouseUp(MouseState state, MouseButton button){
		MouseEventArgs args = new MouseEventArgs(state, button);
		mouseUpEvent.invoke(this.entity, args);
	}
	
	@Override
	public void onMouseUpAsButton(MouseState state, MouseButton button){
		MouseEventArgs args = new MouseEventArgs(state, button);
		mouseClickedEvent.invoke(this.entity, args);
	}
	
	@Override
	public void onMouseDrag(MouseState state){
		MouseEventArgs args = new MouseEventArgs(state, null);
		mouseDragEvent.invoke(this.entity, args);
	}
	
	public void addMouseDownListener(IEventListener<MouseEventArgs> listener) {
		this.mouseDownEvent.addListener(listener);
	}
	public void addMouseUpListener(IEventListener<MouseEventArgs> listener) {
		this.mouseUpEvent.addListener(listener);
	}
	public void addMouseOverListener(IEventListener<MouseEventArgs> listener) {
		this.mouseOverEvent.addListener(listener);
	}
	public void addMouseDragListener(IEventListener<MouseEventArgs> listener) {
		this.mouseDragEvent.addListener(listener);
	}
	public void addMouseEnterListener(IEventListener<MouseEventArgs> listener) {
		this.mouseEnterEvent.addListener(listener);
	}
	public void addMouseExitListener(IEventListener<MouseEventArgs> listener) {
		this.mouseExitEvent.addListener(listener);
	}
}
