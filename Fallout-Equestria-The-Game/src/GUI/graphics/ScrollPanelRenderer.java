package GUI.graphics;

import GUI.controls.ScrollPanel;
import graphics.SpriteBatch;
import graphics.Texture2D;
import utils.GameTime;

public class ScrollPanelRenderer implements IGUIRenderer<ScrollPanel>{

	@Override
	public Class<ScrollPanel> getRenderedType() {
		return ScrollPanel.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, ScrollPanel control,LookAndFeel lookAndFeel) {
		batch.draw(Texture2D.getPixel(), control.getDimention(), control.getBgColor(), null);		
	}

}
