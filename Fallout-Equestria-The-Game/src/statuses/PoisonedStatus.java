package statuses;

import components.RenderingComp;

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
		target.getComponent(RenderingComp.class).setColor(Color.Purple);
		timer.Start();
	}
}