package scripting;

import org.newdawn.slick.openal.Audio;

import utils.GameTime;

import components.HealthComp;
import components.TransformationComp;
import entityFramework.IEntity;

import anotations.Editable;

@Editable
public class BulletBehavior extends Behavior{
	
	public BulletBehavior(){
		this.damage = 10;
		this.soundEffect = null;
	}
	private BulletBehavior(BulletBehavior bulletBehavior){
		this.damage = bulletBehavior.damage;
		this.soundEffect = bulletBehavior.soundEffect;
	}
	
	@Editable
	private final float damage;
	
	@Editable
	private final Audio soundEffect;
	
	@Override
	public void start() {
		this.Entity.getComponent(TransformationComp.class);
	}

	@Override
	public void update(GameTime time) {
		
	}
	
	@Override
	public void onCollisionEnter(IEntity other){
		System.out.println(this.isInitialized());
		HealthComp otherHealth = other.getComponent(HealthComp.class);
		if(otherHealth != null){
			otherHealth.addHealthPoints(-damage);
			System.out.println(damage);
		}
		soundEffect.playAsSoundEffect(1.0f, 0.5f, false);
		this.Entity.kill();
	}

	@Override
	public Object clone() {
		return new BulletBehavior(this);
	}

}