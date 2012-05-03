package GUI.graphics;

import java.util.List;

import com.google.common.collect.ImmutableList;

import math.Matrix4;
import math.Point2;
import math.Vector2;

import GUI.LookAndFeelAssets;
import GUI.controls.TextArea;
import graphics.Color;
import graphics.RenderTarget2D;
import graphics.SpriteBatch;
import utils.GameTime;
import utils.Rectangle;

public class TextAreaRenderer implements IGUIRenderer<TextArea>{
	private static final String DEfAULT_TEXTAREA_BACKGROUND = LookAndFeelAssets.Textarea_BG.toString();
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
			LookAndFeel lookAndFeel, RenderTarget2D target) {
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

		batch.end();
		

		RenderTarget2D innerTarget = createTextTarget(control);
		Rectangle sr = batch.getViewport();
		batch.setViewport(new Rectangle(0,0, innerTarget.getTexture().Width, innerTarget.getTexture().Height));
		
		
		batch.begin(null,Matrix4.Identity,innerTarget);
		
		
		Point2 offset = new Point2(0,-control.getScrollOffset() + control.getMargin());
		batch.drawString(control.getFont(), 
						 builder.toString(), 
						 new Vector2(offset.X, offset.Y),
						 control.getFgColor());
		



		batch.end();
		
		batch.setViewport(sr);
		

		batch.begin(null,Matrix4.Identity,target);
		Vector2 targetOffset = new Vector2(control.getMargin() * 2, control.getMargin());
		batch.draw(innerTarget.getTexture(), targetOffset, Color.White);
		batch.end();
		
		innerTarget.destroy();
		
		batch.begin(null,Matrix4.Identity,target);
		
	}

	private RenderTarget2D createTextTarget(TextArea control) {
		int width = control.getDimention().Width - control.getMargin() * 2;
		int height = control.getDimention().Height - control.getMargin() * 2;
		
		return new RenderTarget2D(width, height);
	}

}
