package common;

import components.TransformationComp;
import content.ContentManager;

import entityFramework.EntityNetworkIDManager;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class MovementNetworkSystem extends NetworkSystem<EntityMovedMessage>{

	public MovementNetworkSystem(IEntityWorld world, EntityNetworkIDManager idManager, ContentManager contentManager) {
		super(EntityMovedMessage.class, world, idManager, contentManager);
	}

	@Override
	public void process(EntityMovedMessage message) {
		IEntity entity = this.idManager.getEntityFromNetworkID(message.entityID);
		if(entity != null) {
			entity.getComponent(TransformationComp.class).setAllFieldsToBeLike(message.newTransfComp);
		}	
	}

}
