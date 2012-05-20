package GUI.graphics;

import GUI.LookAndFeelAssets;
import GUI.controls.Slider;
import graphics.RenderTarget2D;
import graphics.SpriteBatch;
import utils.Rectangle;
import utils.time.GameTime;

public class SliderRenderer implements IGUIRenderer<Slider>{
	public static final String DEFAULT_SLIDER_BACKGROUND = LookAndFeelAssets.Slider_BG.toString();
	public static final String DEFAULT_SLIDER_OVERLAY = LookAndFeelAssets.Slider_Overlay.toString();
	public static final String DEFAULT_V_SLIDER_BACKGROUND = LookAndFeelAssets.VerticalSlider_BG.toString();
	public static final String DEFAULT_V_SLIDER_OVERLAY = LookAndFeelAssets.VerticalSlider_Overlay.toString();
	
	
	
	@Override
	public Class<Slider> getRenderedType() {
		return Slider.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, Slider control,LookAndFeel lookAndFeel, RenderTarget2D target) {
		VisibleElement backgroundElement = lookAndFeel.getElement(DEFAULT_SLIDER_BACKGROUND);
		VisibleElement overlayElement = lookAndFeel.getElement(DEFAULT_SLIDER_OVERLAY);
		if(control.isVertical()) {
			backgroundElement = lookAndFeel.getElement(DEFAULT_V_SLIDER_BACKGROUND);
			overlayElement = lookAndFeel.getElement(DEFAULT_V_SLIDER_OVERLAY);
		}
		
		
		Rectangle rect = control.getDimention();
		Rectangle filledRect;		
		double filledRatio = (control.getScrollValue() / (double)control.getScrollMax());
		
		if(control.isHorizontal()) {
			rect = new Rectangle(0,rect.Height / 4,rect.Width, rect.Height / 2);
			filledRect = new Rectangle(0, rect.Y, (int)(rect.Width * filledRatio), rect.Height);
		} else {
			rect = new Rectangle(rect.Width / 4, 0, rect.Width / 2, rect.Height);
			filledRect = new Rectangle(rect.X, 0, rect.Width, (int)(rect.Height * filledRatio));
		}
		
		
		
		batch.draw(backgroundElement.getTexture(), 
				   rect, 
				   control.getBgColor(),
				   backgroundElement.getSrcRect());
		
		batch.draw(overlayElement.getTexture(), filledRect, control.getFgColor(), overlayElement.getSrcRect());	
	}

}
