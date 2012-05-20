package utils;


public class TimerEventArgs extends EventArgs{
	private final int currentTick;
	private final int numTicks;
	private final float tickInterval;
	
	public TimerEventArgs(int currentTick, int numTicks, float tickInterval) {
		super();
		this.currentTick = currentTick;
		this.numTicks = numTicks;
		this.tickInterval = tickInterval;
	}

	public int getCurrentTick() {
		return currentTick;
	}

	public int getNumTicks() {
		return numTicks;
	}

	public float getTickInterval() {
		return tickInterval;
	}
	
	
	
}
