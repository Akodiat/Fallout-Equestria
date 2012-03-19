package math;

public class MathHelper {

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
