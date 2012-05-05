package gameMap;

import utils.Rectangle;
import math.Vector2;
import graphics.Texture2D;

public class TexturedSceneNode extends SceneNode{
	private final Texture2D texture;
	private final Rectangle srcRectangle;
		
	public TexturedSceneNode(String nodeID, Vector2 position, Texture2D texture, Rectangle srcRectangle) {
		super(nodeID, position);
		this.texture = texture;
		this.srcRectangle = srcRectangle;
	}

	public Texture2D getTexture() {
		return texture;
	}

	public Rectangle getSrcRectangle() {
		return srcRectangle;
	}

}
