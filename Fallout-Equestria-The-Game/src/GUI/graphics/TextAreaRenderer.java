package GUI.graphics;

import java.util.List;
import math.Matrix4;
import math.Point2;
import math.Vector2;
import GUI.LookAndFeelAssets;
import GUI.controls.TextArea;
import graphics.Color;
import graphics.RenderTarget2D;
import graphics.SpriteBatch;
import utils.Rectangle;
import utils.time.GameTime;

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
		Rectangle correctedBG =  new Rectangle(0,0, control.getDimention().Width - control.getScrollBarSize(), 
												    control.getDimention().Height);
		batch.draw(backgroundElement.getTexture(),
				   correctedBG,
				   control.getBgColor(),
				   backgroundElement.getSrcRect());
		batch.end();

		
		
		Rectangle correct = new Rectangle(control.getMargin(),control.getMargin(),
										  correctedBG.Width - control.getMargin() * 2, 
										  correctedBG.Height - control.getMargin() * 2);


		RenderTarget2D innerTarget = new RenderTarget2D(correct.Width, correct.Height);
		
		drawText(batch, control, backgroundElement,correctedBG, innerTarget);
		

		//Blitting (copying the pixels to the better rendertarget).
		innerTarget.blitToTarget(target, correct);
		innerTarget.destroy();
		
		batch.begin(null,Matrix4.Identity,target);
		
	}

	private void drawText(SpriteBatch batch, TextArea control,
			VisibleElement backgroundElement, 
			Rectangle bgRect,
			RenderTarget2D innerTarget) {
		
		
		List<String> lines = control.getLines();	
		StringBuilder builder = new StringBuilder();
		for (String line : lines) {
			builder.append(line);
			builder.append('\n');
		}
		
		batch.begin(null,Matrix4.Identity,innerTarget);
		batch.clearScreen(Color.Transparent);
		
		//Text have a habit of not playing nice with the rendertargets so we repaint and blit the targets instead.
		
		Vector2 offset = new Vector2(0,-control.getScrollOffset() - control.getMargin());
		
		Rectangle correctBG_Offseted = bgRect.offset(new Point2(-control.getMargin(), -control.getMargin()));
		
		batch.draw(backgroundElement.getTexture(),
				   correctBG_Offseted, 
				   control.getBgColor(),
				   backgroundElement.getSrcRect());
		
		batch.drawString(control.getFont(), 
						 builder.toString(), 
						 offset,
						 control.getFgColor());
		
		


		batch.end();
	}
}
