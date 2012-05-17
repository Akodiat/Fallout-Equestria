package serverNetworkSystems;

import com.esotericsoftware.kryonet.Server;

import common.EntityMovedMessage;
import components.TransformationComp;
import entityFramework.ComponentMapper;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class ServerMovementNetworkSystem extends ServerNetworkSystem{
	
	public ServerMovementNetworkSystem(IEntityWorld world, Server server) {
		super(world, server, TransformationComp.class);
		
	}

	private ComponentMapper<TransformationComp> transCM;
	
	public void initialize() {
		transCM = ComponentMapper.create(getDatabase(), TransformationComp.class);
	}
	
	@Override
	public void process() {
		for (IEntity entity : this.getEntities().values()) {
			EntityMovedMessage message = new EntityMovedMessage();
			message.entityID = entity.getUniqueID();
			message.newTransfComp = this.transCM.getComponent(entity);
			
			this.Server.sendToAllUDP(message);
		}			
	}

}
