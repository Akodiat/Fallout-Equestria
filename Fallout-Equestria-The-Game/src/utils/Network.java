package utils;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import math.Vector2;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

import common.ChatMessage;
import common.EntityCreatedMessage;
import common.EntityDestroyedMessage;
import common.EntityMovedMessage;
import common.EntityNetworkIDsetMessage;
import common.InputMessage;
import common.NewPlayerMessage;
import common.PlayerCharacteristics;
import components.InputComp;
import entityFramework.IEntityArchetype;
import graphics.Color;

/**
 * s 
 * @author Joakim Johansson
 *
 */
public class Network {
	
	private final int tpcPort;
	private final int udpPort;
	private EndPoint endPoint;
	
	public Network(int tpcport, int udpPort) {
		this.tpcPort = tpcport;
		this.udpPort = udpPort;
	}
		
	public EndPoint getEndPoint() {
		return this.endPoint;		
	}
	
	public boolean isServer() {
		return this.endPoint instanceof Server;
	}
	public boolean isClient() {
		return this.endPoint instanceof Client;
	}
	
	public Server getServer() {
		if(this.isServer()) {
			return (Server)this.endPoint;
		} else {
			throw new RuntimeException("The network is not a server!");
		}
	}
	
	public Client getClient() {
		if(this.isClient()) {
			return (Client)this.endPoint;
		} else {
			throw new RuntimeException("The network is not a client");
		}
	}
	
	public void HostGame() {		
		reset();
		
		Server server = new Server();
		server.start();
		registerClasses(server);
		try {
			server.bind(tpcPort, udpPort);
		} catch (IOException e) {
			throw new RuntimeException("The server could not be started!", e);
		}
	}
	
	public void reset() {
		if(this.endPoint == null)
			return;
			
		if(this.endPoint instanceof Server) {
			Server server = (Server)this.endPoint;
			server.stop();
		} else {
			Client client = (Client)this.endPoint;
			client.stop();
		}
		
		this.endPoint = null;	
	}

	private void startClient() {		
		Client client = new Client();
		client.start();
		registerClasses(client);	
		this.endPoint = client;
	}
	
	public List<InetAddress> getAvalibleLanHosts() {
		if(!this.isClient()) {
			this.reset();
			this.startClient();
		}
		
		Client client = (Client)this.endPoint;
		return client.discoverHosts(this.udpPort, 100);
	}
	
	public void connectToHost(InetAddress address) {
		if(this.isClient()) {
			Client client = (Client)this.endPoint;
			try {
				client.connect(5000, address, tpcPort, udpPort);
			} catch (IOException e) {
				throw new RuntimeException("Failed to connect with the server", e);
			}
		} else {
			throw new RuntimeException("You are not a client so you cannot connect to a host!");
		}
	}
	
	public static void registerClasses(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Keys.class);
		kryo.register(Vector2.class);
		kryo.register(IEntityArchetype.class);
		
		kryo.register(InputComp.class);
		kryo.register(components.PhysicsComp.class);
		kryo.register(components.RenderingComp.class);
		kryo.register(components.TransformationComp.class);
		kryo.register(components.AbilityComp.class);
		kryo.register(components.AnimationComp.class);
		kryo.register(components.BehaviourComp.class);
		kryo.register(components.SpecialComp.class);
		kryo.register(components.SpatialComp.class);
		kryo.register(InputMessage.class);
		
		kryo.register(NewPlayerMessage.class);
		kryo.register(EntityNetworkIDsetMessage.class);
		kryo.register(EntityMovedMessage.class);
		kryo.register(EntityCreatedMessage.class);
		kryo.register(EntityDestroyedMessage.class);
		kryo.register(PlayerCharacteristics.class);
		kryo.register(Color.class);
		kryo.register(ChatMessage.class);
		kryo.register(utils.Keyboard.class);
		kryo.register(utils.ButtonState.class);
		kryo.register(utils.KeyboardKey.class);
		kryo.register(utils.Mouse.class);
		kryo.register(utils.MouseState.class);
		kryo.register(java.util.ArrayList.class);
	}
}
