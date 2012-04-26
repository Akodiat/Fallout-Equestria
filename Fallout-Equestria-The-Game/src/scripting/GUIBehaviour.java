package scripting;

import java.awt.dnd.MouseDragGestureRecognizer;

import misc.EventArgs;
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
	private Event<EventArgs> 	  focusGainedEvent;
	private Event<EventArgs>	  focusLostEvent;
	
	private boolean isFocused;

	public GUIBehaviour() {
		this.mouseDownEvent = new Event<>();
		this.mouseUpEvent = new Event<>();
		this.mouseOverEvent = new Event<>();
		this.mouseEnterEvent = new Event<>();
		this.mouseExitEvent = new Event<>();
		this.mouseClickedEvent = new Event<>();
		this.mouseDragEvent = new Event<>();
		this.focusLostEvent = new Event<>();
		this.focusGainedEvent = new Event<>();
	}
	
	public boolean isFocused() {
		return isFocused;
	}

	public void setFocused(boolean isFocused) {
		this.isFocused = isFocused;
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
	
	public void onFocusLost() {
		this.focusLostEvent.invoke(this.entity, EventArgs.Empty);
	}
	
	public void onFocusGained() {
		this.focusGainedEvent.invoke(this.entity, EventArgs.Empty);
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
	public void addMouseClicked(IEventListener<MouseEventArgs> listener) {
		this.mouseClickedEvent.addListener(listener);
	}
	public void addFocusGainedEvent(IEventListener<EventArgs> listener) {
		this.focusGainedEvent.addListener(listener);
	}
	public void addFocusLostEvent(IEventListener<EventArgs> listener) {
		this.focusLostEvent.addListener(listener);
	}
	
	
	public void removeMouseDownListener(IEventListener<MouseEventArgs> listener) {
		this.mouseDownEvent.removeListener(listener);
	}
	public void removeMouseUpListener(IEventListener<MouseEventArgs> listener) {
		this.mouseUpEvent.removeListener(listener);
	}
	public void removeMouseOverListener(IEventListener<MouseEventArgs> listener) {
		this.mouseOverEvent.removeListener(listener);
	}
	public void removeMouseDragListener(IEventListener<MouseEventArgs> listener) {
		this.mouseDragEvent.removeListener(listener);
	}
	public void removeMouseEnterListener(IEventListener<MouseEventArgs> listener) {
		this.mouseEnterEvent.removeListener(listener);
	}
	public void removeMouseExitListener(IEventListener<MouseEventArgs> listener) {
		this.mouseExitEvent.removeListener(listener);
	}
	public void removeMouseClicked(IEventListener<MouseEventArgs> listener) {
		this.mouseClickedEvent.removeListener(listener);
	}
	public void removeFocusGainedEvent(IEventListener<EventArgs> listener) {
		this.focusGainedEvent.removeListener(listener);
	}
	public void removeFocusLostEvent(IEventListener<EventArgs> listener) {
		this.focusLostEvent.removeListener(listener);
	}
	
	
}
