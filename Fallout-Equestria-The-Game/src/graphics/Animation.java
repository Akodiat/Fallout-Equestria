package graphics;

import com.google.common.collect.ImmutableList;

import utils.ITimerListener;
import utils.Timer;

public class Animation implements ITimerListener {

	private int activeIndex;
	private ImmutableList<Frame> frames;
	private Timer timer;
	
	public Animation(ImmutableList<Frame> frames, Timer timer) {
		this.activeIndex = 0;
		this.frames = frames;
		this.timer = timer;
		this.timer.addEventListener(this);

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

	@Override
	public void Start() {
		activeIndex = 0;
		
	}

	@Override
	public void Tick() {
		activeIndex = (activeIndex + 1)%frames.size();
	}

	@Override
	public void Complete() {
		activeIndex = 0;
	}

}
