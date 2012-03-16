package math;

public final class Matrix3 {

	public final float a; public final float b; public final float c;
	public final float d; public final float e; public final float f;
	public final float g; public final float h; public final float i;

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

		this.a = m00; this.d = m01; this.g = m02;
		this.b = m10; this.e = m11; this.h = m12;
		this.c = m20; this.f = m21; this.i = m22;
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
		a = data[0];
		b = data[1];
		c = data[2];

		d = data[3];
		e = data[4];
		f = data[5];

		g = data[6];
		h = data[7];
		i = data[8];
	}

	/**Gets the transpose of the matrix.
	 * 
	 * @return the transpose of the matrix.
	 */
	public Matrix3 transpose() {
		return new Matrix3(a, b, c,
				d, e, f,
				g, h, i);
	}

	public Matrix3 mul(float k, Matrix3 m) {
		return new Matrix3(k*m.a, k*m.d, k*m.g,
				k*m.b, k*m.e, k*m.h,
				k*m.c, k*m.f, k*m.i);
	}

	public Matrix3 rotate(float angle) {
		float cosAngle = (float)Math.cos(angle);
		float sinAngle = (float)Math.sin(angle);

		return new Matrix3(cosAngle, -sinAngle, 0, 
				sinAngle, cosAngle,  0, 
				0,        0,         1);
	}

	public static Matrix3 createScale(float scale) {
		return new Matrix3(scale, 0, 0,
				0, scale, 0,
				0, 0,     1);
	}

	public static Matrix3 mul(Matrix3 l, Matrix3 r) {

		float m00 = l.a * r.a + l.d * r.b + l.g * r.c;
		float m01 = l.a * r.d + l.d * r.e + l.g * r.f;
		float m02 = l.a * r.g + l.d * r.h + l.g * r.i;

		float m10 = l.b * r.a + l.e * r.b + l.h * r.c;
		float m11 = l.b * r.d + l.e * r.e + l.h * r.f;
		float m12 = l.b * r.g + l.e * r.h + l.h * r.i;

		float m20 = l.c * r.a + l.f * r.b + l.i * r.c;
		float m21 = l.c * r.d + l.f * r.e + l.i * r.f;
		float m22 = l.c * r.g + l.f * r.h + l.i * r.i;
		
		return new Matrix3(m00, m01, m02,
				           m10, m11, m12,
				           m20, m21, m22);
	}	

}
