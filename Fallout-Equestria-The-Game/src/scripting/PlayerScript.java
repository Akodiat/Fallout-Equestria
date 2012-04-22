package scripting;

import math.Vector2;
import ability.AbilityInfo;
import anotations.Editable;

import components.AbilityComp;
import components.InputComp;
import components.PhysicsComp;
import components.TransformationComp;
import components.WeaponComp;

import utils.GameTime;
@Editable
public class PlayerScript extends BehaviourScript{
	
	PhysicsComp	  	        physComp;
	InputComp 		  	     inpComp;
	TransformationComp       posComp;
	AbilityComp 		      apComp;
	WeaponComp 	  		  weaponComp;
	
	@Override
	public void start() {
		physComp  = entity.getComponent(PhysicsComp.class);
		posComp  = entity.getComponent(TransformationComp.class);
		apComp  = entity.getComponent(AbilityComp.class);
		weaponComp  = entity.getComponent(WeaponComp.class);	

	}

	@Override
	public void update(GameTime time) {

		inpComp  = entity.getComponent(InputComp.class);

		int speedFactor = 200;
		if(inpComp.isGallopButtonPressed()){
			speedFactor=400;
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
	public BehaviourScript createNew() {
		return new PlayerScript();
	}

}
