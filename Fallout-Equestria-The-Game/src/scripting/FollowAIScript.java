package scripting;

import javax.xml.crypto.dsig.Transform;

import anotations.Editable;
import math.MathHelper;
import math.Vector2;
import components.PhysicsComp;
import components.TransformationComp;
import entityFramework.IEntity;

import utils.GameTime;

@Editable
public class FollowAIScript extends Script{

	private PhysicsComp physComp;
	private TransformationComp transComp;
	
	@Editable
	public float speed = 50f;
	@Editable
	public float minDistance = 150f;
	@Editable
	public float sightRange = 300f;
	
	@Editable
	public String targetEntityLabel = "Player";
	
	@Override
	public void start() {
		this.physComp = this.entity.getComponent(PhysicsComp.class);
		this.transComp = this.entity.getComponent(TransformationComp.class);
		
		if(physComp == null) {
			this.physComp = new PhysicsComp();
			this.entity.addComponent(new PhysicsComp());
		}
		if(this.transComp == null) {
			throw new NullPointerException("transComp");
		}
	}

	@Override
	public void update(GameTime time) {
		IEntity targetEntity = this.entityManager.getEntity(targetEntityLabel);
		if(targetEntity != null) {
			TransformationComp playerTrans = targetEntity.getComponent(TransformationComp.class);
			if(targetInRange(playerTrans.getPosition())) {
				moveTowardsTarget(playerTrans.getPosition());
			} else {
				moveRandomly();
			}
		} else {
			moveRandomly();
		}
			
	}

	private boolean targetInRange(Vector2 targetPos) {
		return Vector2.distanceSquared(this.transComp.getPosition(), targetPos) < this.sightRange * this.sightRange;
	}

	private void moveRandomly() {
		
		double angle = this.physComp.getVelocity().angle() + (MathHelper.Tau / 40 - Math.random() * MathHelper.Tau / 20);
		
		Vector2 rndV = new Vector2(MathHelper.sin(angle),MathHelper.cos(angle));
		rndV = Vector2.mul(this.speed, rndV);
		this.physComp.setVelocity(rndV);
	}

	private void moveTowardsTarget(Vector2 target) {
		Vector2 dir;
		dir = Vector2.subtract(target, this.transComp.getPosition());
		
		if(dir.length() < minDistance) {
			this.physComp.setVelocity(Vector2.Zero);
		} else {
			dir = Vector2.norm(dir);
			this.physComp.setVelocity(Vector2.mul(this.speed, dir));
		}
	}

	@Override
	public Script createNew() {
		// TODO Auto-generated method stub
		return new FollowAIScript();
	}

}
