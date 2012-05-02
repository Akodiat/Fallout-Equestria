package GUI;

import utils.Rectangle;
import graphics.Texture2D;

public class VisibleElement {
	private final Texture2D texture;
	private final Rectangle srcRect;
	
	public VisibleElement(Texture2D texture, Rectangle sorceRect) {
		this.texture = texture;
		this.srcRect = sorceRect;
	}

	public Texture2D getTexture() {
		return texture;
	}

	public Rectangle getSrcRect() {
		return srcRect;
	}
}
