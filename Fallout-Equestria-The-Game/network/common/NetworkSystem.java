package common;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Endpoint;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import content.ContentManager;

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
public abstract class NetworkSystem<T extends NetworkMessage> extends EntitySystem{
	private final Object lock = new Object();
	private final Listener listener;
	
	protected List<T> messageList;
	protected Class<T> usingClass;
	protected IEntityWorld world;
	protected EntityNetworkIDManager idManager;
	protected ContentManager contentManager;

	@SafeVarargs
	public NetworkSystem(Class<T> usingClass, IEntityWorld world, EntityNetworkIDManager idManager, ContentManager contentManager, Class<? extends IComponent> ... components ){
		super(world, components);
		
		this.usingClass = usingClass;
		this.world = world;
		this.idManager = idManager;
		this.contentManager = contentManager;
		this.messageList = new ArrayList<T>();
		
		
		this.listener = new Listener() {
			public void received(Connection connection, Object obj) {
				NetworkSystem.this.recived(connection, obj);
			}
			public void connected(Connection connection) {
				NetworkSystem.this.connected(connection);
			}
			public void disconnected(Connection connection) {
				NetworkSystem.this.disconnected(connection);
			}
		};
	}


	@Override
	public void initialize() { }

	protected void connected(Connection connection) { }
	protected void disconnected(Connection connection) { }


	@SuppressWarnings("unchecked")
	protected void recived(Connection connection, Object obj) {
		synchronized (lock) {
			if(	obj.getClass().equals(NetworkSystem.this.usingClass)){
				NetworkSystem.this.messageList.add(((T)obj));
			}
		}
	}
	
	public final void process() {
		synchronized(lock) {
			for (T message : this.messageList) {
				this.process(message);
			}
			this.messageList.clear();
		}
	}

	public abstract void process(T message);

	public Listener getListener() {
		return listener;
	}
}
