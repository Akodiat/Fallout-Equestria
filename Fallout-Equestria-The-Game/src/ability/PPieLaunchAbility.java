package ability;

import com.google.common.collect.ImmutableList;

import math.Vector2;
import components.AttackComponent;
import components.PhysicsComponent;
import components.TransformationComp;

import content.ContentManager;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;

public class PPieLaunchAbility extends Ability{
	private IEntityArchetype pinkeRocket;
	private final static float speedFactor=3;
	public PPieLaunchAbility(int APCost, float shootingInterval){
		super(APCost, shootingInterval);
	}

	@Override
	public void useAbility(IEntity sourceEntity, Vector2 targetPos, IEntityManager manager) {
		IEntity bullet = manager.createEntity(pinkeRocket);
		TransformationComp transComp = sourceEntity.getComponent(TransformationComp.class);
		Vector2 attackSpeed = Vector2.subtract(targetPos, transComp.getPosition());
		attackSpeed = Vector2.norm(attackSpeed);
		
		bullet.getComponent(AttackComponent.class).setTargetGroups(ImmutableList.of("Friends"));
		bullet.getComponent(TransformationComp.class).setRotation(attackSpeed.angle());
		bullet.getComponent(TransformationComp.class).setPosition(Vector2.add(transComp.getPosition(), Vector2.mul(60, attackSpeed)));
		
		bullet.getComponent(PhysicsComponent.class).setVelocity(Vector2.mul(speedFactor, attackSpeed));
		
		bullet.refresh();
	}

	@Override
	public void initialize() {
		pinkeRocket = ContentManager.loadArchetype("pinkieRocket.archetype");
	}

}
