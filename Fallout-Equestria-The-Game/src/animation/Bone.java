package animation;

import graphics.Color;
import math.Vector2;

public class Bone {
	
	private String name;
	private boolean hidden;
	private int parentIndex;
	private int textureIndex;
	private int selfIndex;
	private int updateIndex;
	private Vector2 position;
	private float rotation;
	private Vector2 scale;
	private boolean mirrored;
	private Color color;
	
	public Bone(String name, boolean hidden, int parentIndex, int textureIndex,
			int selfIndex, int updateIndex, Vector2 position, float rotation,
			Vector2 scale, boolean mirrored) {
		this.name = name;
		this.hidden = hidden;
		this.parentIndex = parentIndex;
		this.textureIndex = textureIndex;
		this.selfIndex = selfIndex;
		this.updateIndex = updateIndex;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.mirrored = mirrored;
		this.color = Color.White;
	}
	
	public Bone() {
		this.color = Color.White;
	}
	
	private Bone(Bone other){
		this.name = other.name;
		this.hidden = other.hidden;
		this.parentIndex = other.parentIndex;
		this.textureIndex = other.textureIndex;
		this.selfIndex = other.selfIndex;
		this.updateIndex = other.updateIndex;
		this.position = other.position;
		this.rotation = other.rotation;
		this.scale = other.scale;
		this.mirrored = other.mirrored;
		this.color = other.color;
	}
	
	public Bone clone(){
		return new Bone(this);
	}
	
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
	public boolean isMirrored() {
		return mirrored;
	}
	public void setMirrored(boolean textureFlipHorizontal) {
		this.mirrored = textureFlipHorizontal;
	}
	public Color getColor() {
		return this.color;
	}
	public void setColor(Color param) {
		this.color = param;
	}
}
