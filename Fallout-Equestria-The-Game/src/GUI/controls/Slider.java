package GUI.controls;

import GUI.graphics.SliderRenderer;
import math.MathHelper;
import math.Point2;

public class Slider extends ScrollBar {
	public static final SliderRenderer DEFAULT_RENDERER = new SliderRenderer();
	
	public Slider() {
		super();
		this.scrollButton.setBounds(0,0,10,35);
		this.setRenderer(DEFAULT_RENDERER);
	}
	
	protected void repositionScrollButton() {
		
		
		double ratio = this.getScrollValue() / (double)this.getScrollMax();
		if(this.isHorizontal()) {
			repositionHorizontal(ratio);
		} else {
			repositionVertical(ratio);		
		}
	}

	protected void repositionVertical(double ratio) {	
		int buttonCenterY = this.getScrollButton().getBounds().Height / 2;
		if(this.getBounds().Height < buttonCenterY * 2)
			return;
			
		
		int pos = (int)(ratio * this.getBounds().Height);
		pos -= buttonCenterY;
		
		pos = MathHelper.clamp(0, this.getBounds().Height - buttonCenterY * 2, pos);
		
		
		this.getScrollButton().setPosition(new Point2(0, pos));
	}

	protected void repositionHorizontal(double ratio) {
		int buttonCenterX = this.getScrollButton().getBounds().Width / 2;
		if(this.getBounds().Width < buttonCenterX * 2)
			return;
			
		
		int pos = (int)(ratio * this.getBounds().Width);
		pos -= buttonCenterX;
		
		pos = MathHelper.clamp(0, this.getBounds().Width - buttonCenterX * 2, pos);
		
		this.getScrollButton().setPosition(new Point2(pos,0));	
	}
}
