package statuses;

import components.HealthComp;
import components.RenderingComp;

import entityFramework.IEntity;
import graphics.Color;
import utils.ITimerListener;
import utils.Timer;

public abstract class RecurringDamageStatus implements IStatus, ITimerListener{
	private IEntity target;
	private Timer timer;
	
	public RecurringDamageStatus(IEntity target, int duration) {
		this.target = target;
		this.timer = new Timer(10, duration);
		this.timer.addEventListener(this);
	}
	
	public abstract void activateStatusEffect();
	
	public void deactivateStatusEffect() {
		target.getComponent(RenderingComp.class).setColor(Color.White);
	}
	
	public void update() {
		target.getComponent(HealthComp.class).addHealthPoints(-10);
	}
	
	@Override
	public void Start() {
		this.activateStatusEffect();
	}

	@Override
	public void Tick() {
		this.update();
	}

	@Override
	public void Complete() {
		this.deactivateStatusEffect();
	}
}
