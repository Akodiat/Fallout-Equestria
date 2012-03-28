package ability;

import math.Vector2;
import components.AttackComponent;
import components.PhysicsComponent;
import components.TransformationComp;

import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;

public class BulletAbility extends Ability{
	
	//TODO enable sound once again, When we all have the nessasary packages.
	//private Audio soundEffect;
	private final IEntityArchetype bulletArch;
	private final float bulletSpeed;
	
	
	public BulletAbility(IEntityArchetype bulletArch, int APCost, float shootingInterval, float bulletSpeed){
		super(APCost, shootingInterval);
		this.bulletArch = bulletArch;
		this.bulletSpeed = bulletSpeed;
		
	}

	@Override
	protected void use(IEntity sourceEntity, Vector2 targetPos, IEntityManager manager) {
		IEntity bullet = manager.createEntity(bulletArch);
		TransformationComp transComp = sourceEntity.getComponent(TransformationComp.class);
		
		Vector2 attackSpeed = Vector2.subtract(targetPos, transComp.getPosition());
		attackSpeed = Vector2.norm(attackSpeed);
		
		bullet.getComponent(AttackComponent.class).setSourceEntity(sourceEntity);
		bullet.getComponent(TransformationComp.class).setRotation(attackSpeed.angle());
		bullet.getComponent(TransformationComp.class).setPosition(Vector2.add(transComp.getPosition(), Vector2.mul(60, attackSpeed)));
		bullet.getComponent(PhysicsComponent.class).setVelocity(Vector2.mul(bulletSpeed, attackSpeed));
		
		//TODO enable once again. Later
		//ssssoundEffect.playAsSoundEffect(1.0f, 1.0f, false);
		
		bullet.refresh();
	}

}
