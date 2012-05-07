package math;

public class Vector3 {
	public final float X;
	public final float Y;
	public final float Z;
	
	public Vector3(float x, float y, float z) {
		this.X = x;
		this.Y = y;
		this.Z = z;
	}
	
	public Vector3(Vector2 vector2, float z) {
		this.X = vector2.X;
		this.Y = vector2.Y;
		this.Z = z;
	}
	
	public static Vector3 add(Vector3 first, Vector3 second) {
		return new Vector3(first.X + second.X,
						   first.Y + second.Y,
						   first.Z + second.Z);
	}
	
	public static Vector3 subtract(Vector3 first, Vector3 second) {
		return new Vector3(first.X - second.X,
						   first.Y - second.Y,
						   first.Z - second.Z);
	}
	
	
}
