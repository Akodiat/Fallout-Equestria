package ability;

import org.newdawn.slick.openal.Audio;

import components.AnimationComp;
import components.TransformationComp;
import content.ContentManager;

import math.Vector2;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;

public class TeleportAbility extends Ability{
	private Audio soundEffect;
	private final IEntityArchetype cloudArchetype;
	
	public TeleportAbility(int apCost, float cooldown, String soundFilePath, IEntityArchetype cloudArchetype) {
		super(apCost, cooldown);
		this.soundEffect = ContentManager.loadSound(soundFilePath);
		this.cloudArchetype = cloudArchetype;
	}

	@Override
	protected void use(IEntity sourceEntity, Vector2 targetPos, IEntityManager manager) {
	//	sourceEntity.getComponent(AnimationComp.class).setActiveAnimation(null);
		IEntity cloud1 = manager.createEntity(cloudArchetype);
		cloud1.getComponent(TransformationComp.class).setPosition(sourceEntity.getComponent(TransformationComp.class).getPosition());
		cloud1.refresh();
		
		sourceEntity.getComponent(TransformationComp.class).setPosition(targetPos);
		IEntity cloud2 = manager.createEntity(cloudArchetype);
		cloud2.getComponent(TransformationComp.class).setPosition(sourceEntity.getComponent(TransformationComp.class).getPosition());
		cloud2.refresh();
		//sourceEntity.getComponent(AnimationComp.class).setActiveAnimation(null);
		soundEffect.playAsSoundEffect(1.0f, 1.0f, false);
	}

}
