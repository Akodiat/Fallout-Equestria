package math;

public final class Vector4 {
	public final float X;
	public final float Y;
	public final float Z;
	public final float W;
	
	public Vector4(float x, float y, float z, float w) {
		this.X = x;
		this.Y = y;
		this.Z = z;
		this.W = w;
	}
	
	public static final Vector4 Zero = new Vector4(0.0f, 0.0f, 0.0f, 0.0f);
	public static final Vector4 One = new Vector4(1.0f, 1.0f, 1.0f, 1.0f);
}