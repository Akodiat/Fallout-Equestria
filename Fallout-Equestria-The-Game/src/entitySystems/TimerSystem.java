package entitySystems;

import components.TimerComp;

import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class TimerSystem extends EntitySingleProcessingSystem{

	protected TimerSystem(IEntityWorld world) {
		super(world, TimerComp.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize() {		
	}

	@Override
	protected void processEntity(IEntity entity) {
		TimerComp comp = entity.getComponent(TimerComp.class);
		comp.increaseElapsedTime((float)this.getWorld().getTime().getElapsedTime().getTotalSeconds());
		if(comp.getElapsedTime() > comp.getDuration()) {
			comp.triggerEvent(entity);
		}
	}

}
