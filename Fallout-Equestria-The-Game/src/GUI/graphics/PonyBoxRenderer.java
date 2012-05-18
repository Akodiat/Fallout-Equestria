package GUI.graphics;

import content.ContentManager;
import math.Vector2;
import GUI.controls.PonyBox;
import graphics.Color;
import graphics.RenderTarget2D;
import graphics.ShaderEffect;
import graphics.SpriteBatch;
import graphics.Texture2D;
import utils.GameTime;
import utils.Rectangle;

public class PonyBoxRenderer implements IGUIRenderer<PonyBox>{

	Texture2D testlol = new ContentManager("resources").loadTexture("HEJHEJ.png");
	
	@Override
	public Class<PonyBox> getRenderedType() {
		return PonyBox.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, PonyBox control, LookAndFeel lookAndFeel, RenderTarget2D target) {
		batch.begin();
		Vector2 position = new Vector2(control.getPosition().X, control.getPosition().Y);
		
		batch.drawString(lookAndFeel.getDefaultFont(), control.getPonyName(), position, Color.White);
		
		control.getPonyPlayer().update(time);
		control.getPonyPlayer().draw(batch, position, false, 0, Color.White, new Vector2(2,2));
		batch.draw(testlol, new Vector2((float)Math.random()*2*(-1366) + 1366,(float)Math.random()*2*(-768) + 768), Color.White);
	}
}
