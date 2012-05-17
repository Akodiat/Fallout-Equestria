package clientNetworkSystems;

import java.util.ArrayList;
import java.util.List;

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
	protected final Client client;	
	protected final EntityNetworkIDManager IdManager;

	@SafeVarargs
	public ClientNetworkSystem(IEntityWorld world, Class<T> usingClass, EntityNetworkIDManager idManager, Client client,  Class<? extends IComponent> ... components ){
		super(world, components);
		
		this.usingClass = usingClass;
		this.IdManager = idManager;
		this.MessageList = new ArrayList<T>();
		this.client = client;
		
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
		
		this.client.addListener(this.listener);
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
	
	public final void process() {
		synchronized(lock) {
			for (T message : this.MessageList) {
				this.processMessage(message);
			}
			this.MessageList.clear();
		}
	}

	protected void cleanup() {
		this.client.removeListener(this.listener);
	}
}
