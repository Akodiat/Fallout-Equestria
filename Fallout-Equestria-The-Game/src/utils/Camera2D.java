package utils;

import math.Matrix4;
import math.Vector2;

/**
 * 
 * @author Lukas Kurtyan
 *
 */
public class Camera2D {

	//Properties of the Camera
	private Vector2 position;
	private Vector2 zoom;
	
	//Used to center the screen.
	private Vector2 screenOffset;
	
	//The boundy of the world. The camera cannot move outside of this.
	public Rectangle worldBounds;
	
	
	/**Creates a new Camera2D instance.
	 * 
	 * @param worldBounds the bounds of the world.
	 * @param screen
	 */
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
		Matrix4 translation = Matrix4.createTranslation(new Vector2(-position.X - screenOffset.X, -position.Y - screenOffset.Y));
		Matrix4 combinedMatrix = Matrix4.mul(screenOff, scale);
		combinedMatrix = Matrix4.mul(combinedMatrix, translation);
		
		return combinedMatrix;
	}
	
	
	public void setPosition(Vector2 position) {
		this.position = Vector2.mul(-1f, position);
		this.validatePosition();
	}
	
	private void validatePosition() {
		//TODO confirm this is correct. 
		float x = this.position.X, y = this.position.Y;
		float left = this.worldBounds.getLeft() * this.zoom.X;
		float right = this.worldBounds.getRight() * this.zoom.X + this.screenOffset.X  * 2 *  this.zoom.X;
		
		float top = this.worldBounds.getTop();
		float bottom = this.worldBounds.getBottom() + this.screenOffset.Y * 2 * this.zoom.Y;
		
		if(x < left) {
			x = left;
		} else if(x > right) {
			x =  right;
		}
		
		if(y < top) {
			y = top ;
		} else if(y > bottom) {
			y = bottom ;
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
	
	public Vector2 getZoom() {
		return this.zoom;
	}
	
	public Rectangle getWorldBounds() {
		return this.worldBounds;
	}
	
	public Vector2 getWorldPosition() {
		return this.position;
	}
	
	public Rectangle getVisibleArea() {
		int x,y,width,height;
		
		x = (int)((this.position.X) * this.zoom.X);
		y = (int)((this.position.Y) * this.zoom.Y);
		width = (int)(this.screenOffset.X * -2.0f * this.zoom.X);
		height = (int)(this.screenOffset.Y * -2.0f * this.zoom.Y);
		
		return new Rectangle(x,y,width,height);	
	}
	
	
	public Vector2 getViewToWorldCoords(Vector2 viewPosition) {
		//TODO fix so that scale works!
		return Vector2.add(this.position, viewPosition);
	}
	
	public Vector2 getScreenOffset() {
		return this.screenOffset;
	}
	
	public String toString() {
		return "Camera :" 
			+  "Screen Offset:   " + this.screenOffset.toString() + "\n"
			+  "Center Position: " + this.position.toString() + "\n"
			+  "Zoom:            " + this.zoom.toString() + "\n"
			+  "World Bounds:    " + this.worldBounds.toString() + "\n";
	}
	
}
