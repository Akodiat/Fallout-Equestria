package ability;

import components.TimerComp;

import utils.Timer;
import math.Vector2;
import misc.IEventListener;
import misc.TimerEventArgs;
import entityFramework.IEntityArchetype;

public class MachineBullet extends BulletAbility {
		
	private float fireInterval;
	private Timer timer;
	
	public MachineBullet(IEntityArchetype bulletArchetype, int speed,
			Vector2 fireOffset, float fireInterval) {
		super(bulletArchetype, speed, fireOffset, true);
		this.fireInterval = fireInterval;
	}

	@Override
	public void start() {
		super.start();
		this.timer = new Timer(Integer.MAX_VALUE, this.fireInterval);
		timer.addTickListener(new IEventListener<TimerEventArgs>() {
			public void onEvent(Object sender, TimerEventArgs e) {
				createBullet();
			}
		});		
		
		TimerComp timerComp = this.SorceEntity.getComponent(TimerComp.class);
		if(timerComp == null) {
			timerComp = new TimerComp();
			this.SorceEntity.addComponent(timerComp);
			this.SorceEntity.refresh();
		} 
		
		timerComp.addTimer(timer);
		timer.start();
		
	}
	
	@Override
	public void stop() {
		this.timer.stop();
	}
}
