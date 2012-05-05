package utils;

import misc.Event;
import misc.IEventListener;
import misc.TimerEventArgs;

public class Timer {
	private final int numTicks;
	private final TimeSpan tickInterval;
	private TimeSpan currentTime;
	private int currentTick;
	private boolean active;
	
	
	private Event<TimerEventArgs> startEvent;
	private Event<TimerEventArgs> tickEvent;
	private Event<TimerEventArgs> completeEvent;
		
	public Timer(int numTicks, float tickInterval) {
		super();
		this.numTicks = numTicks;
		this.tickInterval = TimeSpan.fromSeconds(tickInterval);
		this.currentTime = TimeSpan.fromSeconds(0);
		this.currentTick = 0;
		
		this.startEvent = new Event<>();
		this.tickEvent = new Event<>();
		this.completeEvent = new Event<>();
	}
	
	public void start() {
		this.currentTick = 0;
		this.onStart();
		this.active = true;
	}
	
	public void stop() {
		this.onComplete();
		this.active = false;
	}

	public void Update(TimeSpan elapsedTime) {
		if(this.isActive()) {
			this.currentTime = this.currentTime.add(elapsedTime);
			if(this.currentTime.compareTo(tickInterval) >= 0) {
				this.currentTime = this.currentTime.subtract(tickInterval);
				this.incrementTicks();
			}	
		}
	}
	
	private void incrementTicks() {
		this.currentTick++;
		this.onTick();
		if(this.currentTick >= this.numTicks) {
			this.stop();
		}
	}
	
	private void onStart() {
		TimerEventArgs args = new TimerEventArgs(currentTick, numTicks, (float)tickInterval.getTotalSeconds());
		this.startEvent.invoke(this, args);
	}
	
	private void onTick() {
		TimerEventArgs args = new TimerEventArgs(currentTick, numTicks, (float)tickInterval.getTotalSeconds());
		this.tickEvent.invoke(this, args);
	}

	private void onComplete() {
		TimerEventArgs args = new TimerEventArgs(currentTick, numTicks, (float)tickInterval.getTotalSeconds());
		this.completeEvent.invoke(this, args);
	}
	


	public void addStartListener(IEventListener<TimerEventArgs> listener) {
		this.startEvent.addListener(listener);
	}
	public void addTickListener(IEventListener<TimerEventArgs> listener) {
		this.tickEvent.addListener(listener);
	}
	public void addCompleteListener(IEventListener<TimerEventArgs> listener) {
		this.completeEvent.addListener(listener);
	}
	public void removeStartListener(IEventListener<TimerEventArgs> listener) {
		this.startEvent.removeListener(listener);
	}
	public void removeTickListener(IEventListener<TimerEventArgs> listener) {
		this.tickEvent.removeListener(listener);
	}
	public void removeCompleteListener(IEventListener<TimerEventArgs> listener) {
		this.completeEvent.removeListener(listener);
	}

	public boolean isActive() {
		return active;
	}
}
