package entitySystems;

import java.util.List;

import utils.TimeSpan;
import utils.Timer;


import components.TimerComp;

import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class TimerSystem extends EntitySingleProcessingSystem{

	public TimerSystem(IEntityWorld world) {
		super(world, TimerComp.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize() {		
	}

	@Override
	protected void processEntity(IEntity entity) {
		TimerComp comp = entity.getComponent(TimerComp.class);
		TimeSpan elapsedTime = this.getWorld().getTime().getElapsedTime();
		List<Timer> timers = comp.getTimers();
		for (Timer timer : timers) {
			timer.Update(elapsedTime);
		}
		
		for (int i = timers.size() - 1; i >= 0; i--) {
			Timer t = timers.get(i);
			if(!t.isActive()) {
				timers.remove(i);
			}
		}
		
		
	}

}
