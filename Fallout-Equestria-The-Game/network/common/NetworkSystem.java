package common;

import java.util.ArrayList;
import java.util.List;

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
	private Object lock = new Object();
	private Listener listener;
	
	protected List<T> messageList;
	protected Class<T> usingClass;
	protected IEntityWorld world;
	protected EntityNetworkIDManager idManager;
	protected ContentManager contentManager;

	public NetworkSystem(Class<T> usingClass, IEntityWorld world, EntityNetworkIDManager idManager, ContentManager contentManager, Class<? extends IComponent> ... components ){
		super(world, components);
		
		this.usingClass = usingClass;
		this.world = world;
		this.idManager = idManager;
		this.contentManager = contentManager;
		this.messageList = new ArrayList<T>();
		
		this.listener = new Listener() {
			public void received(Connection connection, Object obj) {
				super.received(connection, obj);
				synchronized (lock) {
					if(obj.getClass().equals(usingClass)){
						this.process((T) obj);
					}
				}
			}
		}
	}
	
	

	public abstract void process(T message);
}
