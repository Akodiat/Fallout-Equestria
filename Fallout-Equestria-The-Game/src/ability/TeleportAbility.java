package ability;

import org.newdawn.slick.openal.Audio;

import components.AnimationComp;
import components.TransformationComp;
import content.ContentManager;

import math.Vector2;
import entityFramework.IEntity;
import entityFramework.IEntityManager;

public class TeleportAbility extends Ability{
	private Audio soundEffect;
	
	public TeleportAbility(int apCost, float cooldown, String soundFilePath) {
		super(apCost, cooldown);
		this.soundEffect = ContentManager.loadSound(soundFilePath);
	}

	@Override
	protected void use(IEntity sourceEntity, Vector2 targetPos, IEntityManager manager) {
	//	sourceEntity.getComponent(AnimationComp.class).setActiveAnimation(null);
		
		sourceEntity.getComponent(TransformationComp.class).setPosition(targetPos);
		
		//sourceEntity.getComponent(AnimationComp.class).setActiveAnimation(null);
		soundEffect.playAsSoundEffect(1.0f, 1.0f, false);
	}

}
