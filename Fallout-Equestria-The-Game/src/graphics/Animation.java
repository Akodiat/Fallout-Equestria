package graphics;

import com.google.common.collect.ImmutableList;

public class Animation {

	private int activeIndex;
	private ImmutableList<Frame> frames;
	private float time;
	private float tickInterval;

	public Animation(ImmutableList<Frame> frames,float tickInterval) {
		this.frames = frames;
		this.activeIndex = 0;
		this.time = 0;
	}

	public Animation(Animation other) {
		this.frames = other.frames;
	}

	public Animation clone(){
		return new Animation(this);
	}

	public ImmutableList<Frame> getFrames() {
		return frames;
	}
	
	public int numFrames(){
		return this.frames.size();
	}
	

	public Frame getActiveFrame(){
		return frames.get(activeIndex);
	}
	
	public float getDuration(){
		return frames.size()*this.tickInterval;
	}
	
	public void reset(){
		this.activeIndex = 0;
	}

	public void update(float deltha) {
		time += deltha;
		if (time>=tickInterval){
			time -= tickInterval;
			activeIndex = (activeIndex + 1)%frames.size();
		}
	}


}
