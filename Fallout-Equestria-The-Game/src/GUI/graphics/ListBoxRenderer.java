package GUI.graphics;

import math.Point2;
import math.Vector2;

import GUI.controls.ListBox;

import com.google.common.collect.ImmutableList;

import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;
import utils.GameTime;

@SuppressWarnings("rawtypes")
public class ListBoxRenderer implements IGUIRenderer<ListBox>{

	@Override
	public Class<ListBox> getRenderedType() {
		return ListBox.class;
	}

	@Override
	public void render(SpriteBatch batch, GameTime time, ListBox control,LookAndFeel lookAndFeel) {
		float itemHeight = control.getFont().getLineSpacing() + control.getItemPadding();		
		int selectedItemIndex = control.getSelecteItemIndex();
		Point2 viewOffset = control.getViewOffset();
		
		
		batch.draw(Texture2D.getPixel(), control.getDimention(), control.getBgColor(), null);
		
		@SuppressWarnings("unchecked")
		ImmutableList<String> itemStrings = control.getItemsAsStrings();
		for (int i = 0; i < itemStrings.size(); i++) {
			String text = this.trimEventualLines(itemStrings.get(i));
			Vector2 position = new Vector2(-viewOffset.X,
											itemHeight * i - viewOffset.Y);
			
			Color color = (i == selectedItemIndex) ? control.getSelectedItemColor() : control.getFgColor();
			
			batch.drawString(control.getFont(), text, position, color);		
		}		
		
	}
	
	private String trimEventualLines(String text) {
		if(text.contains("\n")) {
			return text.substring(0, text.indexOf("\n"));
		} else {
			return text;
		}
	}

}
