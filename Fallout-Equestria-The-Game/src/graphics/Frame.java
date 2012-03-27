package graphics;

import math.Vector2;
import utils.Rectangle;

public class Frame {

	private Rectangle sourceRect;
	private Vector2 origin;
	private Boolean mirrored;
	
	public Frame(Rectangle sourceRect, Vector2 origin, Boolean mirrored) {
		this.sourceRect = sourceRect;
		this.origin = origin;
		this.mirrored = mirrored;
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
	public Boolean getMirrored() {
		return mirrored;
	}
	public void setMirrored(Boolean mirrored) {
		this.mirrored = mirrored;
	}
}
