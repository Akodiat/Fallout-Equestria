package animation;

import math.Vector2;
import utils.Rectangle;

public class TextureBounds {
	private Rectangle location;
	private Vector2 origin;

	public TextureBounds(){
		this(Rectangle.Empty, Vector2.Zero);
	}

	public TextureBounds(Rectangle location, Vector2 origin)
	{
		this.location = location;
		this.origin = origin;
	}

	public Rectangle getLocation() {
		return location;
	}

	public void setLocation(Rectangle location) {
		this.location = location;
	}

	public Vector2 getOrigin() {
		return origin;
	}

	public void setOrigin(Vector2 origin) {
		this.origin = origin;
	}
}
