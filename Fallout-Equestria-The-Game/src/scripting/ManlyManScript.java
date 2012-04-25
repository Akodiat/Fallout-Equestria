package scripting;

import utils.GameTime;
import utils.MouseState;
import animation.KeyframeTriggerEventArgs;
import anotations.Editable;
import components.*;

import math.Vector2;
import misc.EntityGroups;
import entityFramework.IEntity;

@Editable
public class ManlyManScript extends Behavior {
	
	private PhysicsComp physComp;
	private TransformationComp transComp;
	private AnimationComp animComp;
	
	private @Editable String explodeAnimationName = "explode";
	private @Editable String targetEntityLabel = "Player";
	private @Editable float moveSpeed = 100f;
	
	
	@Override
	public void start() {
		this.physComp = this.getComponent(PhysicsComp.class);
		this.transComp = this.getComponent(TransformationComp.class);
		this.animComp = this.getComponent(AnimationComp.class);
	}

	@Override
	public void update(GameTime time) {
		IEntity targetEntity = this.entityManager.getEntity(targetEntityLabel);
		if(targetEntity != null) {
			TransformationComp playerTrans = targetEntity.getComponent(TransformationComp.class);
			moveTowardsTarget(playerTrans.getPosition());
		}
	}
	
	private void moveTowardsTarget(Vector2 target) {
		Vector2 dir;
		dir = Vector2.subtract(target, this.transComp.getPosition());
		dir = Vector2.norm(dir);
		this.physComp.setVelocity(Vector2.mul(this.moveSpeed, dir));
		
		if (dir.X<0){
			this.entity.getComponent(TransformationComp.class).setMirror(true);
		} else {
			this.entity.getComponent(TransformationComp.class).setMirror(false);
		}
	}
	
	
	@Override
	public void onMouseOver(MouseState ms) {
		System.out.println("Mouse is over me " + this.entity);
	}
	
	@Override
	public Object clone() {
		return new ManlyManScript();
	}
	
	@Override
	public void onTriggerEnter(IEntity entity) {	
		if(entity.isInGroup(EntityGroups.Players.toString())) {
			explode();
		}
	}

	private void explode() {
		animComp.changeAnimation(explodeAnimationName, 0.1f);
		
		//Remove our behavior making us just sit on the screen.
		this.entity.removeComponent(PhysicsComp.class);
		this.entity.removeComponent(BehaviourComp.class);
		this.entity.addComponent(new ExistanceComp(10f));
		this.entity.refresh();
	}

}
