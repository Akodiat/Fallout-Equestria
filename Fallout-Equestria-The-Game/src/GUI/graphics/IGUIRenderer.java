package GUI.graphics;

import GUI.controls.GUIControl;
import graphics.SpriteBatch;
import utils.GameTime;

public interface IGUIRenderer<T extends GUIControl> {
	public Class<T> getRenderedType();
	void render(SpriteBatch batch, GameTime time, T control, LookAndFeel lookAndFeel);
}
