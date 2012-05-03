package GUI.controls;

import graphics.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import GUI.ControlEventArgs;
import GUI.Event;
import GUI.KeyboardEventArgs;
import GUI.MouseEventArgs;
import GUI.ResizedEventArgs;
import GUI.graphics.GUIRenderingContext;
import GUI.graphics.IGUIRenderer;


import math.Point2;
import misc.EventArgs;
import misc.IEventListener;

import utils.GameTime;
import utils.Mouse;
import utils.MouseButton;
import utils.MouseState;
import utils.Rectangle;

public abstract class GUIControl {
	private GUIControl parent;
	private List<GUIControl> children;
	private int renderOrder;
	private IGUIRenderer<?> renderer;
	
	protected Rectangle bounds;
	private Color bgColor;
	private Color fgColor;
	private String name;
	private boolean focused;
	public boolean visible;
	public boolean enabled;
	
	public GUIControl() {
		this.parent = null;
		this.children = new ArrayList<>();
		this.bounds = new Rectangle(0,0,0,0);
		this.setBgColor(Color.White);
		this.setFgColor(Color.White);
		this.name = "";
		this.focused = false;
		this.visible = true;
		this.enabled = true;
		this.setRenderOrder(0);
		
		this.mouseDownEvent 	 = new Event<>();
		this.mouseUpEvent 		 = new Event<>();
		this.mouseOverEvent		 = new Event<>();
		this.mouseEnterEvent     = new Event<>();
		this.mouseExitEvent 	 = new Event<>();
		this.mouseClickedEvent   = new Event<>();
		this.mouseDragEvent 	 = new Event<>();
		this.keyUpEvent 		 = new Event<>();
		this.keyDownEvent 		 = new Event<>();
		this.focusLostEvent 	 = new Event<>();
		this.focusGainedEvent 	 = new Event<>();
		this.controlAddedEvent 	 = new Event<>();
		this.controlRemovedEvent = new Event<>();
		this.resizedEvent 		 = new Event<>();
		this.clickedEvent 		 = new Event<>();
		
		this.mouseEventState = stateNone;
	}
	
	public GUIControl getParent() {
		return this.parent;
	}
	protected void setParent(GUIControl parent) {
		this.parent = parent;
	}
	public void addChild(GUIControl child) {
		this.children.add(child);
		if(child.getParent() != null) {
			child.getParent().removeChild(child);
		}
		child.setParent(this);
		this.onChildAdded(child);
	}
	public void removeChild(GUIControl child) {
		this.children.remove(child);
		child.setParent(null);
		this.onChildRemoved(child);
	}	
	public void setBounds(Rectangle rectangle) {
		this.bounds = rectangle;
		this.onResized(this.bounds);
	}
	public void setBounds(int x, int y, int width, int height) {
		this.setBounds(new Rectangle(x, y, width, height));
	}	
	public Rectangle getBounds() {
		return this.bounds;
	}
	public Rectangle getAbsolueBounds() {
		GUIControl control = this;
		int x = this.bounds.X, y = this.bounds.Y;
		while(control.getParent() != null) {
			control = control.getParent();
			x += control.bounds.X;
			y += control.bounds.Y;
		}
		
		return new Rectangle(x,y, this.bounds.Width, this.bounds.Height);
		
	}
	
