package GUI.graphics;

import GUI.controls.Panel;
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
	public void render(SpriteBatch batch, GameTime time, Panel control, LookAndFeel lookAndFeel) {	
		Rectangle dim = new Rectangle(0,0,control.getBounds().Width, control.getBounds().Height);	
		batch.draw(Texture2D.getPixel(), dim, control.getBgColor(), null);	
	}

}
