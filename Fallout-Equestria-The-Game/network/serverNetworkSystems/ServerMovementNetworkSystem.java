package serverNetworkSystems;

import common.Network;
import common.messages.EntityMovedMessage;
import components.TransformationComp;
import entityFramework.ComponentMapper;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class ServerMovementNetworkSystem extends ServerNetworkSystem{
	
	public ServerMovementNetworkSystem(IEntityWorld world, Network server) {
		super(world, server, TransformationComp.class);
		
	}

	private ComponentMapper<TransformationComp> transCM;
	
	public void initialize() {
		transCM = ComponentMapper.create(getDatabase(), TransformationComp.class);
	}
	
	@Override
	public void process() {
		try {
		for (IEntity entity : this.getEntities().values()) {
			EntityMovedMessage message = new EntityMovedMessage();
			message.entityID = entity.getUniqueID();
			message.newTransfComp = this.transCM.getComponent(entity);
			
			this.getServer().sendToAllUDP(message);
		}			
		} catch(Exception e) {
			System.out.println("Failed but we ignore it since it's not important");
		}
	}

}
