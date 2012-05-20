package GUI.graphics;

import GUI.controls.GUIControl;
import graphics.RenderTarget2D;
import graphics.SpriteBatch;
import utils.time.GameTime;

public interface IGUIRenderer<T extends GUIControl> {
	public Class<T> getRenderedType();
	void render(SpriteBatch batch, GameTime time, T control, LookAndFeel lookAndFeel, RenderTarget2D target);
}
