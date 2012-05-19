package clientNetworkSystems;

import utils.Network;

import com.esotericsoftware.kryonet.Client;

import common.EntityDestroyedMessage;
import entityFramework.*;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class ClientEntityDestructionNetworkSystem extends ClientNetworkSystem<EntityDestroyedMessage>{

	public ClientEntityDestructionNetworkSystem(IEntityWorld world,
			EntityNetworkIDManager idManager, Network client) {
		super(world, EntityDestroyedMessage.class, idManager, client);
	}
	
	@Override
	public void processMessage(EntityDestroyedMessage message) {
		IEntity entity = this.IdManager.getEntityFromNetworkID(message.messageID);
		IdManager.removeNetworkIDEntity(message.messageID); 
		
		if(entity != null)
			entity.kill();
	}
}
