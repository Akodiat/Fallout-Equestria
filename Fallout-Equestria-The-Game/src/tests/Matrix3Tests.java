package tests;

import static org.junit.Assert.*;

import math.Matrix3;

import org.junit.Test;

public class Matrix3Tests {

	@Test
	public void testTranspose() {
		Matrix3 m = new Matrix3(1,2,3,
								4,5,6,
								7,8,9);
		
		Matrix3 result = m.transpose();
		
		
	}
	
}
