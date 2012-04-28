package GUI;

import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;
import utils.GameTime;
import utils.Rectangle;

public class SliderRenderer implements IGUIRenderer<Slider>{

	@Override
	public Class<Slider> getRenderedType() {
		return Slider.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, Slider control) {	
		Rectangle rect = control.getDimention();
		
		batch.draw(control.getBackground(), new Rectangle(0,rect.Height / 4,rect.Width, rect.Height / 2), control.getBgColor(), null);	
		
		
		Rectangle filledRect = new Rectangle(0, rect.Height / 4,
								(int)(rect.Width * (control.getSlideValue() / (double)control.getSlideMax())), rect.Height / 2);
		
		//Make nicer here!
		batch.draw(Texture2D.getPixel(), filledRect, new Color(Color.Purple, 0.8f), null);	
	}

}
