package behavior;

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
import entityFramework.IEntity;

import utils.input.Keyboard;
import utils.input.Keys;
import utils.input.Mouse;
import utils.input.MouseButton;
import utils.time.GameTime;
/**
 * 
 * @author Lukas Kurtyan & Joakim Johansson
 *
 */
@Editable
public class PlayerScript extends Behavior{
	private static final String IDLE_STATE = "IDLE_STATE";
	private static final String WALK_STATE = "WALK_STATE";
	private static final String JUMP_STATE = "JUMP_STATE";
	private static final float JUMP_VELO = 600f;
	
	PhysicsComp	  	        physComp;
	InputComp 		  	     inpComp;
	TransformationComp       posComp;
	SpecialComp			 specialComp;
	InputComp			   inputComp;
	AbilityComp 			  apComp;
	AnimationComp			animComp;
	
	
	private BulletAbility bulletAbility;
	
	@Override
	public Object clone() {
		return new PlayerScript();
	}
	
	@Override
	public void start() {
		physComp  = Entity.getComponent(PhysicsComp.class);
		posComp  = Entity.getComponent(TransformationComp.class);
		specialComp = Entity.getComponent(SpecialComp.class);
		inputComp	= Entity.getComponent(InputComp.class);
		apComp      = Entity.getComponent(AbilityComp.class);
		animComp    = Entity.getComponent(AnimationComp.class);
		
				
		this.StateMachine.registerState(IDLE_STATE, new IdleState());
		this.StateMachine.registerState(WALK_STATE, new WalkState());
		this.StateMachine.registerState(JUMP_STATE, new JumpState());
		this.StateMachine.changeState(IDLE_STATE);
		
		this.animComp.changeAnimation("idle", false);
		this.bulletAbility = new MachineBullet(this.ContentManager.loadArchetype("Bullet.archetype"), 1000, new Vector2(160, -20), 0.3f);
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
		

		updateVelo();
		super.update(time);
	}
	
	
	private void updateVelo() {
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
	}
	
	private void jump() {
		this.physComp.setHeightVelocity(JUMP_VELO);
		this.StateMachine.changeState(JUMP_STATE);
	}
	
		
	
	private class IdleState extends BehaviourState {
		@Override
		public void enter() {
			animComp.changeAnimation("idle", false );
		}

		@Override
		public void update(GameTime time) {
			Vector2 velocity = physComp.getVelocity();
			
			if(!velocity.equals(Vector2.Zero)) {
				StateMachine.changeState(WALK_STATE);
			} 	
			Keyboard keyboard = inputComp.getKeyboard();
			if(keyboard.wasKeyPressed(Keys.Space)) {
				jump();
			}		
		}

	}
	
	private class WalkState extends BehaviourState {
		@Override
		public void enter() {
			animComp.changeAnimation("walk", false);	
		}

		@Override
		public void update(GameTime time) {
			Vector2 velocity = physComp.getVelocity();
			if(velocity.equals(Vector2.Zero)) {
				System.out.println("idle");
				StateMachine.changeState(IDLE_STATE);
			}		
			Keyboard keyboard = inputComp.getKeyboard();
			if(keyboard.wasKeyPressed(Keys.Space)) {
				jump();
			}
		}
	}
	
	private class JumpState extends BehaviourState {
		@Override
		public void enter() {
			animComp.changeAnimation("jump", false);
		}

		@Override
		public void onGroundCollision() {
			if(physComp.getVelocity().equals(Vector2.Zero)) {
				StateMachine.changeState(IDLE_STATE);
			} else {
				StateMachine.changeState(WALK_STATE);
			}
		}
		
		@Override
		public void onTriggerEnter(IEntity other) {
			if(physComp.getHeightVelocity() < 0) {
				SoundManager.playSoundEffect("effects/boing.ogg");
				animComp.changeAnimation("jump", true);
				physComp.setHeightVelocity(600.0f);
			}
		}
	}
	
	@Override
	public void onEnable() {
		this.StateMachine.changeState(IDLE_STATE);
	}
	
	@Override
	public void onDisable() {
		this.StateMachine.changeState(IDLE_STATE);
		this.physComp.setVelocity(Vector2.Zero);
		this.apComp.stopAbility(bulletAbility);
	}

}
