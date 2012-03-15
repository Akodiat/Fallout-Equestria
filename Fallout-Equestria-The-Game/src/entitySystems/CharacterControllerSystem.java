package entitySystems;

import math.Vector2;
import components.InputComponent;
import components.PhysicsComponent;
import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class CharacterControllerSystem extends EntitySingleProcessingSystem{

	protected CharacterControllerSystem(IEntityWorld world) {
		super(world, InputComponent.class, PhysicsComponent.class);
	}
	private ComponentMapper<PhysicsComponent> CMPhys;
	private ComponentMapper<InputComponent> CMInp;
	@Override
	public void initialize() {
		CMPhys = ComponentMapper.create(this.getWorld().getDatabase(), PhysicsComponent.class);
		CMInp = ComponentMapper.create(this.getWorld().getDatabase(), InputComponent.class);
	}

	@Override
	protected void processEntity(IEntity entity) {
		PhysicsComponent	physComp = CMPhys.getComponent(entity);
		InputComponent 		inpComp = CMInp.getComponent(entity);
		
		int speedFactor = 1;
		if(inpComp.isGallopButtonPressed())
			speedFactor=2;
		
		Vector2 velocity = new Vector2(0,0);
		if (inpComp.isBackButtonPressed())
			velocity=Vector2.add(velocity, new Vector2(0,1));
		if (inpComp.isForwardButtonPressed())
			velocity=Vector2.add(velocity, new Vector2(0,-1));
		if (inpComp.isLeftButtonPressed())
			velocity=Vector2.add(velocity, new Vector2(-1,0));
		if (inpComp.isRightButtonPressed())
			velocity=Vector2.add(velocity, new Vector2(1,0));
			
		velocity = Vector2.mul(speedFactor, velocity);
		physComp.setVelocity(velocity);
	}

}
