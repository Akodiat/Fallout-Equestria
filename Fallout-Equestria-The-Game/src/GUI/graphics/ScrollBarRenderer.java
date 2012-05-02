package GUI.graphics;

import GUI.VisibleElement;
import GUI.controls.ScrollBar;
import graphics.SpriteBatch;
import utils.GameTime;

public class ScrollBarRenderer implements IGUIRenderer<ScrollBar>{
	
	private static final String DEFAULT_BACKGROUND = "ScrollBar_Background";
	
	private final String backgroundKey;
	
	public ScrollBarRenderer() {
		this(DEFAULT_BACKGROUND);
	}
	
	public ScrollBarRenderer(String backgroundKey) {
		this.backgroundKey = backgroundKey;
	}

	@Override
	public Class<ScrollBar> getRenderedType() {
		return ScrollBar.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, ScrollBar control, LookAndFeel lookAndFeel) {
		VisibleElement backgroundElement = lookAndFeel.getElement(backgroundKey);
		
		batch.draw(backgroundElement.getTexture(),
				   control.getDimention(), 
				   control.getBgColor(), 
				   backgroundElement.getSrcRect());
	}

}
