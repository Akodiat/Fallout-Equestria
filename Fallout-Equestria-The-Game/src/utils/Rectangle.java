package utils;

import math.MathHelper;
import math.Vector2;

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
	
	public Vector2 getCenter() {
		return new Vector2(this.X + this.Width  / 2.0f, 
						   this.Y + this.Height / 2.0f);
	}
	
	public boolean intersects(Rectangle other) {
		return other.X < this.X + this.Width && 
			   this.X < other.X + other.Width &&
			   other.Y < this.Y + this.Height && 
			   this.Y < other.Y + other.Height;
		
	}
	
	
	public static Rectangle lerp(Rectangle rectangle1, Rectangle rectangle2, float t){
		int x = (int)MathHelper.lerp(rectangle1.X, rectangle2.X, t);
		int y = (int)MathHelper.lerp(rectangle1.Y, rectangle2.Y, t);
		int width = (int)MathHelper.lerp(rectangle1.Width, rectangle2.Width, t);
		int height = (int)MathHelper.lerp(rectangle1.Height, rectangle2.Height, t);
		
		return new Rectangle(x, y, width, height);
	}
	
	public String toString() {
		return "| X = " + this.X + " Y = " + this.Y + " Width = " + this.Width + " Height = " + this.Height + " |";
	}
}
