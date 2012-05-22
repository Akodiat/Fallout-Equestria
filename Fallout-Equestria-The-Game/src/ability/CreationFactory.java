package ability;

import components.PhysicsComp;
import components.TransformationComp;

import math.Vector2;

import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;

public class CreationFactory {	
	private IEntityManager entityManager;	
	
	public CreationFactory(IEntityManager manager) {
		this.entityManager = manager;
	}

	public IEntity createEntityAtPosition(IEntityArchetype archetype, Vector2 position) {
		IEntity entity = entityManager.createEntity(archetype);
		entity.getComponent(TransformationComp.class).setPosition(position);
		return entity;
	}
	
	public IEntity createEntityWithTransformation(IEntityArchetype archetype, TransformationComp transformation) {
		IEntity entity = entityManager.createEntity(archetype);
		
		//This value is hard or immposible to calculate of the bat so we use the already pressent origin.
		Vector2 origin = entity.getComponent(TransformationComp.class).getOrigin();
		transformation.setOrigin(origin);
		
		entity.addComponent(transformation);
		entity.refresh();
		return entity;
	}
	
	public IEntity createMovingEntity(IEntityArchetype archetype, Vector2 position, float height, Vector2 velocity) {
		IEntity entity = entityManager.createEntity(archetype);
		if(!entity.hasComponent(PhysicsComp.class)) {
			entity.addComponent(new PhysicsComp());
		}
		
		entity.getComponent(PhysicsComp.class).setVelocity(velocity);
		entity.getComponent(TransformationComp.class).setPosition(position);
		entity.getComponent(TransformationComp.class).setHeight(height);
		
		return entity;
	}
}
