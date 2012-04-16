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

public class PlayerScript extends Script{

	@Editable
	private float trololo = 10;

	@Editable
	private boolean grodanBoll = false;
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameTime time) {
		PhysicsComp	  	        physComp  = entity.getComponent(PhysicsComp.class);
		InputComp 		  	     inpComp  = entity.getComponent(InputComp.class);
		TransformationComp       posComp  = entity.getComponent(TransformationComp.class);
		AbilityComp 		      apComp  = entity.getComponent(AbilityComp.class);
		WeaponComp 	  		  weaponComp  = entity.getComponent(WeaponComp.class);
 		
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

	@Override
	public Script createNew() {
		return new PlayerScript();
	}

}
