package behavior;

import java.util.List;

import ability.BulletAbility;
import ability.MachineBullet;
import animation.AnimationPlayer;
import anotations.Editable;
import math.MathHelper;
import math.Vector2;
import components.AbilityComp;
import components.AnimationComp;
import components.PhysicsComp;
import components.TransformationComp;
import entityFramework.IEntity;

import utils.time.GameTime;
/**
 * 
 * @author Joakim Johansson
 *
 */
@Editable
public class ChangelingAIScript extends Behavior{

	private AnimationComp animComp;
	private PhysicsComp physComp;
	private TransformationComp transComp;
	private AbilityComp abComp;
	
	private BulletAbility bulletAbility;

	@Editable
	public float sightRange = 300f;
	@Editable
	public float speed = 50f;
	@Editable
	public float minDistance = 150f;

	@Editable
	public String targetGroup = "Players";

	@Override
	public void start() {
		this.transComp = this.Entity.getComponent(TransformationComp.class);
		this.animComp = this.Entity.getComponent(AnimationComp.class);

		if(animComp == null) {
			this.animComp = new AnimationComp(); 
			this.Entity.addComponent(this.animComp);
		}
		if(this.transComp == null) {
			throw new NullPointerException("transComp");
		}
		if(physComp == null) {
			this.physComp = new PhysicsComp();
			this.Entity.addComponent(this.physComp);
		}
		if(abComp == null) {
			this.abComp = new AbilityComp();
			this.Entity.addComponent(this.abComp);
		}
		
		this.bulletAbility = new MachineBullet(this.ContentManager.loadArchetype("Bullet.archetype"), 1000, 0.3f);
		this.bulletAbility.initialize(EntityManager, this.Entity);
	}

	@Override
	public void update(GameTime time) {
		IEntity targetEntity = findRandomTarget();
		if(targetEntity != null) {
			copyTargetAppearance(targetEntity);
		}
		
		targetEntity = findNearestTarget();
		if(targetEntity != null) {
			moveTowardsTarget(targetEntity.getComponent(TransformationComp.class).getPosition());
		}
	}
	
	private IEntity findNearestTarget(){
		Vector2 position = this.Entity.getComponent(TransformationComp.class).getPosition();
		
		IEntity nearestTarget = null;
		//		ComponentMapper<TransformationComp> tCM;
		//		tCM = ComponentMapper.create(NO DATABASE), TransformationComp.class);

		for (IEntity player : this.EntityManager.getEntityGroup(this.targetGroup)) {
			if(Vector2.distance(position, player.getComponent(TransformationComp.class).getPosition()) < this.sightRange){
				if (nearestTarget == null ||
						Vector2.distance(position, player.getComponent(TransformationComp.class).getPosition()) < 
						Vector2.distance(position, nearestTarget.getComponent(TransformationComp.class).getPosition())){
					nearestTarget = player;
				}
			}
		}
		return nearestTarget;
	}
	private IEntity findRandomTarget(){
		Vector2 position = this.Entity.getComponent(TransformationComp.class).getPosition();
		
		List<IEntity> list = this.EntityManager.getEntityGroup(this.targetGroup).asList();
		if(list.isEmpty())
			return null;
		
		IEntity entity = list.get((int) Math.random()*list.size());
		if(Vector2.distance(position, entity.getComponent(TransformationComp.class).getPosition()) > this.sightRange){
			return null;
		}
		return entity;
	}

	private void copyTargetAppearance(IEntity targetEntity) {
		AnimationPlayer animPlayer = targetEntity.getComponent(AnimationComp.class).getAnimationPlayer().clone();
		this.Entity.getComponent(AnimationComp.class).setAnimationPlayer(animPlayer);
		this.Entity.getComponent(TransformationComp.class).setScale(Vector2.One);
		this.Entity.getComponent(TransformationComp.class).setRotation(0f);
	}
	
	private void moveRandomly() {

		double angle = this.physComp.getVelocity().angle() + (MathHelper.Tau / 40 - Math.random() * MathHelper.Tau / 20);

		Vector2 rndV = new Vector2(MathHelper.sin(angle),MathHelper.cos(angle));
		rndV = Vector2.mul(this.speed, rndV);
		this.physComp.setVelocity(rndV);
	}
	
	private void moveTowardsTarget(Vector2 target) {
		Vector2 position = this.transComp.getPosition();
		Vector2 dir = Vector2.subtract(target, position);
		
		if((target.Y - position.Y) <= 0)
			dir = Vector2.add(Vector2.norm(dir), Vector2.UnitY);
		
		if(dir.length() < minDistance) {
			this.physComp.setVelocity(Vector2.Zero);
			this.abComp.startAbility(bulletAbility);
		} else {
			dir = Vector2.norm(dir);
			this.physComp.setVelocity(Vector2.mul(this.speed, dir));
			this.abComp.stopActiveAbility();
		}
		if(this.physComp.getVelocity().X !=0){
			this.transComp.setMirror(this.physComp.getVelocity().X > 0);
		}
	}

	@Override
	public Object clone() {
		return new ChangelingAIScript();
	}

	
	private class Idlestate extends BehaviourState{
		@Override
		public void update(GameTime time) {
			IEntity targetEntity = findRandomTarget();
			if(targetEntity != null) {
				copyTargetAppearance(targetEntity);
			}
		}
	}
}