package serverNetworkSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.esotericsoftware.kryonet.Connection;

import misc.EntityGroups;
import misc.IEventListener;

import entityFramework.EntityDestroyedListener;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entityFramework.IEntityWorld;
import com.esotericsoftware.kryonet.Server;
import common.EntityCreatedMessage;
import common.EntityDestroyedMessage;
import common.EntityEventArgs;
import common.NetworkedEntityFactory;
import common.NewPlayerMessage;
import components.TransformationComp;

public class ServerEntityCreationNetwork extends ServerNetworkSystem{

	private final Object lock = new Object();
	private final List<Object> messages;
	private final Map<Integer, EntityCreatedMessage> entityCreatedMessages;
	
	
	
	public ServerEntityCreationNetwork(IEntityWorld world, Server server) {
		super(world, server);
		this.entityCreatedMessages = new HashMap<>();
		this.messages = new ArrayList<>();
	}
	
	@Override
	public void initialize() {
		NetworkedEntityFactory factory = (NetworkedEntityFactory)this.getWorld().getEntityManager().getFactory();
		factory.addCreatedListener(new IEventListener<EntityEventArgs>() {
			public void onEvent(Object sender, EntityEventArgs e) {
				ServerEntityCreationNetwork.this.sendEntityCreated(e);
			}
		});
		
		IEntityManager manager = this.getWorld().getEntityManager();
		manager.addEntityDestoryedListener(new EntityDestroyedListener() {
			@Override
			public void entityDestroyed(IEntity entity) {
				ServerEntityCreationNetwork.this.sendEntityDestroyed(entity);
			}
		});
	}

	protected void sendEntityCreated(EntityEventArgs e) {
		if(e.getEntity().isInGroup(EntityGroups.Players.toString()))
			return;
				
		EntityCreatedMessage message = new EntityCreatedMessage();
		message.entityArchetypePath = e.getEntityArchetype();
		message.messageID = e.getEntity().getUniqueID();
		message.transComp = e.getEntity().getComponent(TransformationComp.class);
		this.entityCreatedMessages.put(message.messageID, message);
		this.addToMessages(message);
	}
	
	protected void sendEntityDestroyed(IEntity entity) {
		EntityDestroyedMessage message = new EntityDestroyedMessage();
		message.messageID = entity.getUniqueID();
		this.entityCreatedMessages.remove(entity.getUniqueID());
		this.addToMessages(message);
	}
	
	private void addToMessages(Object message) {
		synchronized (this.lock) {
			this.messages.add(message);
		}
	}

	@Override 
	protected void received(Connection connection, Object message) {
		if(message instanceof NewPlayerMessage) {
			this.sendAllCreatedEntities(connection);
		}
	}
	
	private void sendAllCreatedEntities(Connection connection) {
		for (EntityCreatedMessage message : this.entityCreatedMessages.values()) {
			connection.sendTCP(message);			
		}			
	}

	@Override
	public void process() {	
		synchronized (lock) {
			for (Object message : this.messages) {	
				this.Server.sendToAllTCP(message);
			}
			this.messages.clear();
		}		
	}

}
