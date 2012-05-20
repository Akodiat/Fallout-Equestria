package clientNetworkSystems;




import common.Network;
import common.messages.EntityMovedMessage;
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

	public ClientMovementNetworkSystem(IEntityWorld world, EntityNetworkIDManager idManager, Network client) {
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
