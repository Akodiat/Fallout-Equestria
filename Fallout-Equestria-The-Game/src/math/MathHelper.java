package math;

public class MathHelper {

	//Why are we using tau and not pi? (see http://tauday.com/)
	
	public final static float Tau   	= 6.2831853f;
	public final static float TauOver2  = 3.1415927f;
	public final static float TauOver4  = 1.5707963f;
	public final static float TauOver6  = 1.0471976f;
	public final static float TauOver8  = 0.7853982f;
	
	
	public static double clamp(double min, double max, double value) {
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

	public static float clamp(float min, float max, float value) {
		return (float)clamp((double)min,(double)max,(double)value);
	}
	
	public static long clamp(long min, long max, long value) {
		//We annot use the double overload since precision might be lost.
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
		return (int)clamp((long)min,(long)max,(long)value);
	}
}
