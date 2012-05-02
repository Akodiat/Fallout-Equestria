package GUI.graphics;

import GUI.controls.ImageBox;
import graphics.SpriteBatch;
import utils.GameTime;

public class ImageBoxRenderer implements IGUIRenderer<ImageBox>{

	@Override
	public Class<ImageBox> getRenderedType() {
		return ImageBox.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, ImageBox control, LookAndFeel lookAndFeel) {
		batch.draw(control.getImage(), control.getDimention(), control.getFgColor(), control.getImageSrcRect());		
	}

}
