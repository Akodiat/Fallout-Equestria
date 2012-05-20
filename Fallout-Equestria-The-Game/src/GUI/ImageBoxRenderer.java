package GUI;

import graphics.RenderTarget2D;
import graphics.SpriteBatch;
import utils.time.GameTime;

public class ImageBoxRenderer implements IGUIRenderer<ImageBox>{

	@Override
	public Class<ImageBox> getRenderedType() {
		return ImageBox.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, ImageBox control, LookAndFeel lookAndFeel, RenderTarget2D target) {
		batch.draw(control.getImage(), control.getDimention(), control.getFgColor(), control.getImageSrcRect());		
	}

}
