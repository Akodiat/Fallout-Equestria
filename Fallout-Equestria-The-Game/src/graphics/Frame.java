package graphics;

import content.ContentManager;
import math.Vector2;
import utils.Rectangle;

public class Frame {

	private Rectangle sourceRect;
	private Vector2 origin;
	
	public Frame(Rectangle sourceRect, Vector2 origin) {
		this.sourceRect = sourceRect;
		this.origin = origin;
	}
		
	public Rectangle getSourceRect() {
		return sourceRect;
	}
	public void setSourceRect(Rectangle sourceRect) {
		this.sourceRect = sourceRect;
	}
	public Vector2 getOrigin() {
		return origin;
	}
	public void setOrigin(Vector2 origin) {
		this.origin = origin;
	}	
}
