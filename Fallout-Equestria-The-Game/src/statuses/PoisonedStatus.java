package statuses;

import components.RenderingComponent;

import entityFramework.IEntity;
import graphics.Color;
import utils.Timer;

public class PoisonedStatus extends RecurringDamageStatus {
	private IEntity target;
	private Timer timer;
	
	public PoisonedStatus(IEntity target, int duration) {
		super(target, duration);
	}
	
	@Override
	public void activateStatusEffect() {
		target.getComponent(RenderingComponent.class).setColor(Color.Purple);
		timer.Start();
	}
}