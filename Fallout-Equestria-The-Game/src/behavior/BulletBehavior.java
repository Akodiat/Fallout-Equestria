package behavior;

import math.Vector2;
import utils.IEventListener;
import utils.TimerEventArgs;
import utils.time.Timer;
import components.AnimationComp;
import components.HealthComp;
import components.PhysicsComp;
import components.SpatialComp;
import components.TimerComp;
import components.TransformationComp;
import entityFramework.IEntity;

import animation.KeyframeTriggerEventArgs;
import anotations.Editable;

@Editable
public class BulletBehavior extends Behavior{
	private @Editable final float damage;
	private @Editable final String soundEffect;
	
	public BulletBehavior(){
		this.damage = 10;
		this.soundEffect = null;
	}
	private BulletBehavior(BulletBehavior bulletBehavior){
		this.damage = bulletBehavior.damage;
		this.soundEffect = bulletBehavior.soundEffect;
	}
	
	
	@Override
	public void start() {
		AnimationComp aCom = this.Entity.getComponent(AnimationComp.class);
		PhysicsComp pCom = this.Entity.getComponent(PhysicsComp.class);
		aCom.getAnimationPlayer().setBoneHidden("RIGIDBODY", true);
		if(pCom.getVelocity().X>0)
			this.getComponent(TransformationComp.class).setMirror(true);
	}

	
	@Override
	public void onCollisionEnter(IEntity other){
		HealthComp otherHealth = other.getComponent(HealthComp.class);
		
		if(otherHealth != null){
			otherHealth.addHealthPoints(-damage);
		}
		this.destroy();
	
	}
	
	@Override
	public void onMapCollision() {
		super.onMapCollision();
		this.destroy();
	}
	
	private void destroy() {	
		this.SoundManager.playSoundEffect(soundEffect);
		AnimationComp aCom = this.Entity.getComponent(AnimationComp.class);
		aCom.setAnimationPlayer(this.ContentManager.loadAnimationSet("explosion.animset").clone());
		aCom.getAnimationPlayer().addKeyframeTriggerListener(new IEventListener<KeyframeTriggerEventArgs>() {
			@Override
			public void onEvent(Object sender, KeyframeTriggerEventArgs e) {
				killEntity();		
			}
		});
		Timer timer = new Timer(1, 1.0f);
		timer.addCompleteListener(new IEventListener<TimerEventArgs>() {
			
			@Override
			public void onEvent(Object sender, TimerEventArgs e) {
				killEntity();
				
			}
		});
		TimerComp timerComp = this.Entity.getComponent(TimerComp.class);
		
		if(timerComp == null) 
		{
			timerComp = new TimerComp();
			this.Entity.addComponent(timerComp);
			this.Entity.refresh();
		}
		timerComp.addTimer(timer);
		
		PhysicsComp pCom = this.Entity.getComponent(PhysicsComp.class);
		pCom.setVelocity(Vector2.Zero);
		
		this.Entity.getComponent(SpatialComp.class).setCollideable(false);
			
	}
	protected void killEntity() {
		this.Entity.kill();
	}

	@Override
	public Object clone() {
		return new BulletBehavior(this);
	}

}