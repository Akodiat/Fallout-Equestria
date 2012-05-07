package scripting;


import components.HealthComp;
import components.TransformationComp;
import entityFramework.IEntity;

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
		this.Entity.getComponent(TransformationComp.class);
	}

	
	@Override
	public void onCollisionEnter(IEntity other){
		HealthComp otherHealth = other.getComponent(HealthComp.class);
		
		if(otherHealth != null){
			otherHealth.addHealthPoints(-damage);
		}
		this.SoundManager.playSoundEffect(soundEffect);
		this.Entity.kill();
	}

	@Override
	public Object clone() {
		return new BulletBehavior(this);
	}

}