	public Color getBgColor() {
		return bgColor;
	}
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}
	public Color getFgColor() {
		return fgColor;
	}
	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void render(GUIRenderingContext context, GameTime time) {
		if(this.visible) {
			context.render(this, time);
		}
	}
	public List<GUIControl> getChildren() {
		return this.children;
	}
	public boolean isFocused() {
		return focused;
	}
	public void setFocused(boolean focused) {
		this.focused = focused;
		if(focused) {
			this.onFocusGained();
		} else {
			this.onFocusLost();
		}
	}
	public Rectangle getDimention() {
		return new Rectangle(0,0,
							 this.bounds.Width, 
							 this.bounds.Height);
	}	
	public Point2 getPosition() {		
		return new Point2(this.bounds.X, this.bounds.Y);
	}
	public int getRenderOrder() {
		return renderOrder;
	}

	public void setRenderOrder(int renderOrder) {
		this.renderOrder = renderOrder;
	}

	public void setPosition(Point2 point) {
		this.bounds = new Rectangle(point.X, point.Y,
									bounds.Width, bounds.Height);
	}
	public void setPosition(int x, int y) {
		this.setPosition(new Point2(x, y));
	}
	
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public IGUIRenderer<?> getRenderer() {
		return renderer;
	}

	public void setRenderer(IGUIRenderer<? extends GUIControl> renderer) {
		Class<? extends GUIControl> guiClass = renderer.getRenderedType();
		if(!guiClass.isAssignableFrom(this.getClass())) {
			throw new IllegalArgumentException("The renderer cannot render this class");
		}
		
		this.renderer = renderer;
	}


	private Event<EventArgs> clickedEvent;
	private Event<MouseEventArgs> mouseDownEvent;
	private Event<MouseEventArgs> mouseUpEvent;
	private Event<MouseEventArgs> mouseOverEvent;
	private Event<MouseEventArgs> mouseEnterEvent;
	private Event<MouseEventArgs> mouseExitEvent;
	private Event<MouseEventArgs> mouseClickedEvent;
	private Event<MouseEventArgs> mouseDragEvent;
	private Event<KeyboardEventArgs> keyDownEvent;
	private Event<KeyboardEventArgs> keyUpEvent;
	private Event<ControlEventArgs> controlAddedEvent;
	private Event<ControlEventArgs> controlRemovedEvent;
	private Event<ResizedEventArgs> resizedEvent;	
	
	
	private Event<EventArgs> 	  focusGainedEvent;
	private Event<EventArgs>	  focusLostEvent;
	private MouseEventState		  mouseEventState;
	
	private final MouseEventState 	stateOver     = new MouseEventStateOver();
	private final MouseEventState 	stateNone	  = new MouseEventStateNone();
	private final MouseEventState 	stateDragOver = new MouseEventStateDragOver();
	private final MouseEventState 	stateDrag     = new MouseEventStateDrag();
	

	private Rectangle createBestFit(Rectangle rect, Point2 offset) {
		if(this.parent == null) {
			return rect;
		} else {
			Rectangle pB = this.parent.getDimention().offset(offset);
			int x = rect.X, y = rect.Y ,w = rect.Width, h = rect.Height;
			if(x < pB.getLeft()) {
				w = (x + w) - pB.getLeft();
				x = pB.getLeft();
			} 
			
			if(x + w > pB.getRight()) {
				w = pB.getRight() - x;
			}
			
			if(y < pB.getTop()) {
				h = (y + h) - pB.getTop();
				y = pB.getTop();
			} 
			
			if(y + h > pB.getBottom()) {
				h = pB.getBottom() - y;
			}
			
			if(w < 0 || h < 0) {
				
				return Rectangle.Empty;
			} else {
				return new Rectangle(x,y,w,h);
			}		
		}
	}

	private boolean checkMouseCollision(Point2 offset, MouseState state) {
		Rectangle rect = this.getBounds().offset(offset);
		rect = createBestFit(rect, offset);
		if(rect.intersects(state.ViewCoords)) {
			return true;
		}
		
		return false;
	}
	
	public void checkKeyboardInput() {
		while(Keyboard.next()) {
			
			char c = Keyboard.getEventCharacter();
			int key = Keyboard.getEventKey();
			boolean pressed = Keyboard.getEventKeyState();
			
			this.keyboardRecursive(c, key, pressed);
		}	
		
	}
	
	private void keyboardRecursive(char keyChar, int key, boolean pressed) {
		if(!this.visible || !this.enabled) {
			return;
		}
		
		if(this.focused) {
			if(pressed) {
				this.onKeyDown(keyChar, key);
			} else {
				this.onKeyUp(keyChar, key);
			}
		}
		for (GUIControl child : this.children) {
			child.keyboardRecursive(keyChar, key, pressed);
		}
	}
	
	public void checkMouseInput(Point2 parentOffset, Mouse mouse) {
		if(!this.visible || !this.enabled) {
			return;
		}
		
		MouseState state = mouse.getMouseState();
		boolean collision = this.checkMouseCollision(parentOffset, state);
		this.mouseEventState.update(collision, mouse);
		Point2 childOffset = new Point2(this.bounds.X + parentOffset.X,
										this.bounds.Y + parentOffset.Y);
		for (GUIControl child : this.children) {
			child.checkMouseInput(childOffset, mouse);
		}
		
	}
	private void changeMouseEventState(MouseEventState newState) {
		this.mouseEventState = newState;
	}
	
	protected void onKeyDown(char keyChar, int key) {
		if(key == Keyboard.KEY_RETURN) {
			onClicked();
		}
		
		KeyboardEventArgs args = new KeyboardEventArgs(keyChar, key);
		this.keyDownEvent.invoke(this, args);
	}
	
	protected void onClicked() {
		this.clickedEvent.invoke(this, EventArgs.Empty);	
	}

	protected void onKeyUp(char keyChar, int key) {
		KeyboardEventArgs args = new KeyboardEventArgs(keyChar, key);
		this.keyUpEvent.invoke(this, args);
	}
	
	protected void onMouseDown(Mouse mouse, MouseButton button) {
		MouseEventArgs args = new MouseEventArgs(mouse.getMouseState(), button);
		mouseDownEvent.invoke(this, args);
	}
	protected void onMouseOver(Mouse mouse) {
		MouseEventArgs args = new MouseEventArgs(mouse.getMouseState(), null);
		mouseOverEvent.invoke(this, args);
	}	
	protected void onMouseEnter(Mouse mouse){
		MouseEventArgs args = new MouseEventArgs(mouse.getMouseState(), null);
		mouseEnterEvent.invoke(this, args);
	}
	protected void onMouseExit(Mouse mouse){
		MouseEventArgs args = new MouseEventArgs(mouse.getMouseState(), null);
		mouseExitEvent.invoke(this, args);
	}	
	protected void onMouseUp(Mouse mouse, MouseButton button){
		MouseEventArgs args = new MouseEventArgs(mouse.getMouseState(), button);
		mouseUpEvent.invoke(this, args);
	}
	protected void onMouseClick(Mouse mouse, MouseButton button){
		this.onClicked();
		
		MouseEventArgs args = new MouseEventArgs(mouse.getMouseState(), button);
		mouseClickedEvent.invoke(this, args);
	}	
	protected void onMouseDrag(Mouse mouse){
		MouseEventArgs args = new MouseEventArgs(mouse.getMouseState(), null);
		mouseDragEvent.invoke(this, args);
	}	
	protected void onFocusLost() {
		this.focusLostEvent.invoke(this, EventArgs.Empty);
		this.focused = false;
	}
	protected void onFocusGained() {
		this.focusGainedEvent.invoke(this, EventArgs.Empty);
		this.focused = true;
	}
	protected void onChildAdded(GUIControl child) {
		ControlEventArgs args = new ControlEventArgs(child);
		this.controlAddedEvent.invoke(this, args);
	}
	protected void onChildRemoved(GUIControl child) {
		ControlEventArgs args = new ControlEventArgs(child);
		this.controlRemovedEvent.invoke(this, args);
	}
	protected void onResized(Rectangle bounds) {
		ResizedEventArgs args = new ResizedEventArgs(bounds);
		this.resizedEvent.invoke(this, args);
	}
	
	
	public void addClicked(IEventListener<EventArgs> listener) {
		this.clickedEvent.addListener(listener);
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
	public void addKeyDownListener(IEventListener<KeyboardEventArgs> listener) {
		this.keyDownEvent.addListener(listener);
	}
	public void addKeyUpListener(IEventListener<KeyboardEventArgs> listener) {
		this.keyUpEvent.addListener(listener);
	}	
	public void addControlAddedListener(IEventListener<ControlEventArgs> listener) {
		this.controlAddedEvent.addListener(listener);
	}
	public void addControlRemovedListener(IEventListener<ControlEventArgs> listener) {
		this.controlRemovedEvent.addListener(listener);
	}
	public void addResizedListener(IEventListener<ResizedEventArgs> listener) {
		this.resizedEvent.addListener(listener);
	}
	
	public void removeClicked(IEventListener<EventArgs> listener) {
		this.clickedEvent.addListener(listener);
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
	public void removeKeyDownListener(IEventListener<KeyboardEventArgs> listener) {
		this.keyDownEvent.removeListener(listener);
	}
	public void removeKeyUpListener(IEventListener<KeyboardEventArgs> listener) {
		this.keyUpEvent.removeListener(listener);
	}
	public void removeControlAddedListener(IEventListener<ControlEventArgs> listener) {
		this.controlAddedEvent.removeListener(listener);
	}
	public void removeControlRemovedListener(IEventListener<ControlEventArgs> listener) {
		this.controlRemovedEvent.removeListener(listener);
	}
	public void removeResizedListener(IEventListener<ResizedEventArgs> listener) {
		this.resizedEvent.removeListener(listener);
	}
	
	public void checkNonLeftButtonEvents(Mouse mouse) {
		if(mouse.wasButtonPressed(MouseButton.Right)) {
			onMouseDown(mouse, MouseButton.Right);
	
		} else if(mouse.wasButtonReleased(MouseButton.Right)) {
			onMouseUp(mouse, MouseButton.Right);
			onMouseClick(mouse, MouseButton.Right);
		}
		
		if(mouse.wasButtonPressed(MouseButton.Middle)) {
			onMouseDown(mouse, MouseButton.Middle);
		} else if(mouse.wasButtonReleased(MouseButton.Middle)) {
			
			onMouseUp(mouse, MouseButton.Middle);
			onMouseClick(mouse, MouseButton.Middle);
		}
	}
	
	private abstract class MouseEventState {
		public abstract void update(boolean collision, Mouse mouse);
	}
	private class MouseEventStateNone extends MouseEventState{
				
		@Override
		public void update(boolean collision, Mouse mouse) {
			if(collision) {
				onMouseEnter(mouse);
				changeMouseEventState(stateOver);
			}
		}
	}	
	private class MouseEventStateOver extends MouseEventState {
		@Override
		public void update(boolean collision, Mouse mouse) {
			if(!collision) {
				onMouseExit(mouse);
				changeMouseEventState(stateNone);
			} else {
				if(mouse.wasButtonPressed(MouseButton.Left)) {
					onMouseDown(mouse, MouseButton.Left);
					changeMouseEventState(stateDragOver);
				}
				
				checkNonLeftButtonEvents(mouse);
			}
		}
	}
	private class MouseEventStateDragOver extends MouseEventState {
		@Override
		public void update(boolean collision, Mouse mouse) {
			onMouseDrag(mouse);
			if(!collision) {
				onMouseExit(mouse);
				changeMouseEventState(stateDrag);
			} else {
				if(mouse.wasButtonReleased(MouseButton.Left)) {
					changeMouseEventState(stateOver);
					onMouseUp(mouse, MouseButton.Left);
					onMouseClick(mouse, MouseButton.Left);
				}
				
				checkNonLeftButtonEvents(mouse);
			}
		}
	}
	private class MouseEventStateDrag extends MouseEventState {
		@Override
		public void update(boolean collision, Mouse mouse) {
			onMouseDrag(mouse);
			if(collision) {
				onMouseEnter(mouse);
				changeMouseEventState(stateDragOver);
			} else {
				if(mouse.wasButtonReleased(MouseButton.Left)) {
					changeMouseEventState(stateNone);
					onMouseUp(mouse, MouseButton.Left);
				}
			
			}
		}	
	}	
}
