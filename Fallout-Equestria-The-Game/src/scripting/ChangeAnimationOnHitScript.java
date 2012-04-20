package scripting;

import anotations.Editable;
import components.AnimationComp;
import entityFramework.IEntity;

public class ChangeAnimationOnHitScript extends HitScript {

	@Editable
	private String animation;
	
	private AnimationComp animComp;
	
	@Override
	public void start() {
		this.animComp = this.entity.getComponent(AnimationComp.class);
	}

	@Override
	public HitScript createNew() {
		return new ChangeAnimationOnHitScript();
	}
	
	public void setAnimation(String animation){
		this.animation = animation;
	}

	@Override
	public void onHit(IEntity targetEntity) {
		animComp.getAnimationPlayer().startAnimation(animation);
	}

}
