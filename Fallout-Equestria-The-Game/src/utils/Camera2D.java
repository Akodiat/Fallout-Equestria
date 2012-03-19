package utils;

import math.Matrix4;
import math.Vector2;

/**
 * 
 * @author Lukas Kurtyan
 *
 */
public class Camera2D {

	private Vector2 position;
	private Vector2 screenOffset;
	private Vector2 zoom;
	public Rectangle worldBounds;
	
	
	public Camera2D(Rectangle worldBounds, Rectangle screen) {
		this(worldBounds, screen, Vector2.Zero);
	}
	
	public Camera2D(Rectangle worldBounds, Rectangle screen, Vector2 position) {
		this(worldBounds, screen, position, Vector2.One);
	}
	
	public Camera2D(Rectangle worldBounds, Rectangle screen, Vector2 position, Vector2 zoom) {
		this.screenOffset = new Vector2(-screen.Width / 2, -screen.Height / 2);
		this.zoom = zoom;
		this.position = position;
		this.worldBounds = worldBounds;
		
	}

	public Matrix4 getTransformation() {
		Matrix4 screenOff = Matrix4.createTranslation(screenOffset);
		Matrix4 scale = Matrix4.createScale(this.zoom);
		Matrix4 translation = Matrix4.createTranslation(new Vector2(-position.X, -position.Y));
		Matrix4 combinedMatrix = Matrix4.mul(screenOff, scale);
		combinedMatrix = Matrix4.mul(combinedMatrix, translation);
		
		return combinedMatrix;
	}
	
	
	public void setPosition(Vector2 position) {
		this.position = Vector2.mul(-1f, position);
		this.validatePosition();
	}
	
	private void validatePosition() {
		
		
		float x = this.position.X, y = this.position.Y;
		float left = this.worldBounds.getLeft() + this.screenOffset.X * this.zoom.X;
		float right = this.worldBounds.getRight() + this.screenOffset.X  * 3 *  this.zoom.X;
		
		float top = this.worldBounds.getTop() + this.screenOffset.Y * this.zoom.Y;
		float bottom = this.worldBounds.getBottom() + this.screenOffset.Y * 3 * this.zoom.Y;
		System.out.println("Top: " + top + " Bottom " + bottom);
		
		if(x * zoom.X < left) {
			x = left / zoom.Y;
		} else if(x * zoom.X> right) {
			x =  right / zoom.Y;
		}
		
		if(y * zoom.Y < top) {
			y = top / zoom.Y;
		} else if(y * zoom.Y> bottom) {
			y = bottom / zoom.Y;
		}
		this.position = new Vector2(x,y);


	}

	public void setScreenBounds(Rectangle screen) {
		this.screenOffset = new Vector2(-screen.Width / 2, -screen.Height / 2);
	}
	
	public void setZoom(Vector2 zoom) {
		this.zoom = zoom;
	}
	
	public void move(Vector2 displaysment) {
		this.position = Vector2.add(this.position, new Vector2(-displaysment.X, displaysment.Y));
		this.validatePosition();
	}
	
	public void zoomIn(float zoom) {
		this.zoom = Vector2.add(this.zoom, new Vector2(zoom, zoom));
	}
	
	public void zoomOut(float zoom) {
		this.zoom = Vector2.subtract(this.zoom, new Vector2(zoom, zoom));
	}
	
	
}
