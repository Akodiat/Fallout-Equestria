package utils;

import math.Vector2;

public final class Rectangle {

	private final int x, y, width, height;

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int getLeft() {
		return this.x;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getRight() {
		return this.x + this.width;
	}
	
	public int getTop() {
		return this.y;
	}
	
	public int getBottom() {
		return this.y + this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
}
