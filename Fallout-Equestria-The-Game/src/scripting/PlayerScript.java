package scripting;

import math.Vector2;
import ability.BulletAbility;
import ability.MachineBullet;
import anotations.Editable;

import components.AbilityComp;
import components.AnimationComp;
import components.InputComp;
import components.PhysicsComp;
import components.SpecialComp;
import components.TransformationComp;

import utils.GameTime;
import utils.Keyboard;
import utils.Keys;
import utils.Mouse;
import utils.MouseButton;
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
	SpecialComp			 specialComp;
	InputComp			   inputComp;
	AbilityComp 			  apComp;
	AnimationComp			animComp;
	
	
	private BulletAbility bulletAbility;
	private PlayerState activeState;
	private float gravity = 1200.0f;
	
	@Override
	public void start() {
		physComp  = Entity.getComponent(PhysicsComp.class);
		posComp  = Entity.getComponent(TransformationComp.class);
		specialComp = Entity.getComponent(SpecialComp.class);
		inputComp	= Entity.getComponent(InputComp.class);
		apComp      = Entity.getComponent(AbilityComp.class);
		animComp    = Entity.getComponent(AnimationComp.class);
		
		this.activeState = new IdleState();
		this.activeState.enter();
		
		this.bulletAbility = new MachineBullet(this.ContentManager.loadArchetype("Bullet.archetype"), 500, new Vector2(160, -20), 0.3f);
		this.bulletAbility.initialize(EntityManager, this.Entity);
	}
	

	@Override
	public void update(GameTime time) {
		inpComp  = Entity.getComponent(InputComp.class);
		Mouse mouse = inpComp.getMouse();
		
	
		if(mouse.wasButtonPressed(MouseButton.Left)){
			this.apComp.startAbility(bulletAbility);			
		} else if(inpComp.getMouse().wasButtonReleased(MouseButton.Left)){
			this.apComp.stopAbility(bulletAbility);
		}
		
		this.activeState.update(time);
		
	}
	
	private Vector2 updateVelo() {
		Keyboard keyboard = inputComp.getKeyboard();

		int speedFactor = 200;
		if(keyboard.isKeyDown(inpComp.getGallopButton())){
			speedFactor=400;
		}
		
		Vector2 velocity = new Vector2(0,0);
		if (keyboard.isKeyDown(inpComp.getBackButton())){
			velocity=Vector2.add(velocity, new Vector2(0,1));
		}
		if (keyboard.isKeyDown(inpComp.getForwardButton())){
			velocity=Vector2.add(velocity, new Vector2(0,-1));
		}
		if (keyboard.isKeyDown(inpComp.getLeftButton())){
			velocity=Vector2.add(velocity, new Vector2(-1,0));
			posComp.setMirror(false);
		}
		if (keyboard.isKeyDown(inpComp.getRightButton())){
			velocity=Vector2.add(velocity, new Vector2(1,0));
			posComp.setMirror(true);
		}
		


		if(velocity.length()!=0)
			velocity=Vector2.norm(velocity);

		velocity = Vector2.mul(speedFactor, velocity);
		physComp.setVelocity(velocity);
		
		return velocity;
	}

	@Override
	public Object clone() {
		return new PlayerScript();
	}
	
	private abstract class PlayerState {
		public abstract void enter();
		public abstract void update(GameTime time);
		public abstract void exit();
	}
	
	private class IdleState extends PlayerState {
		@Override
		public void enter() {
			animComp.changeAnimation("idle");
		}

		@Override
		public void update(GameTime time) {
			Vector2 velocity = updateVelo();
			
			if(!velocity.equals(Vector2.Zero)) {
				activeState = new WalkState();
				activeState.enter();
			} 
			
			Keyboard keyboard = inputComp.getKeyboard();
			if(keyboard.wasKeyPressed(Keys.Space)) {
				physComp.setHeightVelocity(600.0f);
				activeState = new JumpState();
				activeState.enter();
			}
			
			
		}

		@Override
		public void exit() {
			// TODO Auto-generated method stub
			
		}	
	}
	
	private class WalkState extends PlayerState {

		@Override
		public void enter() {
			animComp.changeAnimation("walk");			
		}

		@Override
		public void update(GameTime time) {
			Vector2 velocity = updateVelo();
			if(velocity.equals(Vector2.Zero)) {
				activeState = new IdleState();
				activeState.enter();
			}
			
			Keyboard keyboard = inputComp.getKeyboard();
			if(keyboard.wasKeyPressed(Keys.Space)) {
				physComp.setHeightVelocity(600.0f);
				activeState = new JumpState();
				activeState.enter();
			}
			
		}
		@Override
		public void exit() {
			// TODO Auto-generated method stub
			
		}
	}
	
	private class JumpState extends PlayerState {
		@Override
		public void enter() {
			animComp.changeAnimation("jump");
		}

		@Override
		public void update(GameTime time) {
			Vector2 velo = updateVelo();
			if(posComp.getHeight() <= 0) {
				posComp.setHeight(0);
				physComp.setHeightVelocity(0);
				if(velo.equals(Vector2.Zero)) {
					activeState = new IdleState();
					activeState.enter();
				} else {
					activeState = new WalkState();
					activeState.enter();
				}
				return;
			}

			physComp.setHeightVelocity( physComp.getHeightVelocity() - 
										gravity * (float)time.getElapsedTime().getTotalSeconds());
		}

		@Override
		public void exit() {
			// TODO Auto-generated method stub
			
		}
		
	}

}
