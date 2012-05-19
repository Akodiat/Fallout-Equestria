package serverNetworkSystems;

import utils.Network;
import common.InputMessage;
import components.InputComp;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class ServerInputNetworkSystem extends ServerNetworkSystem {

	public ServerInputNetworkSystem(IEntityWorld world,
			Network server) {
		super(world, server);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected void received(com.esotericsoftware.kryonet.Connection connection, Object obj) {
		if(obj instanceof InputMessage) {
			InputMessage message = (InputMessage)obj;
			IEntity entity = this.getEntityManager().getEntity("Player" + message.networkID);
			
			if(entity != null) {
				InputComp comp = entity.getComponent(InputComp.class);
				comp.setKeyboard(message.keyboard);
				comp.setMouse(message.mouse);
			}
		}
		
	}
	
	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}

}
