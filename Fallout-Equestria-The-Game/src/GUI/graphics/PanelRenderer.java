package GUI.graphics;

import GUI.controls.Panel;
import graphics.RenderTarget2D;
import graphics.SpriteBatch;
import graphics.Texture2D;
import utils.GameTime;
import utils.Rectangle;

public class PanelRenderer implements IGUIRenderer<Panel>{
	@Override
	public Class<Panel> getRenderedType() {
		return Panel.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, Panel control, LookAndFeel lookAndFeel, RenderTarget2D target) {	
		Rectangle dim = new Rectangle(-10,-10,control.getBounds().Width + 1000, control.getBounds().Height + 1000);	
		batch.draw(Texture2D.getPixel(), dim, control.getBgColor(), null);	
	}

}
