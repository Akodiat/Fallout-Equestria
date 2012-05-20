package components;

import java.util.ArrayList;
import java.util.List;

import utils.time.Timer;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import entityFramework.IComponent;

@XStreamAlias("Timer")
public class TimerComp implements IComponent {
	private final List<Timer> timers;
	public TimerComp() {
		this.timers = new ArrayList<>();
	}
	
	public Object clone() {
		return new TimerComp();
	}
	
	
	public void addTimer(Timer timer) {
		this.timers.add(timer);
		timer.start();
	}
	

	public List<Timer> getTimers() {
		return timers;
	}
}
