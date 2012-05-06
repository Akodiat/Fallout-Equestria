package uinttests;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import graphics.Color;
import math.MathHelper;

/**
 * Tests for colorclass
 * @author Gustav
 *
 */
public class ColorTests {
	
	private Color randomC1 = new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), (float)Math.random());
	private Color randomC2 = new Color((float)Math.random(),(float)Math.random(), (float)Math.random(), (float)Math.random());

	@Test
	public void testLerp() {
		float random = (float)(Math.random()*2)-0.5f;//A value between -0.5 and 1.5
		Color lerped  = Color.lerp(randomC1, randomC2, random);

		assertTrue(lerped.R == MathHelper.lerp(randomC1.R, randomC2.R, random));
		assertTrue(lerped.G == MathHelper.lerp(randomC1.G, randomC2.G, random));
		assertTrue(lerped.B == MathHelper.lerp(randomC1.B, randomC2.B, random));
		assertTrue(lerped.A == MathHelper.lerp(randomC1.A, randomC2.A, random));
	}
	
}
