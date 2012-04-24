package scripting;

import math.Vector2;
import anotations.Editable;

import components.AbilityComp;
import components.InputComp;
import components.PhysicsComp;
import components.TransformationComp;
import components.WeaponComp;
import entityFramework.IEntity;

import utils.GameTime;
import utils.MouseState;

@Editable
public class PlayerScript extends Behaviour{
	
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
	public void onMouseOver(MouseState state){
		System.out.println("Mouse over player!");
	}

	@Override
	public void onMouseEnter(MouseState state){
		System.out.println("Mouse just went over player!");
	}

	@Override
	public void onMouseExit(MouseState state){
		System.out.println("Mouse just left player area!");
	}

	@Override
	public void onMouseDown(MouseState state){
		System.out.println("Mouse was just pressed");
	}

	@Override
	public void onMouseUp(MouseState state){
		System.out.println("Mouse was just released");
	}
	
	@Override
	public void onMouseUpAsButton(MouseState state) {
		System.out.println("Mouse was just released as a button!");
	}
	
	@Override
	public void onMouseDrag(MouseState state){
		System.out.println("The mouse is getting draged!");
	}

	@Override
	public void onCollisionEnter(IEntity entity) {
		System.out.println("Player just collided with " + entity);
	}
		
	@Override
	public void onCollisionOver(IEntity entity) {
		System.out.println("Player is continusly colliding with " + entity);
	}

	@Override
	public void onCollisionExit(IEntity entity) {
		System.out.println("Player stoped colliding with " + entity);
	}
	
	@Override
	public Object clone() {
		return new PlayerScript();
	}

}
