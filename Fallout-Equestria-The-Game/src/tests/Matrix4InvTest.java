package tests;

import static org.junit.Assert.assertTrue;
import math.Matrix4;

import org.junit.Test;

public class Matrix4InvTest {

	private Matrix4 randomMatrix = new Matrix4((float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),
											   (float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),
											   (float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),
											   (float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random());
	
	
	@Test
	public void testDeterminant() {

		
	}

	@Test
	public void testInversion() {
		while(randomMatrix.det()==0){
		randomMatrix = new Matrix4((float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),
				   (float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),
				   (float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random(),
				   (float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random());
		}
		Matrix4 invertedMatrix = randomMatrix.inverse();
		Matrix4 identityMatrix = Matrix4.mul(invertedMatrix, randomMatrix);
		

		System.out.println(randomMatrix.toString());
		System.out.println(invertedMatrix.toString());
		System.out.println(identityMatrix.toString());
		assertTrue(Matrix4.roughlyEquals(identityMatrix, Matrix4.Identity, 0.000001f));
	}
}
