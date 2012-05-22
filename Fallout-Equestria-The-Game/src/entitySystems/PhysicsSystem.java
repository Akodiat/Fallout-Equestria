package entitySystems;

import utils.HeightMap;


import com.google.common.collect.ImmutableSet;

import math.Vector2;
import components.BehaviourComp;
import components.PhysicsComp;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class PhysicsSystem extends EntityProcessingSystem {

	public PhysicsSystem(IEntityWorld world, HeightMap heightMap) {
		super(world, PhysicsComp.class, TransformationComp.class);
		this.heightMap = heightMap;
	}
	private ComponentMapper<TransformationComp> transCM;
	private ComponentMapper<PhysicsComp> physCM;
	private HeightMap heightMap;
	private float gravity = 1200f;
	
	
	@Override
	public void initialize() {
		transCM  = ComponentMapper.create(this.getWorld().getDatabase(), TransformationComp.class);
		physCM = ComponentMapper.create(this.getWorld().getDatabase() , PhysicsComp.class);
	}
	protected void processEntity(IEntity entity) {
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		float deltaTime = (float)this.getWorld().getTime().getElapsedTime().getTotalSeconds();
				
		for (IEntity entity : entities) {
			TransformationComp posComp = transCM.getComponent(entity);
			PhysicsComp physComp = physCM.getComponent(entity);

			Vector2 displacement = Vector2.mul(deltaTime,physComp.getVelocity());
			posComp.setPosition(Vector2.add(posComp.getPosition(), displacement));
			posComp.setRotation(posComp.getRotation() + physComp.getTorque());
			

			float heightDisplacement = physComp.getHeightVelocity() * deltaTime;
			posComp.setHeight(posComp.getHeight() + heightDisplacement);
			
			if(physComp.isAffectedByGravity()) {
				applyGravity(deltaTime, posComp, physComp, entity);
			}									
		}
	}
	
	
	private void applyGravity(float deltaTime,	TransformationComp posComp, PhysicsComp physComp, IEntity entity) {
		float heightMapHeight = heightMap.getHeightAt(posComp.getPosition());
		float stepGravity = gravity * deltaTime;
		if(posComp.getHeight() >= heightMapHeight) {
			physComp.setHeightVelocity(physComp.getHeightVelocity() - stepGravity);
		} else {
			posComp.setHeight(heightMapHeight);
			if(physComp.getHeightVelocity() < 0) {
				physComp.setHeightVelocity(0);
				hitGround(entity);
			}
		}
	}
	
	private void hitGround(IEntity entity) {
		BehaviourComp comp = entity.getComponent(BehaviourComp.class);
		if(comp != null && comp.isInitialized()) {
			comp.getBehavior().onGroundCollision();
		}
	}

}
