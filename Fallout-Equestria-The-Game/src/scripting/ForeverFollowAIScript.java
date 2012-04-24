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
public class ForeverFollowAIScript extends Behaviour{

	private PhysicsComp physComp;
	private TransformationComp transComp;

	@Editable
	public float speed = 100f;
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
			moveTowardsTarget(playerTrans.getPosition());
		}
	}

	private void moveTowardsTarget(Vector2 target) {
		Vector2 dir;
		dir = Vector2.subtract(target, this.transComp.getPosition());
		dir = Vector2.norm(dir);
		this.physComp.setVelocity(Vector2.mul(this.speed, dir));
		
		if (dir.X<0){
			this.entity.getComponent(TransformationComp.class).setMirror(true);
		} else {
			this.entity.getComponent(TransformationComp.class).setMirror(false);
		}
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new ForeverFollowAIScript();
	}

}
