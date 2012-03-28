package entitySystems;


import ability.Ability;
import math.Vector2;
import components.*;
import entityFramework.*;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class CharacterControllerSystem extends EntitySingleProcessingSystem{

	public CharacterControllerSystem(IEntityWorld world) {
		super(world, InputComponent.class, 
					 PhysicsComponent.class,
					 ActionPointsComponent.class,
					 TransformationComp.class,
					 WeaponComponent.class);
	}
	
	@Override
	public void initialize() {
	}

	@Override
	protected void processEntity(IEntity entity) {
		PhysicsComponent	  physComp   = entity.getComponent(PhysicsComponent.class);
		InputComponent 		  inpComp    = entity.getComponent(InputComponent.class);
		TransformationComp    posComp    = entity.getComponent(TransformationComp.class);
		ActionPointsComponent apComp     = entity.getComponent(ActionPointsComponent.class);
		WeaponComponent 	  weaponComp = entity.getComponent(WeaponComponent.class);
 		
		if(inpComp.isLeftMouseButtonDown()){
			Ability ability = weaponComp.getPrimaryAbility();
			ability.useAbility(entity, inpComp.getMousePosition(), this.getWorld().getEntityManager());
		}

		int speedFactor = 2;
		if(inpComp.isGallopButtonPressed())
			speedFactor=4;

		Vector2 velocity = new Vector2(0,0);
		if (inpComp.isBackButtonPressed()){
			velocity=Vector2.add(velocity, new Vector2(0,1));
		}
		if (inpComp.isForwardButtonPressed()){
			velocity=Vector2.add(velocity, new Vector2(0,-1));
		}
		if (inpComp.isLeftButtonPressed()){
			velocity=Vector2.add(velocity, new Vector2(-1,0));
			posComp.setMirror(false);
		}
		if (inpComp.isRightButtonPressed()){
			velocity=Vector2.add(velocity, new Vector2(1,0));
			posComp.setMirror(true);
		}

		if(velocity.length()!=0)
			velocity=Vector2.norm(velocity);

		velocity = Vector2.mul(speedFactor, velocity);
		physComp.setVelocity(velocity);


	}

}
