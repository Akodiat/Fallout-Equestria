package utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**A timer class. 
 * To make events trigger at specific times. 
 * 
 * @author Lukas Kurtyan
 *
 */
public class Timer {
	
	Set<ITimerListener> listeners;
	private float tickInterval;
	private float elapsedTime;
	private int tickCount;
	private int numTicks;
	
	private static Set<Timer> activeTimers =
								new HashSet<>();
						
	public Timer(float tickInterval, int numTicks) {
		this.listeners = new HashSet<>();
		this.tickInterval = tickInterval;
		this.elapsedTime = 0.0f;
		this.tickCount = 0;
		this.numTicks = numTicks;
	}
	
	/**Starts the timer.
	 * 
	 */
	public void Start() {
		if(!this.isActive()) {
			this.onStart();
			activeTimers.add(this);
		}
	}
	
	/**Pauses the timer.
	 * Use unpause to start it again.
	 * 
	 */
	public void pause() {
		activeTimers.remove(this);
	}
	
	/**Unpauses the timer.
	 * Use this after the timer has been paused.
	 */
	public void unpaus() {
		activeTimers.add(this);
	}
	
	/**Stops the timer. 
	 * This differs from pause due to the fact that the timer resets it's state.
	 */
	public void Stop() {
		if(this.isActive()) {
			this.onComplete();
			activeTimers.remove(this);
		}
		
		this.tickCount = 0;
		this.elapsedTime = 0.0f;
	}
	
	public float getTickInterval() {
		return tickInterval;
	}

	public void setTickInterval(float tickInterval) {
		this.tickInterval = tickInterval;
	}

	public int getNumTicks() {
		return numTicks;
	}

	public void setNumTicks(int numTicks) {
		this.numTicks = numTicks;
	}

	private void onStart() {
		for (ITimerListener listener : this.listeners) {
			listener.Start();
		}
	}

	private void onTick() {
		for (ITimerListener listener : this.listeners) {
			listener.Tick();
		}
	}

	private void onComplete() {
		for (ITimerListener listener : this.listeners) {
			listener.Complete();
		}
	}
	
	private void update(float delta) {
		this.elapsedTime += delta;
		while(this.elapsedTime >= this.tickInterval) {
				this.elapsedTime -= this.tickInterval;
				this.tickCount++;
				this.onTick();
				if(this.isComplete()) {
					return;
				}
		}		
	}
	
	private boolean isComplete() {
		return this.tickCount >= this.numTicks;
	}
	
	public boolean isActive() {
		return activeTimers.contains(this);
	}

	/**Updates all the active timers applying the elapsed time.
	 * Note: This method must be called, otherwise the timers wont tick.
	 * 
	 * @param delta the amount of time since the last update.
	 */
	public static void updateTimers(float delta) {
		//TODO check if this rly is a proper way to do it!
		List<Timer> timersToRemove = new ArrayList<>();
		for (Timer timer : activeTimers) {
			timer.update(delta);
			if(timer.isComplete()) {
				timer.onComplete();
				timersToRemove.add(timer);
			}
		}
		
		activeTimers.removeAll(timersToRemove);
	}
	
	public void addEventListener(ITimerListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeEventListener(ITimerListener listener) {
		this.listeners.remove(listener);
	}
}
