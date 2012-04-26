package scripting;

import math.Vector2;
import ability.AbilityFactory;
import anotations.Editable;

import components.AbilityComp;
import components.BehaviourComp;
import components.InputComp;
import components.PhysicsComp;
import components.SpecialComp;
import components.TransformationComp;
import components.WeaponComp;
import entityFramework.IEntity;

import utils.GameTime;
import utils.MouseState;
/**
 * 
 * @author Lukas Kurtyan & Joakim Johansson
 *
 */
@Editable
public class PlayerScript extends Behavior{
	
	PhysicsComp	  	        physComp;
	InputComp 		  	     inpComp;
	TransformationComp       posComp;
	AbilityComp 		      apComp;
	WeaponComp 	  		  weaponComp;
	SpecialComp			 specialComp;
	InputComp			   inputComp;
	
	@Override
	public void start() {
		physComp  = entity.getComponent(PhysicsComp.class);
		posComp  = entity.getComponent(TransformationComp.class);
		apComp  = entity.getComponent(AbilityComp.class);
		weaponComp  = entity.getComponent(WeaponComp.class);
		specialComp = entity.getComponent(SpecialComp.class);
		inputComp	= entity.getComponent(InputComp.class);
	}

	@Override
	public void update(GameTime time) {

		inpComp  = entity.getComponent(InputComp.class);

		int speedFactor = 200;
		if(inpComp.isGallopButtonPressed()){
			speedFactor=400;
		}
		if(inpComp.isLeftMouseButtonDown()){
			AbilityFactory abilityFactory = new AbilityFactory(entityManager);
			Vector2 velocity = Vector2.norm(Vector2.subtract(inputComp.getMousePosition(),posComp.getPosition()));
			IEntity projectile = abilityFactory.createProjectile(
					this.posComp.getPosition(), 
					velocity, 
					this.weaponComp.getPrimaryArchetype(), 
					this.specialComp);
			
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

	@Override
	public Object clone() {
		return new PlayerScript();
	}

}
