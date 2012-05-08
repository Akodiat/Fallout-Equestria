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

	public static final Vector3 Zero = new Vector3(0,0,0);
	public static final Vector3 UnitX = new Vector3(1,0,0);
	public static final Vector3 UnitY = new Vector3(0,1,0);
	public static final Vector3 UnitZ = new Vector3(0,0,1);
	
	
	public static Vector3 add(Vector3 first, Vector2 second) {
		return new Vector3(first.X + second.X ,
						   first.Y + second.Y ,
						   first.Z);
	}
	
	public static Vector3 subtract(Vector3 first, Vector2 second) {
		return new Vector3(first.X - second.X,
						   first.Y - second.Y,
						   first.Z);
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
	
	public float length() {
		return (float)Math.sqrt(X*X + 
								Y*Y + 
								Z*Z);
	}
	
	
	public static Vector3 lerp(Vector3 first, Vector3 second, float amount) {
			amount = MathHelper.clamp(0, 1, amount);
	        float x = MathHelper.lerp(first.X, second.Y, amount);
	        float y = MathHelper.lerp(first.Y, second.Y, amount);
	        float z = MathHelper.lerp(first.Y, second.Y, amount);
	        return new Vector3(x,y,z);

	}
}
