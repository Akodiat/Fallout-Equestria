package content;

import math.Vector2;

import org.jdom.Element;

import utils.Rectangle;

public class XMLAnimHelper {
	
	static Rectangle extractRectangle(Element element) {
		int x,y,width,height;
		x = extractInt(element.getChild("X"));
		y = extractInt(element.getChild("Y"));
		width = extractInt(element.getChild("Width"));
		height = extractInt(element.getChild("Height"));
		
		return new Rectangle(x,y,width,height);
	}
	
	static Vector2 extractVector2(Element element) {
		float x,y;
		x = extractFloat(element.getChild("X"));
		y = extractFloat(element.getChild("Y"));
		
		return new Vector2(x,y);
	}

	static float extractFloat(Element element) {
		return Float.parseFloat(element.getValue());
	}
	
	static int extractInt(Element element) {
		return Integer.parseInt(element.getValue());
	}
	
	static boolean extractBool(Element element) {
		return Boolean.parseBoolean(element.getValue());
	}
	
}
