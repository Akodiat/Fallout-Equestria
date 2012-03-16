package entitySystems;

import com.google.common.collect.ImmutableSet;

import math.Vector2;
import components.PhysicsComponent;
import components.PositionComponent;
import components.SpatialComponent;

import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityDatabase;
import entityFramework.IEntityWorld;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class PhysicsSystem extends EntitySingleProcessingSystem {

	public PhysicsSystem(IEntityWorld world) {
		super(world, PhysicsComponent.class, PositionComponent.class, SpatialComponent.class);
		// TODO Auto-generated constructor stub
	}
	private ComponentMapper<PositionComponent> posCM;
	private ComponentMapper<PhysicsComponent> physCM;
	private ComponentMapper<SpatialComponent> spatCM;
	
	@Override
	public void initialize() {
		posCM = ComponentMapper.create(this.getWorld().getDatabase(), PositionComponent.class);
		physCM = ComponentMapper.create(this.getWorld().getDatabase(), PhysicsComponent.class);
		spatCM = ComponentMapper.create(this.getWorld().getDatabase(), SpatialComponent.class);
	}

	@Override
	protected void processEntity(IEntity entity) {
		PositionComponent posComp = posCM.getComponent(entity);
		PhysicsComponent physComp = physCM.getComponent(entity);
		SpatialComponent spatComp = spatCM.getComponent(entity);
		boolean collision=false;
		ImmutableSet<IEntity> collidableEntities = this.getWorld().getDatabase().getEntitysContainingComponent(SpatialComponent.class);
		for(IEntity i:collidableEntities){
			if(Vector2.distance((i.getComponent(PositionComponent.class).getPosition()),posComp.getPosition())<20);
				collision=true;
		}
		if(!collision)
			physComp.setVelocity(Vector2.mul(-0.5f, physComp.getVelocity()));
		posComp.setPosition(Vector2.add(posComp.getPosition(), physComp.getVelocity()));
		
	}

}
