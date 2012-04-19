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
		//We cannot use the double overload since precision might be lost.
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
	
	public static double min(double val0, double val1) {
		if(val0 < val1)
			return val0;
		else
			return val1;
	}
	public static float min(float val0, float val1) {
		return (float) min((double)val0,(double)val1);
	}
	public static long min(long val0,long val1) {
		//Cannot use the double overload since we lose precision.
		if(val0 < val1)
			return val0;
		else
			return val1;
	}
	public static int min(int val0, int val1) {
		return (int)min((long)val0,(long)val1);
	}

	public static double max(double val0, double val1) {
		if(val0 > val1)
			return val0;
		else
			return val1;
	}
	public static float max(float val0, float val1) {
		return (float) max((double)val0,(double)val1);
	}
	public static long max(long val0,long val1) {
		//Cannot use the double overload since we lose precision.
		if(val0 > val1)
			return val0;
		else
			return val1;
	}
	public static int max(int val0, int val1) {
		return (int)max((long)val0,(long)val1);
	}

	public static float sin(double angle) {
		return (float)Math.sin(angle);
	}

	public static float lerp(float rotation, float rotation2, float t) {
		t = MathHelper.clamp(0,	1, t);
		return rotation + (rotation2 - rotation) * t;	
	}
	
	public static float cos(double angle) {
		return (float)Math.cos(angle);
	}
}
