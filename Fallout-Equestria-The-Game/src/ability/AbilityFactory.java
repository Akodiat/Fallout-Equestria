package ability;

import utils.Circle;
import components.AttackComp;
import components.ExistanceComp;
import components.InputComp;
import components.PhysicsComp;
import components.RenderingComp;
import components.SpecialComp;
import components.TransformationComp;
import content.ContentManager;

import math.Vector2;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class AbilityFactory {
	
	private IEntityManager manager;
	
	public AbilityFactory(IEntityManager manager){
		this.manager = manager;
	}
	
	public IEntity createProjectile(Vector2 position, Vector2 velocity, IEntityArchetype bulletArch, SpecialComp specialComp){
		float distanceFromEntity = 100;
		float velocityFactor = 30;
		IEntity bullet = manager.createEntity(bulletArch);
		bullet.getComponent(TransformationComp.class).setPosition(Vector2.add(Vector2.mul(distanceFromEntity, Vector2.norm(velocity)), position));
		bullet.getComponent(PhysicsComp.class).setVelocity(Vector2.mul(velocityFactor, velocity));
		bullet.addComponent(specialComp);
		bullet.refresh();
		
		return bullet;
	}

}
