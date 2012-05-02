package GUI.graphics;

import GUI.VisibleElement;
import GUI.controls.Slider;
import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;
import utils.GameTime;
import utils.Rectangle;

public class SliderRenderer implements IGUIRenderer<Slider>{
	public static final String DEFAULT_SLIDER_BACKGROUND = "Slider_Background";
	
	@Override
	public Class<Slider> getRenderedType() {
		return Slider.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, Slider control,LookAndFeel lookAndFeel) {	
		VisibleElement backgroundElement = lookAndFeel.getElement(DEFAULT_SLIDER_BACKGROUND);
		
		
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
		
		batch.draw(Texture2D.getPixel(), filledRect, new Color(Color.Purple, 0.8f), null);	
	}

}
