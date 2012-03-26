package graphics;

import math.Vector2;
import utils.Rectangle;

public class Frame {

	private String spriteSheetName;
	private Rectangle destRect;
	private Vector2 origin;
	
	public Frame(String spriteSheetName, Rectangle destRect, Vector2 origin) {
		this.spriteSheetName = spriteSheetName;
		this.destRect = destRect;
		this.origin = origin;
	}
	public String getSpriteSheetName() {
		return spriteSheetName;
	}
	public void setSpriteSheetName(String spriteSheetName) {
		this.spriteSheetName = spriteSheetName;
	}
	public Rectangle getDestRect() {
		return destRect;
	}
	public void setDestRect(Rectangle destRect) {
		this.destRect = destRect;
	}
	public Vector2 getOrigin() {
		return origin;
	}
	public void setOrigin(Vector2 origin) {
		this.origin = origin;
	}
	
}
