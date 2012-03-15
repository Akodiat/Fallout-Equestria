package entitySystems;

import math.Vector2;
import components.PhysicsComponent;
import components.PositionComponent;

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

	protected PhysicsSystem(IEntityWorld world) {
		super(world, PhysicsComponent.class, PositionComponent.class);
		// TODO Auto-generated constructor stub
	}
	private ComponentMapper<PositionComponent> CMPos;
	private ComponentMapper<PhysicsComponent> CMPhys;
	@Override
	public void initialize() {
		CMPos = ComponentMapper.create(this.getWorld().getDatabase(), PositionComponent.class);
		CMPhys = ComponentMapper.create(this.getWorld().getDatabase(), PhysicsComponent.class);
	}

	@Override
	protected void processEntity(IEntity entity) {
		PositionComponent posComp = CMPos.getComponent(entity);
		PhysicsComponent physComp = CMPhys.getComponent(entity);
		
		posComp.setPosition(Vector2.add(posComp.getPosition(), physComp.getVelocity()));
		
	}

}
