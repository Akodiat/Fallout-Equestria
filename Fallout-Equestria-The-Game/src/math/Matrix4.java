package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

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
	
	
	
	public Matrix4() {
		this(1, 0, 0, 0,
		     0, 1, 0, 0,
		     0, 0, 1, 0,
		     0, 0, 0, 1);
	}
	
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
	
	public static Matrix4 createtranslation(Vector2 offset) {
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
}
