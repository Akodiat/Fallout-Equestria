package math;

public final class Vector2{
	
	public final float X;
	public final float Y;
	
	/**
	 * Creates a new instance of a Vector2, set to (0,0).
	 */
	public Vector2(){
		this.X = 0;
		this.Y = 0;
	}
	
	/**
	 * Creates a new instance of a Vector2.
 	 * @param x - xCoordinate of the vector.
	 * @param y - yCoordinate of the vector.
	 */
	public Vector2(float x, float y) {
		this.X = x;
		this.Y = y;
	}
	
	/**
	 * Creates a new instance of a Vector2.
 	 * @param xy x and y coordinate.
	 */
	public Vector2(float xy) {
		this.X = xy;
		this.Y = xy;
	}
	
	/**Adds 2 vectors together.
	 * 
	 * @param vector0 the first vector.
	 * @param vector1 the second vector.
	 * @return the vector result.
	 */
	public static Vector2 add(Vector2 vector0, Vector2 vector1) {
		return new Vector2(vector0.X + vector1.X, vector0.Y + vector1.Y);
	}
	
	/**Subtracts one vector from the other.
	 * 
	 * @param vector0 the vector operated on.
	 * @param vector1 the vector to subtract.
	 * @return the vector result of the subtraction.
	 */
	public static Vector2 subtract(Vector2 vector0, Vector2 vector1) {
		return new Vector2(vector0.X - vector1.X, vector0.Y - vector1.Y);
	}
	
	/**Multiplies a vector by a scalar.
	 * 
	 * @param scalar the scalar.
	 * @param vector the vector.
	 * @return the multiplication result.
	 */
	public static Vector2 mul(float scalar, Vector2 vector) {
		return new Vector2(scalar * vector.X, scalar * vector.Y);
	}
	
	/**Multiplies a vector by another vector.
	 * (X1*X2,Y1*Y2)
	 * @return the multiplication result.
	 */
	public static Vector2 mul(Vector2 vector1, Vector2 vector2) {
		return new Vector2(vector1.X * vector2.X, vector1.Y * vector2.Y);
	}
	
	/**Calculates the dot product( scalar product) of 2 vectors.
	 * 
	 * @param vector0 the first vector.
	 * @param vector1 the second vector.
	 * @return the dot product result.
	 */
	public static float dot(Vector2 vector0, Vector2 vector1) {
		return vector0.X * vector1.X + vector0.Y * vector1.Y;
	}
	
	/**
	 * Returns a new vector consisting of the highest 
	 * x- and y-values found in the parameters.
	 */
	public static Vector2 max(Vector2 vector1, Vector2 vector2){
		float highestXValue = vector1.X > vector2.X ? vector1.X:vector2.X;
		float highestYValue = vector1.Y > vector2.Y ? vector1.Y:vector2.Y;
		return new Vector2(highestXValue,highestYValue);
	}
	
	/**
	 * Returns a new vector consisting of the lowest 
	 * x- and y-values found in the parameters.
	 */
	public static Vector2 min(Vector2 vector1, Vector2 vector2){
		float lowestXValue = vector1.X > vector2.X ? vector2.X:vector1.X;
		float lowestYValue = vector1.Y > vector2.Y ? vector2.Y:vector1.Y;
		return new Vector2(lowestXValue,lowestYValue);
	}
	
	/**
	 * Calculates the distance between two vectors.
	 * @param vector0 the first vector.
	 * @param vector1 the second vector.
	 * @return
	 */
	public static float distance(Vector2 vector0, Vector2 vector1) {
		return (float)Math.sqrt(distanceSquared(vector0, vector1));
	}
	
	/**Calculates the distance squared between two vectors.
	 * 
	 * @param vector0
	 * @param vector1
	 * @return
	 */
	public static float distanceSquared(Vector2 vector0, Vector2 vector1) {
		float a = vector0.X - vector1.X;
		float b = vector0.Y - vector1.Y;
		return (a*a + b*b);
	}

	/**
	 * Calculates the length of the vector.
	 * @return the length of the vector.
	 */
	public float length() {
		return (float)(Math.sqrt(X*X + Y*Y));
	}
	
