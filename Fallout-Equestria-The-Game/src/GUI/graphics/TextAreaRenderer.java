package GUI.graphics;

import java.util.List;

import math.Point2;
import math.Vector2;

import GUI.VisibleElement;
import GUI.controls.TextArea;
import graphics.SpriteBatch;
import utils.GameTime;

public class TextAreaRenderer implements IGUIRenderer<TextArea>{
	private static final String DEfAULT_TEXTAREA_BACKGROUND = "TextArea_Background";
	private static final int BORDER_OFFSET = 4;
	
	private final String backgroundKey;
	
	public TextAreaRenderer() {
		this(DEfAULT_TEXTAREA_BACKGROUND);
	}
	
	public TextAreaRenderer(String backgroundKey) {
		this.backgroundKey = backgroundKey;
	}
	
	@Override
	public Class<TextArea> getRenderedType() {
		return TextArea.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, TextArea control,
			LookAndFeel lookAndFeel) {
		VisibleElement backgroundElement = lookAndFeel.getElement(backgroundKey);
		batch.draw(backgroundElement.getTexture(),
				   control.getDimention(), 
				   control.getBgColor(),
				   backgroundElement.getSrcRect());
		
		List<String> lines = control.getLines();
		
		
		StringBuilder builder = new StringBuilder();
		for (String line : lines) {
			builder.append(line);
			builder.append('\n');
		}
		
		Point2 offset = new Point2(BORDER_OFFSET,-control.getScrollOffset());
		batch.drawString(control.getFont(), 
						 builder.toString(), 
						 new Vector2(offset.X, offset.Y),
						 control.getFgColor());
	}

}
