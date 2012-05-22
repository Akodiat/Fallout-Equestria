package common;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import player.PlayerCharacteristics;

import utils.input.Keys;

import math.Vector2;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import common.messages.AnimationChangedMessage;
import common.messages.ChatMessage;
import common.messages.EntityCreatedMessage;
import common.messages.EntityDestroyedMessage;
import common.messages.EntityMovedMessage;
import common.messages.EntityNetworkIDsetMessage;
import common.messages.GoToScreenMessage;
import common.messages.InputMessage;
import common.messages.NewPlayerMessage;
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
	
	private List<Listener> addedListeners;
	
	public Network(int tpcport, int udpPort) {
		this.tpcPort = tpcport;
		this.udpPort = udpPort;
		this.addedListeners = new ArrayList<Listener>();
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
			this.endPoint = server;
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
		kryo.register(common.messages.SoundMessage.class);
		kryo.register(AnimationChangedMessage.class);
		kryo.register(NewPlayerMessage.class);
		kryo.register(EntityNetworkIDsetMessage.class);
		kryo.register(EntityMovedMessage.class);
		kryo.register(EntityCreatedMessage.class);
		kryo.register(EntityDestroyedMessage.class);
		kryo.register(GoToScreenMessage.class);
		kryo.register(PlayerCharacteristics.class);
		kryo.register(player.ManeStyle.class);
		kryo.register(player.SpecialStats.class);
		kryo.register(player.Race.class);
		kryo.register(Color.class);
		kryo.register(ChatMessage.class);
		kryo.register(utils.input.Keyboard.class);
		kryo.register(utils.input.ButtonState.class);
		kryo.register(utils.input.KeyboardKey.class);
		kryo.register(utils.input.Mouse.class);
		kryo.register(utils.input.MouseState.class);
		kryo.register(java.util.ArrayList.class);
		kryo.register(common.messages.HealthChangedMessage.class);
		
	}

	public void addListener(Listener listener) {
		if(this.isClient()) {
			((Client)this.endPoint).addListener(listener);
		} else if(this.isServer()) {
			((Server)this.endPoint).addListener(listener);
		} else {
			throw new RuntimeException("No Connection is avalible to add listeners to.");
		}
		this.addedListeners.add(listener);
	}
	
	public void removeListener(Listener listener) {
		if(this.isClient()) {
			((Client)this.endPoint).removeListener(listener);
		} else if(this.isServer()) {
			((Server)this.endPoint).removeListener(listener);
		} else {
			throw new RuntimeException("No Connection is avalible to remove listeners from.");
		}
	}
	
	public void removeAllListeners() {
		if(this.endPoint == null)
			return;
		
		for (Listener listener : this.addedListeners) {
			this.removeListener(listener);
		}
	}
}
