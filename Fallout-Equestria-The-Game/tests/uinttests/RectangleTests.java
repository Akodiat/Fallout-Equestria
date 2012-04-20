package uinttests;

import static org.junit.Assert.assertTrue;
import math.MathHelper;

import org.junit.Test;

import utils.Rectangle;
/**
 * Tests for rectangleclass
 * @author Gustav
 *
 */
public class RectangleTests {

	private Rectangle randomR1 = new Rectangle((int)Math.random()*100,(int)Math.random()*100,(int)Math.random()*100,(int)Math.random()*100);
	private Rectangle randomR2 = new Rectangle((int)Math.random()*100,(int)Math.random()*100,(int)Math.random()*100,(int)Math.random()*100);

	@Test
	public void testGetLeft(){
		assertTrue(randomR1.X == randomR1.getLeft() 
				&& randomR2.X == randomR2.getLeft());
	}

	@Test
	public void testGetRight(){
		assertTrue(randomR1.X + randomR1.Width == randomR1.getRight() 
				&& randomR2.X + randomR2.Width == randomR2.getRight());
	}

	@Test
	public void testGetTop(){
		assertTrue(randomR1.Y == randomR1.getTop() 
				&& randomR2.Y == randomR2.getTop());
	}

	@Test
	public void testGetBottom(){
		assertTrue(randomR1.Y + randomR1.Height == randomR1.getBottom() 
				&& randomR2.Y + randomR2.Height == randomR2.getBottom());
	}


	@Test
	public void testLerp() {
		float random = (float)(Math.random()*2)-0.5f;//A value between -0.5 and 1.5
		Rectangle lerped  = Rectangle.lerp(randomR1, randomR2, random);

		assertTrue(lerped.X == (int)MathHelper.lerp(randomR1.X, randomR2.X, random));
		assertTrue(lerped.Y == (int)MathHelper.lerp(randomR1.Y, randomR2.Y, random));
		assertTrue(lerped.Width == (int)MathHelper.lerp(randomR1.Width, randomR2.Width, random));
		assertTrue(lerped.Height == (int)MathHelper.lerp(randomR1.Height, randomR2.Height, random));
	}
}
