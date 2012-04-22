package components;

import misc.IEventListener;

import anotations.Editable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;

@XStreamAlias("Timer")
public class TimerComp implements IComponent {
	private static final float DEF_DURATION = 1f;
	
	@Editable
	private final float duration;
	private float elapsedTime;
	
	private IEventListener listener;
	
	public TimerComp() {
		this(DEF_DURATION);
	}
	
	public TimerComp(float duration) {
		this.duration = duration;
		this.elapsedTime = 0;
	}	
	
	public TimerComp(TimerComp timerComp) {
		this.duration = timerComp.duration;
		this.elapsedTime = 0;
	}

	public Object clone() {
		return new TimerComp(this);
	}
	
	public float getDuration() {
		return this.duration;
	}
	
	public float getElapsedTime() {
		return this.elapsedTime;
	}
	
	public void increaseElapsedTime(float delta) {
		this.elapsedTime += delta;
	}
	
	public void triggerEvent() {
		if(this.listener != null) {
			this.listener.onEvent();
		}
	}
	
	public void setEventListener(IEventListener listener) {
		this.listener = listener;
	}

	public void invokeTimedEvent() {
		if(this.listener != null) {
			this.listener.onEvent();
		}
		
	}
}
