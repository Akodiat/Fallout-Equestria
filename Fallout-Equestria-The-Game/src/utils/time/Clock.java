package utils.time;

import org.lwjgl.Sys;


public class Clock {
	private long startTime;
	private long totalTime;
	private long delthaTime;
	
	public Clock() {
		initializeTime();
	}

	private void initializeTime() {
		this.startTime = this.getTime();
		this.totalTime = 0L;
		this.delthaTime = 0L;
	}
	
	public void update() {
		long prevTotalTime = totalTime;
		this.totalTime = this.getTime() -  startTime;
		this.delthaTime = totalTime - prevTotalTime;
	}
	
	public void reset() {
		this.initializeTime(); 
	}
	
	public GameTime getGameTime() {
		return new GameTime(TimeSpan.fromMiliseconds(totalTime),
							TimeSpan.fromMiliseconds(delthaTime));	
	}
	
	/**Gets the time in miliseconds
	 * 
	 * @return The time on the system in miliseconds.
	 */
	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
}
