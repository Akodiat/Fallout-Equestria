package serverNetworkSystems;

import common.Network;
import common.messages.HealthChangedMessage;
import components.HealthComp;

import entityFramework.ComponentMapper;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class ServerHealthNetworkSystem extends ServerNetworkSystem {

	public ServerHealthNetworkSystem(IEntityWorld world, Network server) {
		super(world, server, HealthComp.class);
		// TODO Auto-generated constructor stub
	}

	private ComponentMapper<HealthComp> healthCM;
	
	@Override
	public void initialize() {
		this.healthCM = ComponentMapper.create(getDatabase(),  HealthComp.class);
	}
	
	@Override
	public void process() {
		for (IEntity entity : this.getEntities().values()) {
			HealthChangedMessage message = new HealthChangedMessage();
			message.entityID = entity.getUniqueID();
			message.health = this.healthCM.getComponent(entity).getHealthPoints();
			this.getServer().sendToAllUDP(message);
		}
	}
}
