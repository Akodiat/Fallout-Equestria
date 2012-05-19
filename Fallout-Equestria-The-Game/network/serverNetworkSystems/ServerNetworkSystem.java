package serverNetworkSystems;

import utils.Network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import entityFramework.EntitySystem;
import entityFramework.IComponent;
import entityFramework.IEntityWorld;

public abstract class ServerNetworkSystem  extends EntitySystem {

	private final Listener listener;	
	private final Network network;
	
	protected Server getServer() {
		return this.network.getServer();
	}
	
	@SafeVarargs
	protected ServerNetworkSystem(IEntityWorld world, Network network, Class<? extends IComponent>... componentsClasses) {
		super(world, componentsClasses);
		this.network = network;
		this.listener = new Listener() {
			public void connected(Connection connection) {
				ServerNetworkSystem.this.connected(connection);
			}
			public void received(Connection connection, Object obj) {
				ServerNetworkSystem.this.received(connection, obj);
			}
			public void disconnected(Connection connection) {
				ServerNetworkSystem.this.disconnected(connection);
			}			
		};
		this.network.addListener(listener);		
	}

	@Override
	public void initialize() {	}	
	protected void received(Connection connection, Object obj) { }
	protected void disconnected(Connection connection) {}
	protected void connected(Connection connection) {}


}
