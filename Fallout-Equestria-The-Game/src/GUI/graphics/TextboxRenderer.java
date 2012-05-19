package GUI.graphics;

import GUI.LookAndFeelAssets;
import GUI.controls.Textfield;
import math.Vector2;
import graphics.RenderTarget2D;
import graphics.SpriteBatch;
import graphics.Texture2D;
import graphics.TextureFont;
import utils.GameTime;
import utils.Rectangle;

public class TextboxRenderer implements IGUIRenderer<Textfield>{
	private static final String DEFAULT_BACKGROUND = LookAndFeelAssets.Textfield_BG.toString();
	private static final String DEFAULT_FOCUS = LookAndFeelAssets.Textfield_Focus.toString();
	private static final int borderSpacing = 10;
	private static final int verticalBorderSpacing = 4;
	
	private final String backgroundKey;
	private final String focusKey;
	
	public TextboxRenderer() {
		this(DEFAULT_BACKGROUND, DEFAULT_FOCUS);
	}
	
	
	public TextboxRenderer(String backgroundKey, String focusKey) {
		this.backgroundKey = backgroundKey;
		this.focusKey = focusKey;
	}

	
	@Override
	public Class<Textfield> getRenderedType() {
		return Textfield.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, Textfield control,LookAndFeel lookAndFeel, RenderTarget2D target) {
		VisibleElement backgroundElement = lookAndFeel.getElement(backgroundKey);
		VisibleElement focusElement = lookAndFeel.getElement(focusKey);
		
		String text = control.getText();
		TextureFont font = control.getFont();
		
		
		if(control.isFocused() && control.isEditable()) {
			renderFocused(batch, control, text, font, focusElement);	
		} else {
			renderNonFocused(batch, control,text, font,backgroundElement);
		}
	}

	private void renderNonFocused(SpriteBatch batch, Textfield control, String text,
			TextureFont font, VisibleElement backgroundElement) {
		batch.draw(backgroundElement.getTexture(), 
				   control.getDimention(), 
				   control.getBgColor(), 
				   backgroundElement.getSrcRect());
		
		batch.drawString(font, text, new Vector2(borderSpacing, 0), control.getFgColor());
	}

	private void renderFocused(SpriteBatch batch, Textfield control,
			String text, TextureFont font, VisibleElement focusElement) {
		
		batch.draw(focusElement.getTexture(), 
				   control.getDimention(), 
				   control.getBgColor(), 
				   focusElement.getSrcRect());
		
		Vector2 messure = font.meassureString(text);
		if(messure.X > control.getDimention().Width - borderSpacing * 2) {
			text = getCorrectSubstring(control, text, font);
		}
		
		batch.drawString(font, text, new Vector2(borderSpacing, 0), control.getFgColor());
		Rectangle rect = createMarkerBounds(control, text, font);
		batch.draw(Texture2D.getPixel(), rect, control.getFgColor(), null);
	}

	private Rectangle createMarkerBounds(Textfield control, String text,
			TextureFont font) {
		
		Vector2 position = getCorrectPosition(control, text);
	
		
		Rectangle rect = new Rectangle((int)position.X, verticalBorderSpacing, 2, 
									   (int)(position.Y - verticalBorderSpacing * 2));
		return rect;
	}

	private Vector2 getCorrectPosition(Textfield control, String text) {
		if(text == "" || control.getMarkerPosition() == 0) {
			return new Vector2(borderSpacing, control.getFont().getLineSpacing());
		}
		
		
		int offset = control.getText().length() - text.length();	
		String substr = text.substring(0, control.getMarkerPosition() - offset);
		Vector2 messure = control.getFont().meassureString(substr);
		float characterSpacing = control.getFont().getCharacterSpacing();
		
		
		return new Vector2(messure.X + borderSpacing + (characterSpacing / 2), messure.Y);
	}

	private String getCorrectSubstring(Textfield control, String text,
			TextureFont font) {
		Vector2 messure;
		String modifiedText = text.substring(control.getMarkerPosition());
		messure = font.meassureString(modifiedText);
		if(messure.X < control.getDimention().Width - borderSpacing * 2) {
			text = creteRightAlignedText(text, font, control.getDimention(), control.getMarkerPosition());
		} else {
			text = modifiedText;
		}
		return text;
	}

	private String creteRightAlignedText(String text, TextureFont font,
			Rectangle dimention, int markerPositon) {
		
		String result = text.substring(markerPositon);
		for (int i = markerPositon - 1; font.meassureString(result).X < dimention.Width - borderSpacing *2 ; i--) {
			result = text.substring(i);
		}
		
		return result;
	}

}
