package GUI.graphics;

import GUI.controls.Textfield;
import math.Vector2;
import graphics.SpriteBatch;
import graphics.Texture2D;
import graphics.TextureFont;
import utils.GameTime;
import utils.Rectangle;

public class TextboxRenderer implements IGUIRenderer<Textfield>{
	private static final String DEFAULT_BACKGROUND = "Textfield_Background";
	private static final int borderSpacing = 10;
	private static final int verticalBorderSpacing = 4;
	
	private final String backgroundKey;
	
	public TextboxRenderer() {
		this(DEFAULT_BACKGROUND);
	}
	
	
	public TextboxRenderer(String backgroundKey) {
		this.backgroundKey = backgroundKey;
	}

	
	@Override
	public Class<Textfield> getRenderedType() {
		return Textfield.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, Textfield control,LookAndFeel lookAndFeel) {
		VisibleElement backgroundElement = lookAndFeel.getElement(backgroundKey);
		
		String text = control.getText();
		TextureFont font = control.getFont();
		
		batch.draw(backgroundElement.getTexture(), 
				   control.getDimention(), 
				   control.getBgColor(), 
				   backgroundElement.getSrcRect());
		
		if(control.isFocused() && control.isEditable()) {
			renderFocused(batch, control, text, font);	
		} else {
			renderNonFocused(batch, control,text, font);
		}
	}

	private void renderNonFocused(SpriteBatch batch, Textfield control, String text,
			TextureFont font) {
		batch.drawString(font, text, new Vector2(borderSpacing, 0), control.getFgColor());
	}

	private void renderFocused(SpriteBatch batch, Textfield control,
			String text, TextureFont font) {
		
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
