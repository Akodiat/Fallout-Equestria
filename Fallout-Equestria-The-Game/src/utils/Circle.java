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

	public static Boolean intersects(Circle circle1, Circle circle2) {
		float comboRadius = circle1.radius + circle2.getRadius();
		return comboRadius * comboRadius > distanceSquared(circle1, circle2);
	}

	public static float distanceSquared(Circle circle1, Circle circle2) {
		float dx = circle1.position.X - circle2.position.X;
		float dy = circle1.position.Y - circle2.position.Y;
		return (dx * dx + dy * dy);
	}

	public static Boolean intersects(Circle circle1, Vector2 offset1,
			Circle circle2, Vector2 offset2) {
		float comboRadius = circle1.radius + circle2.getRadius();
		return comboRadius * comboRadius > distanceSquared(circle1,offset1, circle2,offset2);
	}

	public static float distanceSquared(Circle circle1, Vector2 offset1,
			Circle circle2, Vector2 offset2) {
		float dx = circle1.position.X + offset1.X
				- (circle2.position.X + offset2.X);
		float dy = circle1.position.Y + offset1.Y
				- (circle2.position.Y + offset2.Y);
		return (dx * dx + dy * dy);
	}
}
