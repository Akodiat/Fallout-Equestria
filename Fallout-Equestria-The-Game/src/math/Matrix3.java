package math;

public final class Matrix3 {

	public final float m00;
	public final float m01;
	public final float m02;
	
	public final float m10;
	public final float m11;
	public final float m12;
	
	public final float m20;
	public final float m21;
	public final float m22;
	
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
		m10 = data[1];
		m20 = data[2];
		
		m01 = data[3];
		m11 = data[4];
		m21 = data[5];
		
		m02 = data[6];
		m12 = data[7];
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
	
	//TODO more functions should be done.
}
