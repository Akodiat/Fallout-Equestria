package scripting;

import org.newdawn.slick.openal.Audio;

import anotations.Editable;
import math.Vector2;
import utils.Circle;
import utils.GameTime;
import utils.MouseButton;
import utils.MouseState;

import components.AbilityComp;
import components.AttackComp;
import components.ExistanceComp;
import components.HealthComp;
import components.InputComp;
import components.PhysicsComp;
import components.RenderingComp;
import components.TransformationComp;
import components.WeaponComp;
import content.ContentManager;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;

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