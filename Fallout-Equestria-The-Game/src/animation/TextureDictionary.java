package animation;

import java.util.Dictionary;

import graphics.Texture2D;

public class TextureDictionary {
	private String version;
	private Texture2D texture;
	private Dictionary<String, TextureBounds> textureCollection;

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Texture2D getTexture() {
		return texture;
	}
	public void setTexture(Texture2D texture) {
		this.texture = texture;
	}

	public TextureBounds getTextureBounds(String id) {
		return textureCollection.get(id);
	}
	
	public Dictionary<String, TextureBounds> getTextureCollection() {
		return textureCollection;
	}

	public void setTextureCollection(
			Dictionary<String, TextureBounds> textureCollection) {
		this.textureCollection = textureCollection;
	}
}
