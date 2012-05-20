package GUI;

import graphics.Color;
import math.MathHelper;
import math.Point2;

public class Slider extends ScrollBar {
	public static final SliderRenderer DEFAULT_RENDERER = new SliderRenderer();
	private static final ButtonRenderer DEFAULT_SLIDER_BUTTON_RENDERER 
							= new ButtonRenderer(LookAndFeelAssets.SliderButton_Over.toString(),
												 LookAndFeelAssets.SliderButton_Over.toString(),
										    	 LookAndFeelAssets.SliderButton_Down.toString());	

	
	public Slider() {
		super();
		this.scrollButton.setBounds(0,0,20,30);
		this.scrollButton.setRenderer(DEFAULT_SLIDER_BUTTON_RENDERER);
		this.setRenderer(DEFAULT_RENDERER);
	}
	
	@Override
	public void setFgColor(Color color) {
		super.setFgColor(color);
		this.scrollButton.setFgColor(color);
		this.scrollButton.setBgColor(color);
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
	
	
	protected void changeScrollRenderer() {
		this.scrollButton.setRenderer(DEFAULT_SLIDER_BUTTON_RENDERER);	
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
