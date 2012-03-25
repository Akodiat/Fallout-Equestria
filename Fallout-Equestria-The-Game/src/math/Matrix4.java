package math;

import java.nio.FloatBuffer;

/**
 * 
 * @author Lukas Kurtyan
 *
 */
public final class Matrix4 {

	public final float m00;
	public final float m01;
	public final float m02;
	public final float m03;
	
	public final float m10;
	public final float m11;
	public final float m12;
	public final float m13;
	
	public final float m20;
	public final float m21;
	public final float m22;
	public final float m23;
	
	public final float m30;
	public final float m31;
	public final float m32;
	public final float m33;
	
	
	
	public Matrix4(float m00, float m01, float m02, float m03, 
			   	   float m10, float m11, float m12, float m13,
			       float m20, float m21, float m22, float m23,
			       float m30, float m31, float m32, float m33) {
	
	this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
	this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
	this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
	this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
	
	}
	
	public static Matrix4 createRotationZ(float angle) {
		float cosAngle = (float)Math.cos(angle);
		float sinAngle = (float)Math.sin(angle);
		
		return new Matrix4(cosAngle, -sinAngle, 0, 0,
						   sinAngle, cosAngle, 0, 0,
						   0, 0, 1, 0,
						   0, 0, 0, 1);
	}
	
	public static Matrix4 createScale(float scale) {
		return new Matrix4(scale, 0, 0, 0,
						   0, scale, 0 ,0,
						   0, 0, scale ,0, 
						   0, 0, 0, 1);
	}
	
	public static Matrix4 createScale(Vector2 scale) {
		return new Matrix4(scale.X, 0, 0, 0,
						   0, scale.Y, 0 ,0,
						   0, 0, 1 ,0, 
						   0, 0, 0, 1);
	}
	
	
	public static Matrix4 createTranslation(Vector2 offset) {
		return new Matrix4(1, 0, 0, 0,
						   0, 1, 0, 0,
						   0, 0, 1, 0,
						   offset.X, offset.Y, 0, 1);
	}
	
	public static Matrix4 createOrthogonalProjection(float left, float right, float bottom, float top, 
											  float zNear, float zFar) {
		
		float m00 = 2 / (right - left);
		float m11 = 2 / (top - bottom);
		float m22 = 1 / (zFar - zNear);
		float m23 = -zNear / (zFar - zNear);
		float m33 = 1;
		
		return new Matrix4(m00, 0, 0, 0,
						   0, m11, 0, 0,
						   0, 0, m22, m23,
						   0, 0, 0, m33);
	}
	
	public static Matrix4 mul(Matrix4 l, Matrix4 r) {
		
		float m00 = l.m00 * r.m00 + l.m01 * r.m10 + l.m02 * r.m20 + l.m03 * r.m30;
		float m01 = l.m00 * r.m01 + l.m01 * r.m11 + l.m02 * r.m21 + l.m03 * r.m31;
		float m02 = l.m00 * r.m02 + l.m01 * r.m12 + l.m02 * r.m22 + l.m03 * r.m32;
		float m03 = l.m00 * r.m03 + l.m01 * r.m13 + l.m02 * r.m23 + l.m03 * r.m33;
		
		float m10 = l.m10 * r.m00 + l.m11 * r.m10 + l.m12 * r.m20 + l.m13 * r.m30;
		float m11 = l.m10 * r.m01 + l.m11 * r.m11 + l.m12 * r.m21 + l.m13 * r.m31;
		float m12 = l.m10 * r.m02 + l.m11 * r.m12 + l.m12 * r.m22 + l.m13 * r.m32;
		float m13 = l.m10 * r.m03 + l.m11 * r.m13 + l.m12 * r.m23 + l.m13 * r.m33;
		
		float m20 = l.m20 * r.m00 + l.m21 * r.m10 + l.m22 * r.m20 + l.m23 * r.m30;
		float m21 = l.m20 * r.m01 + l.m21 * r.m11 + l.m22 * r.m21 + l.m23 * r.m31;
		float m22 = l.m20 * r.m02 + l.m21 * r.m12 + l.m22 * r.m22 + l.m23 * r.m32;
		float m23 = l.m20 * r.m03 + l.m21 * r.m13 + l.m22 * r.m23 + l.m23 * r.m33;
		
		float m30 = l.m30 * r.m00 + l.m31 * r.m10 + l.m32 * r.m20 + l.m33 * r.m30;
		float m31 = l.m30 * r.m01 + l.m31 * r.m11 + l.m32 * r.m21 + l.m33 * r.m31;
		float m32 = l.m30 * r.m02 + l.m31 * r.m12 + l.m32 * r.m22 + l.m33 * r.m32;
		float m33 = l.m30 * r.m03 + l.m31 * r.m13 + l.m32 * r.m23 + l.m33 * r.m33;
		
		return new Matrix4(m00, m01, m02, m03,
						    m10, m11, m12, m13,
						    m20, m21, m22, m23,
						    m30, m31, m32, m33);
	}
	
