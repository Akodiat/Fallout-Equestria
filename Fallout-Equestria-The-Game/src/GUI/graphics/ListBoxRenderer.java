package GUI.graphics;

import math.Matrix4;
import math.Point2;
import math.Vector2;

import GUI.LookAndFeelAssets;
import GUI.controls.ListBox;

import com.google.common.collect.ImmutableList;

import graphics.Color;
import graphics.RenderTarget2D;
import graphics.SpriteBatch;
import utils.GameTime;
import utils.Rectangle;

@SuppressWarnings("rawtypes")
public class ListBoxRenderer implements IGUIRenderer<ListBox>{
	private static final String DEFAULT_BACKGROUND_KEY = LookAndFeelAssets.Textarea_BG.toString(); 
	
	@Override
	public Class<ListBox> getRenderedType() {
		return ListBox.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, ListBox control,LookAndFeel lookAndFeel, RenderTarget2D target) {
		float itemHeight = control.getFont().getLineSpacing() + control.getItemPadding();		
		int selectedItemIndex = control.getSelecteItemIndex();
		Point2 viewOffset = control.getViewOffset();
		
		VisibleElement element = lookAndFeel.getElement(DEFAULT_BACKGROUND_KEY);
		
		Rectangle dim = new Rectangle(0,0,control.getDimention().Width,control.getDimention().Height);
		batch.draw(element.getTexture(), dim, control.getBgColor(), element.getSrcRect());
		batch.end();
		
		RenderTarget2D innerTarget = createTextTarget(control);		
		batch.begin(null,Matrix4.Identity,innerTarget);
		batch.clearScreen(Color.Transparent);
		
		@SuppressWarnings("unchecked")
		ImmutableList<String> itemStrings = control.getItemsAsStrings();
		for (int i = 0; i < itemStrings.size(); i++) {
			String text = this.trimEventualLines(itemStrings.get(i));
			Vector2 position = new Vector2(-viewOffset.X,
											itemHeight * i - viewOffset.Y);
			
			Color color = (i == selectedItemIndex) ? control.getSelectedItemColor() : control.getFgColor();
			
			batch.drawString(control.getFont(), text, position, color);	
		}	
		batch.end();

		batch.begin(null,Matrix4.Identity,target);
		Vector2 offset = new Vector2(control.getMargin(), control.getMargin());
		batch.draw(innerTarget.getTexture(), offset, Color.White);

		batch.end();	
		innerTarget.destroy();
		

		batch.begin(null,Matrix4.Identity,target);
	}
	
	private RenderTarget2D createTextTarget(ListBox control) {
		int width = control.getDimention().Width - control.getMargin() * 2;
		int height = control.getDimention().Height - control.getMargin() * 2;
		return new RenderTarget2D(width, height);
	}

	private String trimEventualLines(String text) {
		if(text.contains("\n")) {
			return text.substring(0, text.indexOf("\n"));
		} else {
			return text;
		}
	}

}
