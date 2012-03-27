package ability;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

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
	
	private Audio soundEffect;
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
		
		soundEffect.playAsSoundEffect(1.0f, 1.0f, false);
		
		bullet.refresh();
	}

	@Override
	public void initialize() {
		pinkeRocket = ContentManager.loadArchetype("pinkieRocket.archetype");
		
		try {
			soundEffect = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("resources/sound/effects/pew.ogg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
