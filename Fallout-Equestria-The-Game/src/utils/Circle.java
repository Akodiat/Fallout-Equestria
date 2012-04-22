package utils;

import math.MathHelper;
import math.Vector2;

public class Circle {

	public static final Circle Empty = new Circle(Vector2.Zero, 0.0f);
	private Vector2 position;
	private float radius;

	public Circle(Vector2 position, float radius) {
		this.position = position;
		this.radius = radius;
	}
	public Circle(float radius) {
		this(Vector2.Zero, radius);
	}
	public Circle(float x, float y, float radius) {
		this(new Vector2(x, y), radius);
	}
	/**
	 * Defaultconstructor
	 */
	public Circle() {
		this.position = new Vector2(0,0);
		this.radius = 1;
	}
	/**
	 * Copyconstructor
	 */
	public Circle(Circle other) {
		this(other.position, other.radius);
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

	public static boolean intersects(Circle circle1, Circle circle2) {
		float comboRadius = circle1.radius + circle2.getRadius();
		return comboRadius * comboRadius > distanceSquared(circle1, circle2);
	}

	public static float distanceSquared(Circle circle1, Circle circle2) {
		float dx = circle1.position.X - circle2.position.X;
		float dy = circle1.position.Y - circle2.position.Y;
		return (dx * dx + dy * dy);
	}

	public static boolean intersects(Circle circle1, Vector2 offset1,
			Circle circle2, Vector2 offset2) {
		float comboRadius = circle1.radius + circle2.getRadius();
		return comboRadius * comboRadius > distanceSquared(circle1, offset1, circle2, offset2);
	}

	public static float distanceSquared(Circle circle1, Vector2 offset1,
			Circle circle2, Vector2 offset2) {
		float dx = circle1.position.X + offset1.X
				- (circle2.position.X + offset2.X);
		float dy = circle1.position.Y + offset1.Y
				- (circle2.position.Y + offset2.Y);
		return (dx * dx + dy * dy);
	}
	
	/**
	 * Calculates the distance between the bounds of two parameter circles.
	 * A negative distance will be returned if they intersect.
	 * @return
	 */
	public static float distance(Circle circle1, Vector2 offset1,
			Circle circle2, Vector2 offset2){
		float distance = distanceSquared(circle1, offset1,
				circle2, offset2);
		distance -= circle1.radius + circle2.radius;
		return distance;
		
	}
	
	public static Circle lerp(Circle circle1, Circle circle2, float t){
		float x = MathHelper.lerp(circle1.getPosition().X, circle2.getPosition().X, t);
		float y = MathHelper.lerp(circle1.getPosition().Y, circle2.getPosition().Y, t);
		float r = MathHelper.lerp(circle1.getRadius(), circle2.getRadius(), t);
		
		return new Circle(x, y, r);
	}
	
	public String toString() {
		return "Center Postion: " + this.position.toString() + "\n"
			+  "Radius: " + this.radius;
	}
}
