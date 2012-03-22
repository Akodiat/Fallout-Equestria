package entitySystems;

import utils.Circle;

import com.google.common.collect.ImmutableSet;

import math.Vector2;
import components.PhysicsComponent;
import components.SpatialComponent;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class PhysicsSystem extends EntitySingleProcessingSystem {

	public PhysicsSystem(IEntityWorld world) {
		super(world, PhysicsComponent.class, TransformationComp.class, SpatialComponent.class);
		// TODO Auto-generated constructor stub
	}
	private ComponentMapper<TransformationComp> transCM;
	private ComponentMapper<PhysicsComponent> physCM;
	private ComponentMapper<SpatialComponent> spatCM;
	
	@Override
	public void initialize() {
		transCM  = ComponentMapper.create(this.getWorld().getDatabase(), TransformationComp.class);
		physCM = ComponentMapper.create(this.getWorld().getDatabase(), PhysicsComponent.class);
		spatCM = ComponentMapper.create(this.getWorld().getDatabase(), SpatialComponent.class);
	}

	@Override
	protected void processEntity(IEntity entity) {
		TransformationComp posComp = transCM.getComponent(entity);
		PhysicsComponent physComp = physCM.getComponent(entity);
		
		posComp.setPosition(Vector2.add(posComp.getPosition(), physComp.getVelocity()));
				
		if(checkIfCollides(entity))
			posComp.setPosition(Vector2.add(posComp.getPosition(), Vector2.mul(-1,physComp.getVelocity())));
		
	}
	private boolean checkIfCollides(IEntity entity){
		TransformationComp posComp = transCM.getComponent(entity);
		PhysicsComponent physComp = physCM.getComponent(entity);
		SpatialComponent spatComp = spatCM.getComponent(entity);
		
		boolean collision=false;
		
		ImmutableSet<IEntity> collidableEntities = this.getWorld().getDatabase().getEntitysContainingComponent(SpatialComponent.class);
		posComp.setPosition(Vector2.add(posComp.getPosition(), physComp.getVelocity()));
		
		
		for(IEntity i:collidableEntities){							//Checking collision with other entities
			SpatialComponent otherSpatiCom = i.getComponent(SpatialComponent.class);
			TransformationComp otherTransCom = i.getComponent(TransformationComp.class);
			if(!i.equals(entity) && Circle.intersects(
					otherSpatiCom.getBounds(),
					otherTransCom.getPosition(),
					spatComp.getBounds(),
					posComp.getPosition())){
				collision=true;
				otherTransCom.setPosition(Vector2.add(otherTransCom.getPosition(), Vector2.mul(1,physComp.getVelocity())));
			}
		}
		return collision;
	}

}
