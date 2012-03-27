package graphics;

import java.util.Set;

import com.google.common.collect.ImmutableList;

import utils.ITimerListener;
import utils.Timer;

import utils.IAnimationListener;

public class Animation implements ITimerListener {

	private int activeIndex;
	private ImmutableList<Frame> frames;
	private Timer timer;
	private float duration;
	private Set<IAnimationListener> listeners;
	
	public Animation(ImmutableList<Frame> frames, Timer timer) {
		this.frames = frames;
		this.timer = timer;
		this.duration = timer.getTickInterval()*frames.size();
		this.Start();
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public ImmutableList<Frame> getFrames() {
		return frames;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	public Frame getActiveFrame(){
		return frames.get(activeIndex);
	}

	public float getDuration() {
		return duration;
	}
	
	public void addListener(IAnimationListener listener){
		this.listeners.add(listener);
	}
	
	public void removeListener(IAnimationListener listener){
		this.listeners.remove(listener);
	}

	@Override
	public void Start() {
		activeIndex = 0;
		timer.addEventListener(this);
	}

	@Override
	public void Tick() {
		activeIndex = (activeIndex + 1)%frames.size();
	}

	@Override
	public void Complete() {
		activeIndex = 0;
		timer.removeEventListener(this);
		for (IAnimationListener listener : listeners) {
			listener.onComplete();
		}
	}

}
