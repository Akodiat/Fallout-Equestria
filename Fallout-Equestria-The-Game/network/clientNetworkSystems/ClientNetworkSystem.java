package clientNetworkSystems;

import java.util.ArrayList;
import java.util.List;

import utils.Network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import common.NetworkMessage;

import entityFramework.EntityNetworkIDManager;
import entityFramework.EntitySystem;
import entityFramework.IComponent;
import entityFramework.IEntityWorld;
/**
 * 
 * @author Joakim Johansson
 *
 * @param <T> Generic variable indicating the type of network message that the system should handle.
 */
public abstract class ClientNetworkSystem<T extends NetworkMessage> extends EntitySystem{
	private final Object lock = new Object();
	private final Listener listener;
	private final Class<T> usingClass;
	
	protected final List<T> MessageList;
	private final Network network;	
	protected final EntityNetworkIDManager IdManager;
	
	protected Client getClient() {
		return this.network.getClient();
	}

	@SafeVarargs
	public ClientNetworkSystem(IEntityWorld world, Class<T> usingClass, EntityNetworkIDManager idManager, Network client,  Class<? extends IComponent> ... components ){
		super(world, components);
		
		this.usingClass = usingClass;
		this.IdManager = idManager;
		this.MessageList = new ArrayList<T>();
		this.network = client;
		
		this.listener = new Listener() {
				public void received(Connection connection, Object obj) {
					ClientNetworkSystem.this.recived(connection, obj);
				}
				public void connected(Connection connection) {
					ClientNetworkSystem.this.connected(connection);
				}
				public void disconnected(Connection connection) {
					ClientNetworkSystem.this.disconnected(connection);
				}
		};
		
		this.network.addListener(this.listener);
	}


	public void initialize() { }
	protected void connected(Connection connection) { }
	protected void disconnected(Connection connection) { }
	protected abstract void processMessage(T message);
	
	
	@SuppressWarnings("unchecked")
	protected void recived(Connection connection, Object obj) {
		synchronized (lock) {
			if(	obj.getClass().equals(ClientNetworkSystem.this.usingClass)){
				ClientNetworkSystem.this.MessageList.add(((T)obj));
			}
		}
	}
	
	public void process() {
		synchronized(lock) {
			for (T message : this.MessageList) {
				this.processMessage(message);
			}
			this.MessageList.clear();
		}
	}
}
