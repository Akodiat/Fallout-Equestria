package entitySystems;

import ability.AbilityInfo;
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
		super(world, InputComp.class, 
					 PhysicsComp.class,
					 AbilityComp.class,
					 TransformationComp.class,
					 WeaponComp.class);
	}
	
	@Override
	public void initialize() {
	}

	@Override
	protected void processEntity(IEntity entity) {
		PhysicsComp	  physComp   = entity.getComponent(PhysicsComp.class);
		InputComp 		  inpComp    = entity.getComponent(InputComp.class);
		TransformationComp    posComp    = entity.getComponent(TransformationComp.class);
		AbilityComp apComp     = entity.getComponent(AbilityComp.class);
		WeaponComp 	  weaponComp = entity.getComponent(WeaponComp.class);
 		
		if(inpComp.isLeftMouseButtonDown()){
			AbilityInfo ability = weaponComp.getPrimaryAbility();
			apComp.setAbility(ability);
		}

		int speedFactor = 2;
		if(inpComp.isGallopButtonPressed()){
			speedFactor=4;
		}

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
