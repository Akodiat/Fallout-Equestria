package common;

import components.AnimationComp;
import content.ContentManager;

import entityFramework.*;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class AnimationsNetworkSystem extends NetworkSystem<AnimationChangedMessage>{

	public AnimationsNetworkSystem(IEntityWorld world, EntityNetworkIDManager idManager, ContentManager contentManager) {
		super(AnimationChangedMessage.class, world, idManager, contentManager);
	}

	@Override
	public void process(AnimationChangedMessage message) {
		System.out.println(message.newAnimation);
		IEntity entity = this.idManager.getEntityFromNetworkID(message.changedEntityNetworkID);
		entity.getComponent(AnimationComp.class).changeAnimation(message.newAnimation, true); //TODO Should it be true instead?
	}	

}
