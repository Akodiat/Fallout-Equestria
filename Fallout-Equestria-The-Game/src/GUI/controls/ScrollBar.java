package GUI.controls;

import GUI.Event;
import GUI.ScrollEventArgs;
import GUI.graphics.ButtonRenderer;
import GUI.graphics.ScrollBarRenderer;
import math.MathHelper;
import misc.IEventListener;
import utils.Mouse;
import utils.MouseButton;
import utils.Rectangle;
import graphics.Texture2D;

public class ScrollBar extends GUIControl {
	private static final ScrollBarRenderer DEFAULT_RENDERER = new ScrollBarRenderer();
	private static final ButtonRenderer DEFAULT_SCROLLBUTTON_RENDERER = new ButtonRenderer("ScrollBar_Button_Background",
																						   "ScrollBar_Button_Over",
																						   "ScrollBar_Button_Down");	
	
	
	protected Button scrollButton;
	private boolean vertical;
	
	private int scrollMax;
	private int scrollValue;
	private int stepSize;
	
	private Texture2D background;
	
	private Event<ScrollEventArgs> scolledEvent;
	
	public ScrollBar() {
		this.scrollButton = new Button();
		this.addChild(scrollButton);
		this.vertical = true;
		this.scolledEvent = new Event<>();
		
		this.scrollButton.setRenderer(DEFAULT_SCROLLBUTTON_RENDERER);
		this.setRenderer(DEFAULT_RENDERER);
	}
	
	public boolean isHorizontal() {
		return !this.vertical;
	}	
	public boolean isVertical() {
		return this.vertical;
	}
	
	public void setVertical(boolean vertical) {
		this.vertical = vertical;
		this.repositionScrollButton();
	}
	
	public void setHorizontal(boolean horizontal) {
		this.vertical = !horizontal;
		this.repositionScrollButton();
	}
	
	public Texture2D getBackground() {
		return background;
	}

	public void setBackground(Texture2D background) {
		this.background = background;
	}

	public Button getScrollButton() {
		return scrollButton;
	}
	public void setScrollButton(Button scrollButton) {
		this.scrollButton = scrollButton;
		this.repositionScrollButton();
	}

	public int getStepSize() {
		return stepSize;
	}

	public void setStepSize(int stepSize) {
		this.stepSize = stepSize;
	}
	
	public int getScrollMax() {
		return scrollMax;
	}

	public void setScrollMax(int scrollMax) {
		if(scrollMax < 0)
			this.scrollMax = 0;
		else
			this.scrollMax = scrollMax;
		
		this.repositionScrollButton();
	}

	public int getScrollValue() {
		return scrollValue;
	}

	public void setScrollValue(int scrollValue) {
		this.scrollValue = MathHelper.clamp(0, this.scrollMax, scrollValue);
		this.onScrolled();
	}
	
	protected void repositionScrollButton() {		
		double ratio = this.scrollValue / (double)this.scrollMax;
		if(this.isHorizontal()) {
			repositionHorizontal(ratio);
		} else {
			repositionVertical(ratio);		
		}
	}

	protected void repositionVertical(double ratio) {	
		int center = (int)(this.getBounds().Height * ratio);	
		int size = this.getBounds().Height / 10;
		
		int top = MathHelper.clamp(0, this.getBounds().Height - size, (center - size / 2));
		
		Rectangle newBounds = new Rectangle(0, top, this.getBounds().Width, size);
		this.scrollButton.setBounds(newBounds);
	}

	protected void repositionHorizontal(double ratio) {
		int center = (int)(this.getBounds().Width * ratio);	
		int size = this.getBounds().Width / 10;
		
		int left = MathHelper.clamp(0, this.getBounds().Width - size, (center - size / 2));
		
		Rectangle newBounds = new Rectangle(left, 0, size, this.getBounds().Height);
		this.scrollButton.setBounds(newBounds);		
	}
	
	private void onScrolled() {
		ScrollEventArgs args = new ScrollEventArgs(this.scrollValue);
		this.scolledEvent.invoke(this, args);
		this.repositionScrollButton();
	}

	@Override
	protected void onMouseDrag(Mouse mouse) {
		super.onMouseDrag(mouse);
		Rectangle rect = this.getAbsolueBounds();
		int newScrollValue;
		double ratio;
		if(this.isHorizontal()) {
			newScrollValue =(int)(mouse.getMouseState().ViewCoords.X - rect.X);
			ratio = newScrollValue / (double)rect.Width;
		} else {
			newScrollValue = (int)(mouse.getMouseState().ViewCoords.Y - rect.Y);
			ratio = newScrollValue / (double)rect.Height;
		}		
		
		newScrollValue = (int)(ratio * this.scrollMax);
		this.setScrollValue(newScrollValue);
	}
	@Override
	protected void onMouseDown(Mouse mouse, MouseButton button) {
		this.scrollButton.onMouseDown(mouse, button);
	}
	@Override
	protected void onMouseUp(Mouse mouse, MouseButton button) {
		this.scrollButton.onMouseUp(mouse, button);
	}
	
	public void addScrollListener(IEventListener<ScrollEventArgs> listener) {
		this.scolledEvent.addListener(listener);
	}
	public void removeScrollListener(IEventListener<ScrollEventArgs> listener) {
		this.scolledEvent.removeListener(listener);
	}
}
