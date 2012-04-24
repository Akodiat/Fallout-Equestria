package scripting;

import components.AttackComp;
import components.PhysicsComp;
import components.BehaviourComp;

import entityFramework.IEntity;

public class ManlyManOnHitScript extends ChangeAnimationOnHitScript {
	@Override
	public void onHit(IEntity targetEntity){
		super.onHit(targetEntity);
		this.entity.removeComponent(BehaviourComp.class);
		this.entity.removeComponent(PhysicsComp.class);
		this.entity.removeComponent(AttackComp.class);
		this.entity.refresh();
	}
}
