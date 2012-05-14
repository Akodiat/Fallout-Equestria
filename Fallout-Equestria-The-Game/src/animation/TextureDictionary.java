package animation;


import java.util.Map;

import graphics.Texture2D;

public class TextureDictionary {
	private String version;
	private Texture2D texture;
	private Map<String, TextureBounds> textureCollection;

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
		TextureBounds bounds = this.textureCollection.get(id);
		if(bounds == null) {
			throw new RuntimeException("The id " + id  + "was not present in the dictionary");
		}
		
		return textureCollection.get(id);
	}
	
	public Map<String, TextureBounds> getTextureCollection() {
		return textureCollection;
	}

	public void setTextureCollection(
			Map<String, TextureBounds> textureCollection) {
		this.textureCollection = textureCollection;
	}
	
	public TextureEntry extractTextureEntry(String name){
		return new TextureEntry(this.texture, this.textureCollection.get(name));
	}
}
