package behavior;

import animation.AnimationPlayer;
import anotations.Editable;
import math.MathHelper;
import math.Vector2;
import components.AnimationComp;
import components.PhysicsComp;
import components.RadiationComp;
import components.TransformationComp;
import entityFramework.ComponentMapper;
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
	private TransformationComp transComp;

	@Editable
	public float sightRange = 300f;

	@Editable
	public String targetGroup = "Players";

	@Override
	public void start() {
		this.transComp = this.Entity.getComponent(TransformationComp.class);
		this.animComp = this.Entity.getComponent(AnimationComp.class);

		if(animComp == null) {
			this.animComp = new AnimationComp(); 
			this.Entity.addComponent(new AnimationComp()); //TODO Add animComp instead?
		}
		if(this.transComp == null) {
			throw new NullPointerException("transComp");
		}
	}

	@Override
	public void update(GameTime time) {
		IEntity targetEntity = findNearestTarget(this.Entity.getComponent(TransformationComp.class).getPosition());
		if(targetEntity != null) {
			TransformationComp playerTrans = targetEntity.getComponent(TransformationComp.class);
			copyTargetAppearance(targetEntity);
		}

	}
	private IEntity findNearestTarget(Vector2 position){
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

	private void copyTargetAppearance(IEntity targetEntity) {

		AnimationPlayer animPlayer = targetEntity.getComponent(AnimationComp.class).getAnimationPlayer().clone();
		this.Entity.getComponent(AnimationComp.class).setAnimationPlayer(animPlayer);
	}


	@Override
	public Object clone() {
		return new ChangelingAIScript();
	}

}