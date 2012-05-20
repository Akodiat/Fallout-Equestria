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
		this.screenOffset = new Vector2(-screen.Width / 2, -screen.Height / 2);
		this.position = position;
		this.worldBounds = worldBounds;
		
	}

	public Matrix4 getTransformation() {
		Matrix4 translation = Matrix4.createTranslation(new Vector2(-position.X, -position.Y));
		return translation;
	}
	
	
	public void setPosition(Vector2 position) {
		this.position = Vector2.mul(-1f, position);
		this.validatePosition();
	}
	
	private void validatePosition() {
		//TODO confirm this is correct. 
		float x = this.position.X, y = this.position.Y;
		float left = this.worldBounds.getLeft();
		float right = this.worldBounds.getRight() + this.screenOffset.X  * 2;
		
		float top = this.worldBounds.getTop();
		float bottom = this.worldBounds.getBottom() + this.screenOffset.Y * 2;
		
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
	
	public void move(Vector2 displaysment) {
		this.position = Vector2.add(this.position, new Vector2(-displaysment.X, displaysment.Y));
		this.validatePosition();
	}
	
	public Rectangle getWorldBounds() {
		return this.worldBounds;
	}
	
	public Vector2 getWorldPosition() {
		return this.position;
	}
	

	public Rectangle getViewport() {

		int x,y,width,height;
		
		x = 0;
		y = 0;
		width = (int)(this.screenOffset.X * -2.0f);
		height = (int)(this.screenOffset.Y * -2.0f);

		return new Rectangle(x,y,width,height);	
	}
	
	
	public Rectangle getVisibleArea() {
		int x,y,width,height;
		x = (int)((this.position.X));
		y = (int)((this.position.Y));
		width = (int)(this.screenOffset.X * -2.0f);
		height = (int)(this.screenOffset.Y * -2.0f);
		
		return new Rectangle(x,y,width,height);	
	}
	
	
	public Vector2 getViewToWorldCoords(Vector2 viewPosition) {
		
		float x = this.position.X + viewPosition.X;
		float y = this.position.Y + viewPosition.Y;
		
		return new Vector2(x,y);
	}
	
	public Vector2 getScreenOffset() {
		return this.screenOffset;
	}
	
	public String toString() {
		return "Camera :" 
			+  "Screen Offset:   " + this.screenOffset.toString() + "\n"
			+  "Center Position: " + this.position.toString() + "\n"
			+  "World Bounds:    " + this.worldBounds.toString() + "\n";
	}
	
}
