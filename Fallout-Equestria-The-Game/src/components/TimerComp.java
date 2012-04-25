package components;

import misc.EventArgs;
import misc.IEventListener;

import anotations.Editable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import entityFramework.IComponent;
import entityFramework.IEntity;

@XStreamAlias("Timer")
@SuppressWarnings("serial")
public class TimerComp implements IComponent {
	private static final float DEF_DURATION = 1f;
	
	@Editable
	private final float duration;
	private float elapsedTime;
	
	private IEventListener<EventArgs> listener;
	
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
	
	public void triggerEvent(IEntity entity) {
		if(this.listener != null) {
			this.listener.onEvent(entity, EventArgs.Empty);
		}
	}
	
	public void setEventListener(IEventListener<EventArgs> listener) {
		this.listener = listener;
	}
}
