package entitySystems;

import math.Vector2;
import components.PhysicsComp;
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
		super(world, PhysicsComp.class, TransformationComp.class);
	}
	private ComponentMapper<TransformationComp> transCM;
	private ComponentMapper<PhysicsComp> physCM;

	@Override
	public void initialize() {
		transCM  = ComponentMapper.create(this.getWorld().getDatabase(), TransformationComp.class);
		physCM = ComponentMapper.create(this.getWorld().getDatabase(), PhysicsComp.class);
	}

	@Override
	protected void processEntity(IEntity entity) {
		TransformationComp posComp = transCM.getComponent(entity);
		PhysicsComp physComp = physCM.getComponent(entity);

		posComp.setPosition(Vector2.add(posComp.getPosition(), physComp.getVelocity()));
		posComp.setRotation(posComp.getRotation() + physComp.getTorque());

	}

}
