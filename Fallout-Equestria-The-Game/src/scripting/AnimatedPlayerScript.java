package scripting;

import math.MathHelper;
import math.Vector2;
import anotations.Editable;

import components.AbilityComp;
import components.AnimationComp;
import components.InputComp;
import components.PhysicsComp;
import components.SpecialComp;
import components.TransformationComp;

import utils.GameTime;
/**
 * 
 * @author Lukas Kurtyan & Joakim Johansson & Gustav Alm Rosenblad
 *
 */
@Editable
public class AnimatedPlayerScript extends Behavior{
	
	PhysicsComp	  	        physComp;
	InputComp 		  	     inpComp;
	TransformationComp       posComp;
	AbilityComp 		      apComp;
	SpecialComp			 specialComp;
	InputComp			   inputComp;
	AnimationComp			animComp;
	
	@Override
	public void start() {
		physComp  	= Entity.getComponent(PhysicsComp.class);
		posComp  	= Entity.getComponent(TransformationComp.class);
		apComp  	= Entity.getComponent(AbilityComp.class);
		specialComp = Entity.getComponent(SpecialComp.class);
		inputComp	= Entity.getComponent(InputComp.class);
		animComp	= Entity.getComponent(AnimationComp.class);
	}

	@Override
	public void update(GameTime time) {

		inpComp  = Entity.getComponent(InputComp.class);

		int speedFactor = 200;
		if(inpComp.isGallopButtonPressed()){
			speedFactor=400;
		}
		if(inpComp.isLeftMouseButtonDown()){
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
		
		boolean isMoving = !(MathHelper.roughlyEquals(velocity.X, 0f, 0.01f) && MathHelper.roughlyEquals(velocity.Y, 0f, 0.01f));
		if(speedFactor>=400 && animComp.getAnimationPlayer().getCurrentAnimation() != "lifted"){
			animComp.changeAnimation("lifted", 0.001f);
		}else if (speedFactor< 400 && !isMoving && animComp.getAnimationPlayer().getCurrentAnimation() != "idle")
			animComp.changeAnimation("idle", 0.001f);
		else if (speedFactor< 400 && isMoving && animComp.getAnimationPlayer().getCurrentAnimation() != "walk")
			animComp.changeAnimation("walk", 0.001f);
		
		
	}

	@Override
	public Object clone() {
		return new AnimatedPlayerScript();
	}

}
