package clientNetworkSystems;


import com.esotericsoftware.kryonet.Client;

import common.EntityMovedMessage;
import components.TransformationComp;

import entityFramework.EntityNetworkIDManager;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class ClientMovementNetworkSystem extends ClientNetworkSystem<EntityMovedMessage>{

	public ClientMovementNetworkSystem(IEntityWorld world, EntityNetworkIDManager idManager, Client client) {
		super(world, EntityMovedMessage.class, idManager, client);
	}

	@Override
	public void processMessage(EntityMovedMessage message) {
		IEntity entity = this.IdManager.getEntityFromNetworkID(message.entityID);
		if(entity != null) {
			entity.getComponent(TransformationComp.class).setAllFieldsToBeLike(message.newTransfComp);
		}	
	}
}
