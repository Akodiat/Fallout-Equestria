package gameMap;

import graphics.Texture2D;

public class Tile {
	private Texture2D texture;

	public Tile(Texture2D texture){
		this.texture=texture;
	}
	/**
	 * @return the texture
	 */
	public Texture2D getTexture() {
		return texture;
	}

	/**
	 * @param texture the texture to set
	 */
	public void setTexture(Texture2D texture) {
		this.texture = texture;
	}

}
