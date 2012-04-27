package GUI;

import graphics.TextureFont;
import utils.Rectangle;

public class FontHelper {
	
	public static String fixTextForRendering(TextureFont font, String text, Rectangle bounds) {
		String newText = "";

		String dotString = "...";
		float dots = font.meassureString(dotString).X;

		for(int i = text.length() - 1; i >= 0; i--) {
			String substr = text.substring(0, i);
			if(font.meassureString(substr).X + dots < bounds.Width) {
				newText = substr + dotString;
				return newText;
			}
		}

		return newText;
	}
}
