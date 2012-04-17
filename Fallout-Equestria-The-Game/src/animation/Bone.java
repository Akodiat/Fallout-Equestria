package animation;

import math.Vector2;

public class Bone {
	
	private String name;
	private boolean hidden;
	private int parentIndex;
	private int textureIndex;
	//TODO Java, how do?
	//Microsoft.Xna.Framework.Content.ContentSerializerIgnore
	private int selfIndex;
	//[Microsoft.Xna.Framework.Content.ContentSerializerIgnore]
	private int updateIndex;
	private Vector2 position;
	private float rotation;
	private Vector2 scale;
	private boolean textureFlipHorizontal;
	private boolean textureFlipVertical;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public int getParentIndex() {
		return parentIndex;
	}
	public void setParentIndex(int parentIndex) {
		this.parentIndex = parentIndex;
	}
	public int getTextureIndex() {
		return textureIndex;
	}
	public void setTextureIndex(int textureIndex) {
		this.textureIndex = textureIndex;
	}
	public int getSelfIndex() {
		return selfIndex;
	}
	public void setSelfIndex(int selfIndex) {
		this.selfIndex = selfIndex;
	}
	public int getUpdateIndex() {
		return updateIndex;
	}
	public void setUpdateIndex(int updateIndex) {
		this.updateIndex = updateIndex;
	}
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public Vector2 getScale() {
		return scale;
	}
	public void setScale(Vector2 scale) {
		this.scale = scale;
	}
	public boolean isTextureFlipHorizontal() {
		return textureFlipHorizontal;
	}
	public void setTextureFlipHorizontal(boolean textureFlipHorizontal) {
		this.textureFlipHorizontal = textureFlipHorizontal;
	}
	public boolean isTextureFlipVertical() {
		return textureFlipVertical;
	}
	public void setTextureFlipVertical(boolean textureFlipVertical) {
		this.textureFlipVertical = textureFlipVertical;
	}
}
