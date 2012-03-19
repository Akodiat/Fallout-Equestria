package utils;

import math.MathHelper;
import math.Matrix3;
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
	private float rotation;
	
	
	public Camera2D(Rectangle screen) {
		this(screen, Vector2.Zero);
	}
	
	public Camera2D(Rectangle screen, Vector2 position) {
		this(screen, position, Vector2.One);
	}
	
	public Camera2D(Rectangle screen, Vector2 position, Vector2 scale) {
		this(screen, position, scale, 0.0f);
	}
	
	public Camera2D(Rectangle screen, Vector2 position, Vector2 zoom, float rotation) {
		this.screenOffset = new Vector2(-screen.Width / 2, -screen.Height / 2);
		this.zoom = zoom;
		this.position = position;
		this.rotation = rotation;	
	}
	
	public Matrix4 getTransformation() {
		Matrix4 screenOff = Matrix4.createTranslation(screenOffset);
		Matrix4 scale = Matrix4.createScale(this.zoom);
		Matrix4 rotation = Matrix4.createRotationZ(this.rotation);
		Matrix4 translation = Matrix4.createTranslation(position);
		Matrix4 combinedMatrix = Matrix4.mul(screenOff, scale);
		combinedMatrix = Matrix4.mul(combinedMatrix, rotation);
		combinedMatrix = Matrix4.mul(combinedMatrix, translation);
		
		return combinedMatrix;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public void setScreenBounds(Rectangle screen) {
		this.screenOffset = new Vector2(-screen.Width / 2, -screen.Height / 2);
	}
	
	public void setZoom(Vector2 zoom) {
		this.zoom = zoom;
	}
	
	public void move(Vector2 displaysment) {
		this.position = Vector2.add(this.position, new Vector2(displaysment.X, -displaysment.Y));
	}
	
	public void zoomIn(float zoom) {
		this.zoom = Vector2.add(this.zoom, new Vector2(zoom, zoom));
	}
	
	public void zoomOut(float zoom) {
		this.zoom = Vector2.subtract(this.zoom, new Vector2(zoom, zoom));
	}
	
	public void addRotation(float angle) {
		this.rotation += angle;
	}
	
	
	
}
