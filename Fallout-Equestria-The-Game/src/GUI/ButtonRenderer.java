package GUI;

import math.Vector2;
import graphics.SpriteBatch;
import graphics.Texture2D;
import graphics.TextureFont;
import utils.GameTime;
import utils.Rectangle;

public class ButtonRenderer implements IGUIRenderer<Button>{

	@Override
	public Class<Button> getRenderedType() {
		return Button.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, Button control) {
		
		Texture2D background = control.getImage();
		if(background != null) {
			batch.draw(background, control.getDimention(), control.getBgColor(), null);
		} 
		
		if(control.isPressed() && control.isMouseHover()) {
		 	Texture2D texture = control.getDownTexture();
		 	if(texture != null) {
		 		batch.draw(texture, control.getDimention(), control.getFgColor(), null);
		 	}		
		} else if(control.isMouseHover()) {
			Texture2D texture = control.getOverTexture();
		 	if(texture != null) {
		 		batch.draw(texture, control.getDimention(), control.getFgColor(), null);
		 	}		
		}
		
		drawText(batch, control);
	}

	private void drawText(SpriteBatch batch, Button control) {
		String text = control.getText();
		TextureFont font = control.getFont();
		
		if(text == null || text.isEmpty())
			return;
		
		if(text.contains("\n")) {
			
		}
		
		Vector2 textDim = font.meassureString(text);
		
		if(textDim.Y > control.getBounds().Height)
			return;
		if(textDim.X > control.getBounds().Width) {
			text = FontHelper.fixTextForRendering(font, text, control.getBounds());
		}
		
		textDim = font.meassureString(text);
		batch.drawString(font, text, control.getDimention().getCenter(), control.getTextColor(),Vector2.mul(0.5f, textDim),Vector2.One, 0.0f, false);
	}

}
