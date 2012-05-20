package GUI;

import graphics.Texture2D;
import utils.Rectangle;

public class VisibleElement {
	private final Rectangle srcRect;
	private final Texture2D texture;
	
	public VisibleElement(Texture2D texture, Rectangle rectangle) {
		super();
		this.srcRect = rectangle;
		this.texture = texture;
	}
	public Rectangle getSrcRect() {
		return srcRect;
	}
	public Texture2D getTexture() {
		return texture;
	}
}
