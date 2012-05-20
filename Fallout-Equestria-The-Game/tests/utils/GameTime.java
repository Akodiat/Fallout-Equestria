package utils;

/**
 * 
 * @author Lukas Kurtyan
 *
 */
public class GameTime {
		
	
	private final TimeSpan totalTime;
	private final TimeSpan elapsedTime;
		
	protected GameTime(TimeSpan totalTime, TimeSpan elapsedTime) {
		this.totalTime = totalTime;
		this.elapsedTime = elapsedTime;
	}

	/**Gets the total time expired since the last 
	 * reset of the timing system.
	 * @return a TimeSpan containing total time information.
	 */
	public TimeSpan getTotalTime() {
		return totalTime;
	}

	/**Gets the elapsed time since the last frame.
	 * 
	 * @return a TimeSpan containing delta time information.
	 */
	public TimeSpan getElapsedTime() {
		return elapsedTime;
	}
	
	public static final GameTime Zero = new GameTime(TimeSpan.Zero, TimeSpan.Zero);
}