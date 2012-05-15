package common;

import components.AnimationComp;
import content.ContentManager;

import entityFramework.*;

public class AnimationsNetworkSystem extends NetworkSystem<AnimationChangedMessage>{

	public AnimationsNetworkSystem(IEntityWorld world, EntityNetworkIDManager idManager, ContentManager contentManager) {
		super(AnimationChangedMessage.class, world, idManager, contentManager);
	}

	@Override
	public void process(AnimationChangedMessage message) {
		IEntity entity = this.idManager.getEntityFromNetworkID(message.changedEntityNetworkID);
		entity.getComponent(AnimationComp.class).changeAnimation(message.newAnimation, false); //TODO Should it be true instead?
	}	

}
