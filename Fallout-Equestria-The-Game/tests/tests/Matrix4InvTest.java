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
		Matrix4 testMatrix1 = new Matrix4(44f, 45f, 91f, 168f,
										 49f, 798f, 45f, 45f,
										 12f, -15f, 54f, 4f,
										 -87f, -55f, -74f, -51f);
		Matrix4 testMatrix2 = new Matrix4(21, 75, 9, 12,
				 							0, 7, 8, 456,
				 							0, 0, 12, 1,
				 							0, 0, 0, 52);
		
		System.out.println(testMatrix1.det());
		assertTrue(testMatrix1.det() > 424562000);
		assertTrue(testMatrix1.det() < 424563000);
		
		System.out.println(testMatrix2.det());
		assertTrue(testMatrix2.det() > 91700.000);
		assertTrue(testMatrix2.det() < 91800.000);
		
		assertTrue(Matrix4.Identity.det() == 1);
		
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
		assertTrue(Matrix4.roughlyEquals(identityMatrix, Matrix4.Identity, 0.0001f));
	}
}
