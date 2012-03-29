package ability;

import org.newdawn.slick.openal.Audio;
import math.Vector2;
import components.AttackComp;
import components.PhysicsComp;
import components.TransformationComp;
import content.ContentManager;

import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;

public class BulletAbility extends Ability{
	
	private Audio soundEffect;
	private final IEntityArchetype bulletArch;
	private final float bulletSpeed;
	
	
	public BulletAbility(IEntityArchetype bulletArch, int APCost, float shootingInterval, float bulletSpeed){
		super(APCost, shootingInterval);
		this.bulletArch = bulletArch;
		this.bulletSpeed = bulletSpeed; 

		this.soundEffect = ContentManager.loadSound("effects/pew.ogg");
	}

	@Override
	protected void use(IEntity sourceEntity, Vector2 targetPos, IEntityManager manager) {
		IEntity bullet = manager.createEntity(bulletArch);
		TransformationComp transComp = sourceEntity.getComponent(TransformationComp.class);
		
		Vector2 attackSpeed = Vector2.subtract(targetPos, transComp.getPosition());
		attackSpeed = Vector2.norm(attackSpeed);
		
		bullet.getComponent(AttackComp.class).setSourceEntity(sourceEntity);
		bullet.getComponent(TransformationComp.class).setRotation(attackSpeed.angle());
		bullet.getComponent(TransformationComp.class).setPosition(Vector2.add(transComp.getPosition(), Vector2.mul(60, attackSpeed)));
		bullet.getComponent(PhysicsComp.class).setVelocity(Vector2.mul(bulletSpeed, attackSpeed));
		

		soundEffect.playAsSoundEffect(1.0f, 0.5f, false);
		
		bullet.refresh();
	}

}