	/**Calculates the orthogonal Projection of one vector upon another.
	 * 
	 * @param toProject the vector that is projected. 
	 * @param toBeProjectedOn the vector that we project upon.
	 * @return the orthogonal projection vector.
	 */
	public static Vector2 orthogonalProjection(Vector2 toProject, Vector2 toBeProjectedOn) {
		
		float numerator = Vector2.dot(toBeProjectedOn, toProject);
		float denominator = Vector2.dot(toBeProjectedOn, toBeProjectedOn);
		
		return Vector2.mul(numerator / denominator, toBeProjectedOn);
	}
	
	/**
	 * Calculates the angle between the vectors.
	 * @param vector0 the first.
	 * @param vector1 the second.
	 * @return the result of the calculation (in radians).
	 */
	public static float angle(Vector2 vector0, Vector2 vector1) {

		float numerator = Vector2.dot(vector0, vector1);
		float denominator = vector0.length() * vector1.length();
		
		return (float)Math.acos(numerator / denominator);
	}
	
	public float angle() {
		return (float)Math.atan2(Y, X);
	}
	
	public static float twoPiAngle(Vector2 vector0, Vector2 vector1){
		float temp = (float) Math.atan2(vector0.Y - vector1.Y, vector0.X - vector1.X);
		System.out.println(temp);
		return temp;
	}
	/**
	 * Normalizes the 2-vector v
	 * @param v
	 * @return the normalized vector.
	 */
	public static Vector2 norm(Vector2 v) {
		return Vector2.mul(1/v.length(), v);
	}
	/**
	 * Rotates the vector v counter-clockwise with rotation rad.
	 * @param v
	 * @param rotation
	 * @return the rotated vector
	 */
	public static Vector2 rotate(Vector2 v, float rotation){
		return new Vector2((float) (v.X*Math.cos(rotation) + v.Y*-1*Math.sin(rotation)),
				(float) (v.X*Math.sin(rotation) + v.Y*Math.cos(rotation)));
	}
	/**
	 * Linear interpolation. Finds and returns a point on the straight 
	 * line between the two points described by the vectors parameters.
	 * If the float parameter does not describe a value (X) between
	 * the x-values of the vectors, it will be clamped.
	 * @param v1 
	 * @param v2
	 * @param amount A value between 1 and 0.
	 * @return
	 */
	public static Vector2 lerp(Vector2 v1, Vector2 v2, float amount) {
		 
		amount = MathHelper.clamp(0, 1, amount);
  
        float y2 = MathHelper.lerp(v1.Y, v2.Y, amount);
        amount = MathHelper.lerp(v1.X, v2.X, amount);
        return new Vector2(amount, y2);
    }
	/**
	 * Linear interpolation. Finds and returns a point on a smooth curve 
	 * between the two points described by the vectors parameters.
	 * If the float parameter does not describe a value (X) between
	 * the x-values of the vectors, it will be clamped.
	 * @param v1 
	 * @param v2
	 * @param amount A value between 1 and 0.
	 * @return
	 */
	public static Vector2 smoothLerp(Vector2 v1, Vector2 v2, float amount) {
		amount = ((amount > 1f) ? 1f : ((amount < 0f) ? 0f : amount));
		amount = amount * amount * (3f - 2f * amount);
		Vector2 result = new Vector2(v1.X + (v2.X - v1.X) * amount,
									 v1.Y + (v2.Y - v1.Y) * amount);
		return result;
    }
	
	/**
	 * A vector with both elements set to Zero.
	 */
	public final static Vector2 Zero = new Vector2(0.0f,0.0f);
	
	/**
	 * A vector with both elements set to One.
	 */
	public final static Vector2 One = new Vector2(1.0f,1.0f);
	
	/**
	 *The unit vector for the X-dimension.
	 */
	public final static Vector2 UnitX = new Vector2(1.0f,0f);
	
	/**
	 * The unit vector for the Y-dimension.
	 */
	public final static Vector2 UnitY = new Vector2(0f,1.0f);
	
	/**
	 * Mirrors vectors in the X-axis.
	 */
	public final static Vector2 MirrorXAxis = new Vector2(1.0f, -1.0f);
	
	/**
	 * Mirrors vectors in the Y-axis.
	 */
	public final static Vector2 MirrorYAxis = new Vector2(-1.0f, 1.0f);	
	
	public boolean equals(Object other) {
		if(other.getClass().equals(Vector2.class)) {
			Vector2 otherVector2 = (Vector2)other;
			return this.X == otherVector2.X && this.Y == otherVector2.Y;
		}
		return false;
	}
	
	public String toString() {
		return  this.X + "," + this.Y ;
	}

}
