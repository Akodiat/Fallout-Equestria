package scripting;

import math.Vector2;
import utils.GameTime;
import ability.AbilityInfo;
import anotations.Editable;

import components.AbilityComp;
import components.InputComp;
import components.PhysicsComp;
import components.TransformationComp;
import components.WeaponComp;

@Editable
public class TestScript extends Behavior{

	@Editable
	private float trololo = 10;

	@Editable
	private boolean granBoll234 = false;
	
	

	PhysicsComp	  	        physComp;
	InputComp 		  	     inpComp;
	TransformationComp       posComp;
	AbilityComp 		      apComp;
	WeaponComp 	  		  weaponComp;
	
	@Override
	public void start() {
		physComp  = entity.getComponent(PhysicsComp.class);
		inpComp  = entity.getComponent(InputComp.class);
		posComp  = entity.getComponent(TransformationComp.class);
		apComp  = entity.getComponent(AbilityComp.class);
		weaponComp  = entity.getComponent(WeaponComp.class);	
	}

	@Override
	public void update(GameTime time) {
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
	public Object clone() {
		return new PlayerScript();
	}

}