	public static Matrix4 add(Matrix4 left, Matrix4 right) {
		return new Matrix4(left.m00 + right.m00, left.m01 + right.m01, left.m02 + right.m02, left.m03 + right.m03,
						   left.m10 + right.m10, left.m11 + right.m11, left.m12 + right.m12, left.m13 + right.m13,
						   left.m20 + right.m20, left.m21 + right.m21, left.m22 + right.m22, left.m23 + right.m23,
						   left.m30 + right.m30, left.m31 + right.m31, left.m32 + right.m32, left.m33 + right.m33);
		
	}

	public FloatBuffer toFlippedFloatBuffer(FloatBuffer buffer) {
		buffer.clear();
		buffer.put(m00); buffer.put(m01); buffer.put(m02); buffer.put(m03);
		buffer.put(m10); buffer.put(m11); buffer.put(m12); buffer.put(m13);
		buffer.put(m20); buffer.put(m21); buffer.put(m22); buffer.put(m23);
		buffer.put(m30); buffer.put(m31); buffer.put(m32); buffer.put(m33);
		buffer.flip();
		return buffer;
	}
	
	public float det(){
		float det = m00*m11*m22*m33 + m00*m12*m23*m31 +	m00*m13*m21*m32
				+	m01*m10*m23*m32 + m01*m12*m20*m33 +	m01*m13*m22*m30
				+	m02*m10*m21*m33 + m02*m11*m23*m30 +	m02*m13*m20*m31
				+	m03*m10*m22*m31 + m03*m11*m20*m32 + m03*m12*m21*m30
				-	m00*m11*m23*m32 - m00*m12*m21*m33 - m00*m13*m22*m31
				-	m01*m10*m22*m33 - m01*m12*m23*m30 - m01*m13*m20*m32
				-	m02*m10*m23*m31 - m02*m11*m20*m33 - m02*m13*m21*m30
				-	m03*m10*m21*m32 - m03*m11*m22*m30 - m03*m12*m20*m31;
		return det;
	}
	
	public  Matrix4 inverse(){
		float det = this.det();
		float b11 = (m11*m22*m33 + m12*m23*m31 + m13*m21*m32 - m11*m23*m32 - m12*m21*m33 - m13*m22*m31)/det;
		float b12 = (m01*m23*m32 + m02*m21*m33 + m03*m22*m31 - m01*m22*m33 - m02*m23*m31 - m03*m21*m32)/det;
		float b13 = (m01*m12*m33 + m02*m13*m31 + m03*m11*m32 - m01*m13*m32 - m02*m11*m33 - m03*m12*m31)/det;
		float b14 = (m01*m13*m22 + m02*m11*m23 + m03*m12*m21 - m01*m12*m23 - m02*m13*m21 - m03*m11*m22)/det;
		float b21 = (m10*m23*m32 + m12*m20*m33 + m13*m22*m30 - m10*m22*m33 - m12*m23*m30 - m13*m20*m32)/det;
		float b22 = (m00*m22*m33 + m02*m23*m30 + m03*m20*m32 - m00*m23*m32 - m02*m20*m33 - m03*m22*m30)/det;
		float b23 = (m00*m13*m32 + m02*m10*m33 + m03*m12*m30 - m00*m12*m33 - m02*m13*m30 - m03*m10*m32)/det;
		float b24 = (m00*m12*m23 + m02*m13*m20 + m03*m10*m22 - m00*m13*m22 - m02*m10*m23 - m03*m12*m20)/det;
		float b31 = (m10*m21*m33 + m11*m23*m30 + m13*m20*m31 - m10*m23*m31 - m11*m20*m33 - m13*m21*m30)/det;
		float b32 = (m00*m23*m31 + m01*m20*m33 + m03*m21*m30 - m00*m21*m33 - m01*m23*m30 - m03*m20*m31)/det;
		float b33 = (m00*m11*m33 + m01*m13*m30 + m03*m10*m31 - m00*m13*m31 - m01*m10*m33 - m03*m11*m30)/det;
		float b34 = (m00*m13*m21 + m01*m10*m23 + m03*m11*m20 - m00*m11*m23 - m01*m13*m20 - m03*m10*m21)/det;
		float b41 = (m10*m22*m31 + m11*m20*m32 + m12*m21*m30 - m10*m21*m32 - m11*m22*m30 - m12*m20*m31)/det;
		float b42 = (m00*m21*m32 + m01*m22*m30 + m02*m20*m31 - m00*m22*m31 - m01*m20*m32 - m02*m21*m30)/det;
		float b43 = (m00*m12*m31 + m01*m10*m32 + m02*m11*m30 - m00*m11*m32 - m01*m12*m30 - m02*m10*m31)/det;
		float b44 = (m00*m11*m22 + m01*m12*m20 + m02*m10*m21 - m00*m12*m21 - m01*m10*m22 - m02*m11*m20)/det;
		
		return new Matrix4(b11, b12, b13, b14,
						   b21, b22, b23, b24,
						   b31, b32, b33, b34,
						   b41, b42, b43, b44);
	}
	
