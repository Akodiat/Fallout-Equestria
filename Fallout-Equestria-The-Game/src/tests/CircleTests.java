package tests;

import math.Vector2;

import utils.Circle;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the methods in the circle class.
 * @author Gustav
 *
 */
public class CircleTests {

	private static float x1 = (float)Math.random() * 10;
	private static float x2 = (float)Math.random() * 10;
	private static float y1 = (float)Math.random() * 10;
	private static float y2 = (float)Math.random() * 10;
	
	private static float r1 = (float)Math.random() * 10;
	private static float r2 = (float)Math.random() * 10;
	
	private static Vector2 vector1 = new Vector2(x1, y1);
	private static Vector2 vector2 = new Vector2(x2, y2);
	
	private static Circle circle1 = new Circle(vector1,r1);
	private static Circle circle2 = new Circle(vector2,r2);
	
	@Test
	public void testDistanceSquared(){
		float d1 = circle1.distanceSquared(circle2);
		float dx = x1 - x2;
		float dy = y1 - y2;
		
		assertTrue(d1 == (dx*dx + dy*dy));
	}
	
	@Test
	public void testIntersects(){
		float dx = x1 - x2;
		float dy = y1 - y2;
		
		float distance = (float) Math.sqrt(dx*dx + dy*dy);
		float combinedRadius = circle1.getRadius() + circle2.getRadius();
		
		assertTrue(distance < combinedRadius == circle1.intersects(circle2));
	}
}
