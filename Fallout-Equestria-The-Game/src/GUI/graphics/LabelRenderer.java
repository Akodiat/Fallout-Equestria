package GUI.graphics;

import GUI.FontHelper;
import GUI.controls.Label;
import math.Vector2;
import graphics.RenderTarget2D;
import graphics.SpriteBatch;
import graphics.TextureFont;
import utils.GameTime;

public class LabelRenderer implements IGUIRenderer<Label>{
	
	@Override
	public Class<Label> getRenderedType() {
		return Label.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, Label control,LookAndFeel lookAndFeel, RenderTarget2D target) {
			
		TextureFont font = (control.getFont() != null) ? control.getFont() : lookAndFeel.getDefaultFont();
		String text = control.getText();
		
		Vector2 textDim = font.meassureString(text);
		
		if(textDim.Y > control.getBounds().Height)
			return;
		if(textDim.X > control.getBounds().Width) {
			text = FontHelper.fixTextForRendering(font, text, control.getBounds());
		}
	
		if(text != null)
			batch.drawString(font, text,Vector2.Zero, control.getFgColor());

	}


}
