package common;

import components.HealthComp;

import content.ContentManager;
import entityFramework.EntityNetworkIDManager;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class HealthNetworkSystem extends NetworkSystem<HealthChangedMessage>{

	public HealthNetworkSystem(IEntityWorld world, EntityNetworkIDManager idManager,
			ContentManager contentManager) {
		super(HealthChangedMessage.class, world, idManager, contentManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(HealthChangedMessage message) {
		IEntity entity = idManager.getEntityFromNetworkID(message.entityUniqueID);
		entity.getComponent(HealthComp.class).addHealthPoints(message.deltaHealth);
	}

}
