package animation;

import graphics.Texture2D;

public class TextureEntry {
	private boolean useDictionary;
	private Texture2D texture;
	private TextureBounds textureBounds;

	public boolean isUseDictionary() {
		return useDictionary;
	}

	public void setUseDictionary(boolean useDictionary) {
		this.useDictionary = useDictionary;
	}

	public Texture2D getTexture() {
		return texture;
	}

	public void setTexture(Texture2D texture) {
		this.texture = texture;
	}

	public TextureBounds getTextureBounds() {
		return textureBounds;
	}

	public void setTextureBounds(TextureBounds textureBounds) {
		this.textureBounds = textureBounds;
	}
}