	public static Boolean equals(Matrix4 left, Matrix4 right) {
		return (left.m00 == right.m00 && left.m01 == right.m01 && left.m02 == right.m02 && left.m03 == right.m03 &&
				left.m10 == right.m10 && left.m11 == right.m11 && left.m12 == right.m12 && left.m13 == right.m13 &&
				left.m20 == right.m20 && left.m21 == right.m21 && left.m22 == right.m22 && left.m23 == right.m23 &&
				left.m30 == right.m30 && left.m31 == right.m31 && left.m32 == right.m32 && left.m33 == right.m33);

	}
	
	public static Boolean roughlyEquals(Matrix4 left, Matrix4 right, float errorMarigin) {
		return (left.m00 < errorMarigin +  right.m00 && left.m01 < errorMarigin +  right.m01 && left.m02 < errorMarigin +  right.m02 && left.m03 < errorMarigin +  right.m03 &&
				left.m10 < errorMarigin +  right.m10 && left.m11 < errorMarigin +  right.m11 && left.m12 < errorMarigin +  right.m12 && left.m13 < errorMarigin +  right.m13 &&
				left.m20 < errorMarigin +  right.m20 && left.m21 < errorMarigin +  right.m21 && left.m22 < errorMarigin +  right.m22 && left.m23 < errorMarigin +  right.m23 &&
				left.m30 < errorMarigin +  right.m30 && left.m31 < errorMarigin +  right.m31 && left.m32 < errorMarigin +  right.m32 && left.m33 < errorMarigin +  right.m33
				&&
				left.m00 > -errorMarigin + right.m00 && left.m01 > -errorMarigin + right.m01 && left.m02 > -errorMarigin + right.m02 && left.m03 > -errorMarigin + right.m03 &&
				left.m10 > -errorMarigin + right.m10 && left.m11 > -errorMarigin + right.m11 && left.m12 > -errorMarigin + right.m12 && left.m13 > -errorMarigin + right.m13 &&
				left.m20 > -errorMarigin + right.m20 && left.m21 > -errorMarigin + right.m21 && left.m22 > -errorMarigin + right.m22 && left.m23 > -errorMarigin + right.m23 &&
				left.m30 > -errorMarigin + right.m30 && left.m31 > -errorMarigin + right.m31 && left.m32 > -errorMarigin + right.m32 && left.m33 > -errorMarigin + right.m33);

	}
	
	
	
	
	public static final Matrix4 Identity = new Matrix4(1.0f, 0.0f, 0.0f, 0.0f,
													   0.0f, 1.0f, 0.0f, 0.0f,
													   0.0f, 0.0f, 1.0f, 0.0f,
													   0.0f, 0.0f, 0.0f, 1.0f);
	
	public String toString() {
		return this.m00 + " " + this.m01 + " " + this.m02 + " " + this.m03 + "\n"
			+  this.m10 + " " + this.m11 + " " + this.m12 + " " + this.m13 + "\n"
			+  this.m20 + " " + this.m21 + " " + this.m22 + " " + this.m23 + "\n"
			+  this.m30 + " " + this.m31 + " " + this.m32 + " " + this.m33 + "\n";
	}

}

