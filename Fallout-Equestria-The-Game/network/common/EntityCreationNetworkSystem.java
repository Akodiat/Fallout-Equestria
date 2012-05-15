package common;

import content.ContentManager;
import entityFramework.*;

public class EntityCreationNetworkSystem extends NetworkSystem<EntityCreatedMessage>{

	public EntityCreationNetworkSystem(IEntityWorld world, EntityNetworkIDManager idManager, ContentManager contentManager) {
		super(EntityCreatedMessage.class, world, idManager, contentManager);
	}

	@Override
	public void process(EntityCreatedMessage message) {
		IEntityArchetype archetype = this.contentManager.loadArchetype(message.entityArchetypePath);
		IEntity entity = this.world.getEntityManager().createEntity(archetype);
		entity.addComponent(message.transComp);
		System.out.println(message.networkID);
		
		idManager.setNetworkIDToEntity(entity, message.networkID);
	}
}
