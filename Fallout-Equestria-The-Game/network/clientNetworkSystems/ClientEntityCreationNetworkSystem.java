package clientNetworkSystems;

import java.util.ArrayList;
import java.util.List;

import common.Network;
import common.messages.EntityCreatedMessage;
import content.ContentManager;
import entityFramework.*;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class ClientEntityCreationNetworkSystem extends ClientNetworkSystem<EntityCreatedMessage>{

	private ContentManager contentManager;
	private List<EntityCreatedMessage> buffer;

	public ClientEntityCreationNetworkSystem(IEntityWorld world, EntityNetworkIDManager idManager, ContentManager contentManager, Network client) {
		super(world, EntityCreatedMessage.class, idManager, client);
		this.contentManager = contentManager;
		buffer = new ArrayList<>();
	}

	@Override
	public void processMessage(EntityCreatedMessage message) {
		System.out.println("Entity Created: " + message.messageID);

		if(this.IdManager.getEntityFromNetworkID(message.messageID) != null) {
			this.buffer.add(message);
			return;
		}
		this.createEntity(message);

	}
	private void createEntity(EntityCreatedMessage message){
		IEntityArchetype archetype = this.contentManager.loadArchetype(message.entityArchetypePath);
		IEntity entity = this.getWorld().getEntityManager().createEntity(archetype);
		entity.addComponent(message.transComp);

		IdManager.setNetworkIDToEntity(entity, message.messageID);
	}

	@Override
	public void process() {
		super.process();

		List<EntityCreatedMessage> removed = new ArrayList<>();

		for (EntityCreatedMessage message : this.buffer) {
			if(this.IdManager.getEntityFromNetworkID(message.messageID) == null){
				removed.add(message);
				this.createEntity(message);
			}
		}
		this.buffer.removeAll(removed);
	}
}
