package ability;

import components.TimerComp;

import utils.IEventListener;
import utils.TimerEventArgs;
import utils.time.Timer;
import math.Vector2;
import entityFramework.IEntityArchetype;

public class MachineBullet extends BulletAbility {
		
	private float fireInterval;
	private Timer timer;
	
	public MachineBullet(IEntityArchetype bulletArchetype, int speed,
			float fireInterval) {
		super(bulletArchetype, speed, true);
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
