package GUI;

import graphics.SpriteBatch;
import utils.GameTime;
import utils.Rectangle;

public interface IGUIRenderer<T extends GUIControl> {
	public Class<T> getRenderedType();
	void render(SpriteBatch batch, GameTime time, T control);
}
