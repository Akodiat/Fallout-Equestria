package utils;

public final class Rectangle {

	public final int X;
	public final int Y;
	public final int Width;
	public final int Height;
	
	
	public Rectangle(int x, int y, int width, int height) {
		this.X = x;
		this.Y = y;
		this.Width = width;
		this.Height = height;
	}
	
	public int getLeft() {
		return this.X;
	}
	
	public int getRight() {
		return this.X + this.Width;
	}
	
	public int getTop() {
		return this.Y;
	}
	
	public int getBottom() {
		return this.Y + this.Height;
	}
}
