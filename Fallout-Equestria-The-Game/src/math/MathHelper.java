package math;

public class MathHelper {

	public final static float TwoPi   = 6.2831853f;
	public final static float Pi 	  = 3.1415927f;
	public final static float PiOver2 = 1.5707963f;
	public final static float PiOver3 =	1.0471976f;
	public final static float PiOver4 = 0.7853982f;
	
	
	
	public static float clamp(float min, float max, float value) {
		
		if(min > max)
			throw new IllegalArgumentException("Min cannot be greater then Max");
		
		if(value < min) {
			return min;
		}
		if(value > max) {
			return max;
		}
		
		return value;
	}
	
	public static int clamp(int min, int max, int value) {
		return (int)clamp((float)min, (float)max, (float)value);
	}
}
