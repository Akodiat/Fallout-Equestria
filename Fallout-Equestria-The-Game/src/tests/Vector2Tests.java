package tests;

import static org.junit.Assert.*;

import math.Vector2;

import org.junit.Test;

/**
 * Test for the class Vector2.
 * @author Pontus
 *
 */
public class Vector2Tests {

	private static float x1 = (float)Math.random() * 10;
	private static float x2 = (float)Math.random() * 10;
	private static float y1 = (float)Math.random() * 10;
	private static float y2 = (float)Math.random() * 10;
	
	private static Vector2 vector1 = new Vector2(x1, y1);
	private static Vector2 vector2 = new Vector2(x2, y2);
	
	@Test
	public void testAddition() {
		Vector2 result = Vector2.add(vector1, vector2);
		
		assertTrue(result.X == x1 + x2);
		assertTrue(result.Y == y1 + y2);
	}
	
	@Test
	public void testSubtraction() {
		Vector2 result = Vector2.subtract(vector1, vector2);
		
		assertTrue(result.X == x1 - x2);
		assertTrue(result.Y == y1 - y2);
	}
	
	@Test
	public void testScalarMultiplication() {
		float scalar = (float)Math.random() * 10;
		
		Vector2 result = Vector2.mul(scalar, vector1);
		
		assertTrue(result.X == scalar * vector1.X);
		assertTrue(result.Y == scalar * vector1.Y);
	}
	
	@Test
	public void testDotMultiplication() {
		float result = Vector2.dot(vector1, vector2);
		
		assertTrue(result == vector1.X * vector2.X + vector1.Y * vector2.Y);
	}
	
	@Test
	public void testDistance() {
		float result = Vector2.distance(vector1, vector2);
		
		float a = vector1.X - vector2.X;
		float b = vector1.Y - vector2.Y;
		
		assertTrue(result == (float)Math.sqrt((a*a)+(b*b)));
	}
	
	@Test
	public void testDistanceSquared() {
		float result = Vector2.distanceSquared(vector1, vector2);
		
		float a = vector1.X - vector2.X;
		float b = vector1.Y - vector2.Y;
		
		assertTrue(result == (a*a)+(b*b));
	}
	
	@Test
	public void testLength() {
		float result = vector1.length();
		assertTrue(result == (float)Math.sqrt((vector1.X*vector1.X) + (vector1.Y*vector1.Y)));
	}
	
	@Test
	public void testOrthogonalProjection() {
		Vector2 toProject = new Vector2(x1,y1);
		Vector2 toBeProjectedOn = new Vector2(x2,y2);
		
		float numerator = Vector2.dot(toBeProjectedOn, toProject);
		float denominator = Vector2.dot(toBeProjectedOn, toBeProjectedOn);
		
		Vector2 result = Vector2.orthogonalProjection(toProject, toBeProjectedOn);
		
		assertTrue(result.X == Vector2.mul(numerator / denominator, toBeProjectedOn).X);
		assertTrue(result.Y == Vector2.mul(numerator / denominator, toBeProjectedOn).Y);
	}
	
	@Test
	public void testAngle() {
		float numerator = Vector2.dot(vector1, vector2);
		float denominator = vector1.length() * vector2.length();
		
		float result = Vector2.angle(vector1, vector2);
		
		assertTrue(result == (float)Math.acos(numerator / denominator));
	}
	
	@Test
	public void testNorm() {
		Vector2 result = Vector2.norm(vector1);
		
		assertTrue(result.X == Vector2.mul(1/vector1.length(), vector1).X);
		assertTrue(result.Y == Vector2.mul(1/vector1.length(), vector1).Y);
		assertTrue(result.length() == 1.0);
	}
}
