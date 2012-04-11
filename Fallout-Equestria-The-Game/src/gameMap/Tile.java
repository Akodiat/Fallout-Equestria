package gameMap;

import utils.Rectangle;
import graphics.Texture2D;

public class Tile {
	private final Texture2D texture;
	private final Rectangle sorceRect;
	private final float alpha;
	
	public Tile(Texture2D texture, Rectangle sorceRect) {
		this(texture, sorceRect, 0);
	}

	/**Helper constructor making it easy to create a new tile with a different alpha.
	 * 
	 * @param tile
	 * @param alpha
	 */
	public Tile(Tile tile, float alpha) {
		this(tile.texture, tile.sorceRect, alpha);
	}
	
	public Tile(Texture2D texture, Rectangle sorceRect, float alpha){
		this.texture=texture;
		this.sorceRect = sorceRect;
		this.alpha = alpha;
	}
	
	
	/**
	 * @return the texture
	 */
	public Texture2D getTexture() {
		return this.texture;
	}

	public Rectangle getSorceRect() {
		return this.sorceRect;
	}
	
	public float getAlpha() {
		return this.alpha;
	}

}
