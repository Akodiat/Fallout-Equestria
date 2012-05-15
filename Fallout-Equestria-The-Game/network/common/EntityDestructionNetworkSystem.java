package common;

import content.ContentManager;
import entityFramework.*;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class EntityDestructionNetworkSystem extends NetworkSystem<EntityDestroyedMessage>{

	public EntityDestructionNetworkSystem(IEntityWorld world,
			EntityNetworkIDManager idManager, ContentManager contentManager) {
		super(EntityDestroyedMessage.class, world, idManager, contentManager);
	}

	@Override
	public void process(EntityDestroyedMessage message) {
		IEntity entity = idManager.getEntityFromNetworkID(message.entityUniqueID);
		idManager.removeNetworkIDEntity(entity);
		entity.kill();
	}

}
