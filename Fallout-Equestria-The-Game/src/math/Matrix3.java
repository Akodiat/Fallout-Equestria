package math;

import java.nio.FloatBuffer;

public final class Matrix3 {

	public final float m00; public final float m01; public final float m02;
	public final float m10; public final float m11; public final float m12;
	public final float m20; public final float m21; public final float m22;

	/**Creates a 3x3 Identity Matrix.
	 * 
	 */
	public Matrix3() {
		this(1, 0, 0,
				0, 1, 0,
				0, 0, 1); 
	}

	/**Creates a 3x3 custom matrix.
	 * 
	 * @param m00 first row first column.
	 * @param m01 first row second column.
	 * @param m02 first row third column.
	 * @param m10 second row first column.
	 * @param m11 second row second column.
	 * @param m12 second row third column.
	 * @param m20 third row first column.
	 * @param m21 third row second column.
	 * @param m22 third row third column.
	 */
	public Matrix3(float m00, float m01, float m02, 
			float m10, float m11, float m12,
			float m20, float m21, float m22) {

		this.m00 = m00; this.m01 = m01; this.m02 = m02;
		this.m10 = m10; this.m11 = m11; this.m12 = m12;
		this.m20 = m20; this.m21 = m21; this.m22 = m22;
	}

	/**Creates a 3x3 custom matrix.
	 * The data should be stored in a collumn major order
	 * or you will get the transpose. 
	 * @param data the matrix data.
	 */
	public Matrix3(float[] data) {
		if(data.length != 9) {
			throw new IllegalArgumentException();
		}
		m00 = data[0];
		m01 = data[1];
		m02 = data[2];

		m10 = data[3];
		m11 = data[4];
		m12 = data[5];

		m20 = data[6];
		m21 = data[7];
		m22 = data[8];
	}

	/**Gets the transpose of the matrix.
	 * 
	 * @return the transpose of the matrix.
	 */
	public Matrix3 transpose() {
		return new Matrix3(m00, m10, m20,
				           m01, m11, m21,
				           m02, m12, m22);
	}
	
	public static Matrix3 add(Matrix3 m1, Matrix3 m2) {
		return new Matrix3(m1.m00+m2.m00, m1.m01 +m2.m01, m1.m02 + m2.m02,
				           m1.m10+m2.m10, m1.m11+m2.m11, m1.m12 + m2.m12,
				           m1.m20+m2.m20, m1.m21+m2.m21, m1.m22 + m2.m22);
	}

	public static Matrix3 mul(float k, Matrix3 m) {
		return new Matrix3(k*m.m00, k*m.m01, k*m.m02,
				           k*m.m10, k*m.m11, k*m.m12,
				           k*m.m20, k*m.m21, k*m.m22);
	}

	public static Matrix3 createRotation(float angle) {
		float cosAngle = (float)Math.cos(angle);
		float sinAngle = (float)Math.sin(angle);

		return new Matrix3(cosAngle, -sinAngle, 0, 
				           sinAngle, cosAngle,  0, 
				           0,        0,         1);
	}
	
	public static Matrix3 createTranslation(Vector2 offset) {
		return new Matrix3(1, 0, 0, 
				   		   0, 1, 0, 
				   		   offset.X, offset.Y, 1);
	}

	public static Matrix3 createScale(float scale) {
		return new Matrix3(scale, 0, 0,
				           0, scale, 0,
				           0, 0,     1);
	}
	
	public static Matrix3 createScale(float xscale, float yscale) {
		return new Matrix3(xscale, 0, 0,
						   0, yscale, 0,
						   0, 0,      1);
	}

	public static Matrix3 createScale(Vector2 scale) {
		return new Matrix3(scale.X, 0, 0,
						   0, scale.Y, 0,
						   0, 0,      1);
	}


	public static Matrix3 mul(Matrix3 l, Matrix3 r) {

		float m00 = l.m00 * r.m00 + l.m01 * r.m10 + l.m02 * r.m20;
		float m01 = l.m00 * r.m01 + l.m01 * r.m11 + l.m02 * r.m21;
		float m02 = l.m00 * r.m02 + l.m01 * r.m12 + l.m02 * r.m22;
		
		float m10 = l.m10 * r.m00 + l.m11 * r.m10 + l.m12 * r.m20;
		float m11 = l.m10 * r.m01 + l.m11 * r.m11 + l.m12 * r.m21;
		float m12 = l.m10 * r.m02 + l.m11 * r.m12 + l.m12 * r.m22;
		
		float m20 = l.m20 * r.m00 + l.m21 * r.m10 + l.m22 * r.m20;
		float m21 = l.m20 * r.m01 + l.m21 * r.m11 + l.m22 * r.m21;
		float m22 = l.m20 * r.m02 + l.m21 * r.m12 + l.m22 * r.m22;
				
		return new Matrix3(m00, m01, m02,
				           m10, m11, m12,
				           m20, m21, m22);
	}
	
	
	public Matrix3 inverse() {
		float det = this.det();
		if(det == 0.0f) {
			throw new RuntimeException("You cannot take an inverse of this matrix!" +
					"/n It's determinant is 0");
		}
		float scalar = 1 / this.det();
		float m00 = scalar * (this.m11*this.m22 - this.m12*this.m21);
		float m01 = scalar * (this.m02*this.m21 - this.m01*this.m22);
		float m02 = scalar * (this.m01*this.m12 - this.m02*this.m11);
		
		float m10 = scalar * (this.m12*this.m20 - this.m10*this.m22);
		float m11 = scalar * (this.m00*this.m22 - this.m02*this.m20);
		float m12 = scalar * (this.m02*this.m10 - this.m00*this.m12);
		
		float m20 = scalar * (this.m10*this.m21 - this.m11*this.m20);
		float m21 = scalar * (this.m01*this.m20 - this.m00*this.m21);
		float m22 = scalar * (this.m10*this.m21 - this.m11*this.m20);
		
		return new Matrix3(m00, m01, m02,
						   m10, m11, m12,
						   m20, m21, m22);
	}
	
	public float det() {
		return m00*m11*m22 + m10*m21*m02 + m20*m01*m12 
			   - m00*m21*m12 - m20*m11*m02 - m10*m01*m22;
	}
	
	public Matrix4 toMatrix4() {
		return new Matrix4(m00, m01, m02, 0f,
						   m10, m11, m12, 0f,
						   m20, m21, m21, 0f,
						   0f, 0f, 0f, 1f);
	}
	
	
	public FloatBuffer toFlippedFloatBuffer(FloatBuffer buffer) {
		buffer.clear();
		buffer.put(m00); buffer.put(m01); buffer.put(m02); 
		buffer.put(m10); buffer.put(m11); buffer.put(m12);
		buffer.put(m20); buffer.put(m21); buffer.put(m22); 
		buffer.flip();
		return buffer;
	}	

}
