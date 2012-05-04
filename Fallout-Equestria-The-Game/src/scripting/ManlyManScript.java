package scripting;

import utils.GameTime;
import utils.MouseState;
import anotations.Editable;
import components.*;

import math.Vector2;
import misc.EntityGroups;
import misc.IEventListener;
import misc.Timer;
import misc.TimerEventArgs;
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
		IEntity targetEntity = this.EntityManager.getEntity(targetEntityLabel);
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
			this.Entity.getComponent(TransformationComp.class).setMirror(true);
		} else {
			this.Entity.getComponent(TransformationComp.class).setMirror(false);
		}
	}
	
	
	@Override
	public void onMouseOver(MouseState ms) {
		System.out.println("Mouse is over me " + this.Entity);
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
		this.Entity.removeComponent(PhysicsComp.class);
		this.Entity.removeComponent(BehaviourComp.class);
		TimerComp comp = new TimerComp();
		this.Entity.addComponent(comp);
		
		Timer timer = new Timer(1,10f);
		timer.addCompleteListener(new IEventListener<TimerEventArgs>() {
			@Override
			public void onEvent(Object sender, TimerEventArgs e) {
				destroyEntity();
			}
		});
		comp.addTimer(timer);
		this.Entity.refresh();
	}

	protected void destroyEntity() {
		this.Entity.kill();
	}

}
