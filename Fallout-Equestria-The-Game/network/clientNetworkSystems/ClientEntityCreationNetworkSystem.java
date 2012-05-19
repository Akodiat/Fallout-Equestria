package clientNetworkSystems;

import utils.Network;

import com.esotericsoftware.kryonet.Client;
import common.EntityCreatedMessage;
import content.ContentManager;
import entityFramework.*;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class ClientEntityCreationNetworkSystem extends ClientNetworkSystem<EntityCreatedMessage>{

	private ContentManager contentManager;
	
	public ClientEntityCreationNetworkSystem(IEntityWorld world, EntityNetworkIDManager idManager, ContentManager contentManager, Network client) {
		super(world, EntityCreatedMessage.class, idManager, client);
		this.contentManager = contentManager;
		
	}

	@Override
	public void processMessage(EntityCreatedMessage message) {
		System.out.println("Entity Created: " + message.messageID);
		
		IEntityArchetype archetype = this.contentManager.loadArchetype(message.entityArchetypePath);
		IEntity entity = this.getWorld().getEntityManager().createEntity(archetype);
		entity.addComponent(message.transComp);

		IdManager.setNetworkIDToEntity(entity, message.messageID);
	}
}
