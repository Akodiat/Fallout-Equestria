package GUI.graphics;

import GUI.FontHelper;
import GUI.LookAndFeelAssets;
import GUI.controls.ButtonBase;
import math.Vector2;
import graphics.RenderTarget2D;
import graphics.SpriteBatch;
import graphics.TextureFont;
import utils.GameTime;

public class ButtonRenderer implements IGUIRenderer<ButtonBase>{

	private static final String DEFAULT_BUTTON_BACKGROUND = LookAndFeelAssets.Button_BG.toString();
	private static final String DEFAULT_BUTTON_DOWN 	  = LookAndFeelAssets.Button_Down.toString();
	private static final String DEFAULT_BUTTON_OVER		  = LookAndFeelAssets.Button_Over.toString();
	
	private String backgroundKey;
	private String overKey;
	private String downKey;
	
	public ButtonRenderer() {
		this(DEFAULT_BUTTON_BACKGROUND,
			 DEFAULT_BUTTON_DOWN,
			 DEFAULT_BUTTON_OVER);
	}
	
	
	
	
	public ButtonRenderer(String backgroundKey,
						  String overKey, 
						  String downKey) {
		this.backgroundKey = backgroundKey;
		this.overKey = overKey;
		this.downKey = downKey;
	}

	@Override
	public Class<ButtonBase> getRenderedType() {
		return ButtonBase.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, ButtonBase control, LookAndFeel lookAndFeel, RenderTarget2D target) {
		VisibleElement backgroundElement = lookAndFeel.getElement(backgroundKey);
		VisibleElement overElement		 = lookAndFeel.getElement(overKey);
		VisibleElement downElement		 = lookAndFeel.getElement(downKey);
		
		
		
		batch.draw(backgroundElement.getTexture(), 
				  control.getDimention(), 
				  control.getBgColor(), 
				  backgroundElement.getSrcRect());
		
		
		if(control.isPressed() && control.isMouseHover()) {
			batch.draw(downElement.getTexture(), control.getDimention(), control.getFgColor(), downElement.getSrcRect());	
		} else if(control.isMouseHover()) {
			batch.draw(overElement.getTexture(), control.getDimention(), control.getFgColor(), overElement.getSrcRect());	
		}
		
		drawText(batch, control, lookAndFeel);
	}

	protected void drawText(SpriteBatch batch, ButtonBase control, LookAndFeel lookAndFeel) {
		String text = control.getText();
		TextureFont font = (control.getFont() != null) ? control.getFont() : lookAndFeel.getDefaultFont();
		
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
