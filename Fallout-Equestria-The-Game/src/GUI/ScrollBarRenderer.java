package GUI;

import graphics.RenderTarget2D;
import graphics.SpriteBatch;
import utils.time.GameTime;

public class ScrollBarRenderer implements IGUIRenderer<ScrollBar>{
	
	private static final String DEFAULT_BACKGROUND = LookAndFeelAssets.Scrollbar_BG.toString();
	private static final String DEFAULT_H_BACKGROUND = LookAndFeelAssets.HorisontalScrollbar_BG.toString();
	
	
	private final String backgroundKey;
	private final String hBackgroundKey;
	
	
	
	public ScrollBarRenderer() {
		this(DEFAULT_BACKGROUND, DEFAULT_H_BACKGROUND);
	}
	
	public ScrollBarRenderer(String backgroundKey, String hBackgroundKey) {
		this.backgroundKey = backgroundKey;
		this.hBackgroundKey = hBackgroundKey;
	}

	@Override
	public Class<ScrollBar> getRenderedType() {
		return ScrollBar.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, ScrollBar control, LookAndFeel lookAndFeel, RenderTarget2D target) {
		VisibleElement backgroundElement = lookAndFeel.getElement(backgroundKey);
		if(control.isHorizontal()) {
			backgroundElement = lookAndFeel.getElement(hBackgroundKey);
		}		
		
		batch.draw(backgroundElement.getTexture(),
				   control.getDimention(), 
				   control.getBgColor(), 
				   backgroundElement.getSrcRect());
	}

}
