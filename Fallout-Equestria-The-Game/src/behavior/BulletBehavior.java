package behavior;


import math.Vector2;
import utils.IEventListener;
import components.AnimationComp;
import components.HealthComp;
import components.PhysicsComp;
import components.SpatialComp;
import entityFramework.IEntity;
import graphics.Color;

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
			aCom.getAnimationPlayer().setBoneMirrored("BALL", true);
	}

	
	@Override
	public void onCollisionEnter(IEntity other){
		HealthComp otherHealth = other.getComponent(HealthComp.class);
		
		if(otherHealth != null){
			otherHealth.addHealthPoints(-damage);
		}
		this.SoundManager.playSoundEffect(soundEffect);
		AnimationComp aCom = this.Entity.getComponent(AnimationComp.class);
		aCom.setAnimationPlayer(this.ContentManager.loadAnimationSet("explosion.animset").clone());
		aCom.getAnimationPlayer().addKeyframeTriggerListener(new IEventListener<KeyframeTriggerEventArgs>() {
			@Override
			public void onEvent(Object sender, KeyframeTriggerEventArgs e) {
				killEntity();
				
			}
		});
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