package GUI;

import utils.Mouse;
import utils.MouseButton;
import utils.Rectangle;
import math.MathHelper;
import math.Point2;
import misc.IEventListener;
import graphics.Texture2D;

public class Slider extends GUIControl {
	
	private int slideMax;
	private int slideValue;
	
	
	private Texture2D background;
	
	private final Button slidingButton;
	private Event<ScrollEventArgs> scrolledEvent;
	
	
	public Slider(Button slidingButton) {
		this.slidingButton = slidingButton;
		this.addChild(slidingButton);
		this.scrolledEvent = new Event<>();
	}
	
	public int getSlideMax() {
		return slideMax;
	}
	public void setSlideMax(int slideMax) {
		this.slideMax = slideMax;
	}
	public int getSlideValue() {
		return slideValue;
	}
	public void setSlideValue(int slideValue) {
		if(slideValue < 0) {
			slideValue = 0;
		} else if(slideValue > this.slideMax) {
			slideValue = this.slideMax;
		}
		
		this.slideValue = slideValue;
		this.onScrolledValueChanged();
	}
	public Button getSlidingButton() {
		return slidingButton;
	}
	public Texture2D getBackground() {
		return background;
	}
	public void setBackground(Texture2D background) {
		this.background = background;
	}
	public float getRatio() {
		return this.slideValue / (float)this.slideMax;
	}
	
	protected void onScrolledValueChanged() {
		ScrollEventArgs args = new ScrollEventArgs(this.slideValue);
		scrolledEvent.invoke(this, args);
		this.repositionSliderButton();
	}

	private void repositionSliderButton() {
		double ratio = slideValue / (double)slideMax;
		int buttonCenterX = this.slidingButton.getBounds().Width / 2;
		int pos = (int)(ratio * this.getBounds().Width);
		pos -= buttonCenterX;
		
		pos = MathHelper.clamp(0, this.getBounds().Width - buttonCenterX * 2, pos);
		
		
		this.slidingButton.setPosition(new Point2(pos, 0));
	}
	
	@Override
	protected void onMouseDown(Mouse mouse, MouseButton button) {
		this.slidingButton.onMouseDown(mouse, button);
	}
	
	@Override
	protected void onMouseUp(Mouse mouse, MouseButton button) {
		this.slidingButton.onMouseUp(mouse, button);
	}
	
	@Override
	protected void onMouseDrag(Mouse mouse) {
		super.onMouseDrag(mouse);
		Rectangle rect = this.getAbsolueBounds();
		int pos = (int)(mouse.getMouseState().ViewCoords.X - rect.X);
		double ratio = pos / (double)rect.Width;		
		pos = (int)(ratio * this.slideMax);
		System.out.println(pos);
		this.setSlideValue(pos);
	}
	
	
	public void addScrollListener(IEventListener<ScrollEventArgs> listener) {
		this.scrolledEvent.addListener(listener);
	}
	public void removeScrollListener(IEventListener<ScrollEventArgs> listener) {
		this.scrolledEvent.removeListener(listener);
	}
	
}
