package clientNetworkSystems;

import com.esotericsoftware.kryonet.Client;
import common.AnimationChangedMessage;
import components.AnimationComp;
import entityFramework.*;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class ClientAnimationsNetworkSystem extends ClientNetworkSystem<AnimationChangedMessage>{

	public ClientAnimationsNetworkSystem(IEntityWorld world, EntityNetworkIDManager idManager, Client client) {
		super(world, AnimationChangedMessage.class, idManager, client);
	}

	@Override
	public void processMessage(AnimationChangedMessage message) {
		IEntity entity = this.IdManager.getEntityFromNetworkID(message.messageID);
		if(entity != null)
			entity.getComponent(AnimationComp.class).changeAnimation(message.newAnimation, true); 
	}


}
