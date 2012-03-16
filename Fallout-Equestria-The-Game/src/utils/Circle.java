package utils;

import math.Vector2;

public class Circle {

	private Vector2 position;
	private float radius;

	public Circle(Vector2 position, float radius) {
		this.position = position;
		this.radius = radius;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}

	public Boolean intersects(Circle other){
		float comboRadius = this.radius + other.getRadius();
		return comboRadius*comboRadius>distanceSquared(other); //Don't need no sqrt. This is perfectly sufficient.
	}
	
	
	public float distanceSquared(Circle other){
		float dx = this.position.X - other.position.X;
		float dy = this.position.Y - other.position.Y;
		return (dx*dx + dy*dy);
	}
}
