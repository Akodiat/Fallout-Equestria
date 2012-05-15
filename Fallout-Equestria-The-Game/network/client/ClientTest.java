package client;

import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import math.Vector2;
import misc.SoundManager;

import animation.AnimationPlayer;
import animation.PonyColorChangeHelper;

import com.esotericsoftware.kryonet.*;

import common.*;
import components.*;

import scripting.PlayerScript;
import utils.*;
import common.EntityCreatedMessage;
import common.EntityDestroyedMessage;
import common.EntityMovedMessage;
import common.EntityNetworkIDsetMessage;
import common.InputMessage;
import common.NewPlayerMessage;
import components.AnimationComp;
import components.BehaviourComp;
import components.RenderingComp;
import components.ShadowComp;
import components.TransformationComp;

import scripting.PlayerScript;
import utils.Camera2D;
import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.Network;
import utils.Rectangle;
import demos.Demo;
import demos.WorldBuilder;
import entityFramework.*;
import entitySystems.CameraControlSystem;

public class ClientTest extends Demo {
	
	private static final Rectangle sr = new Rectangle(0,0, 800, 600);
	
	
	private Object lock = new Object();
	private ArrayList<Object> messages;
	private IEntityWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Scene scene;
	private Mouse mouse;
	private Keyboard keyboard;
	private SoundManager soundManager;
	private int networkID = -1;
	private Network network;
	
	private EntityNetworkIDManager idManager;
	
	public static void main(String[] args) {
		new ClientTest().start();
	}
	
	public ClientTest() {
		super(sr, 30);
		messages = new ArrayList<>();
		this.idManager = new EntityNetworkIDManager();
		this.network = new Network(54555, 54777);
	}

	@Override
	protected void end() {
		this.network.reset();
	}
	@Override
	public void update(GameTime time) {
		synchronized (lock) {
			for (Object value : this.messages) {
				if(value instanceof NewPlayerMessage) {
					NewPlayerMessage message = (NewPlayerMessage) value;
					System.out.println(message.networkID);
					createNewPlayer(message);				
				} else if(value instanceof EntityMovedMessage) {
					EntityMovedMessage message = (EntityMovedMessage) value;
					moveEntity(message);
				} else if(value instanceof AnimationChangedMessage) {
					AnimationChangedMessage message = (AnimationChangedMessage) value;
					//TODO Do something here... or don't
				} else if(value instanceof EntityCreatedMessage) {
					EntityCreatedMessage message = (EntityCreatedMessage) value;
					createNewEntity(message);
				} else if(value instanceof EntityDestroyedMessage) {
					EntityDestroyedMessage message = (EntityDestroyedMessage) value;
					removeEntity(message);
				}
			}			
			
			this.messages.clear();
		}
		
		this.mouse.poll(sr);
		this.keyboard.poll();
		sendInput();		
		
		
		this.gameWorld.getEntityManager().destoryKilledEntities();
		this.gameWorld.update(time);
	}

	private void createNewEntity(EntityCreatedMessage message) {
		IEntityArchetype archetype = this.ContentManager.loadArchetype(message.entityArchetypePath);
		IEntity entity = this.gameWorld.getEntityManager().createEntity(archetype);
		entity.addComponent(message.transComp);
		System.out.println(message.networkID);
		
		idManager.setNetworkIDToEntity(entity, message.networkID);
	}
	
	private void removeEntity(EntityDestroyedMessage message) {
		IEntity entity = idManager.getEntityFromNetworkID(message.entityUniqueID);
		idManager.removeNetworkIDEntity(entity);
		entity.kill();
	}

	private void moveEntity(EntityMovedMessage message) {
		IEntity entity = this.idManager.getEntityFromNetworkID(message.entityID);
		if(entity != null) {
			entity.getComponent(TransformationComp.class).setAllFieldsToBeLike(message.newTransfComp);
		}
	}

	private void sendInput() {
		InputMessage message = new InputMessage();
		message.networkID = this.networkID;
		message.keyboard = this.keyboard;
		message.mouse = this.mouse;
		
		Client client = this.network.getClient();
		client.sendTCP(message);
	}

	@Override
	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);
		this.spriteBatch.begin(null, this.camera.getTransformation());
		this.gameWorld.render();
		this.spriteBatch.end();	
	}


	@Override
	protected void initialize() {
		this.connect();
		System.out.println("awda");
			
		waitUntilIDISReal();
		
		this.createWorld();
		sendPlayerCreated();
	}
	
	private void waitUntilIDISReal() {
		while(true) {
			synchronized (this.lock) {
				if(this.networkID != -1) {
					return;
				}
			}
		}
		
	}

	private void sendPlayerCreated() {
		NewPlayerMessage message = new NewPlayerMessage();
		message.networkID = this.networkID;	
		message.senderID = this.networkID;
		
		message.playerCharacteristics = new PlayerCharacteristics();
		
		message.playerCharacteristics.bodyColor = Color.Black;
		message.playerCharacteristics.eyeColor  = Color.Cyan;
		message.playerCharacteristics.maneColor	= Color.Purple;
		Client client = this.network.getClient();
		client.sendTCP(message);		
		
	}

	private void createWorld() {
		this.scene = this.ContentManager.load("PerspectiveV5.xml", Scene.class);
		this.mouse = new Mouse();
		this.keyboard = new Keyboard();
		this.soundManager = new SoundManager(this.ContentManager, 0.1f,1.0f,1.0f);
		this.spriteBatch = new SpriteBatch(sr);
		this.camera = new Camera2D(scene.getWorldBounds(), sr);
		
		this.gameWorld = WorldBuilder.buildClientWorld(camera, scene, mouse, keyboard, ContentManager, this.soundManager, spriteBatch, false, "Player" + this.networkID);
		this.gameWorld.initialize();
	}
	

	protected void createNewPlayer(NewPlayerMessage message) {
		IEntityArchetype archetype = ContentManager.loadArchetype("Player.archetype");
		IEntity entity = this.gameWorld.getEntityManager().createEntity(archetype);
		entity.addComponent(new BehaviourComp(new PlayerScript()));
		entity.addComponent(new ShadowComp());
		entity.getComponent(TransformationComp.class).setPosition(1000,1000);	
		
		AnimationPlayer player = this.ContentManager.loadAnimationSet("rdset.animset");
		player.startAnimation("idle");
		AnimationComp comp = new AnimationComp(player);
		PonyColorChangeHelper.setBodyColor(message.playerCharacteristics.bodyColor, comp);
		PonyColorChangeHelper.setEyeColor( message.playerCharacteristics.eyeColor, comp);
		PonyColorChangeHelper.setManeColor(message.playerCharacteristics.maneColor, comp);
		
		System.out.println("Hello friend! " + message.networkID);
		
		entity.addComponent(comp);
		entity.setLabel("Player" + message.senderID);
		if(message.senderID == this.networkID)
			entity.addToGroup(CameraControlSystem.GROUP_NAME);
		
		
		entity.refresh();		
		
		this.idManager.setNetworkIDToEntity(entity, message.networkID);
	}
	
	private void connect() {
		List<InetAddress> adresses = this.network.getAvalibleLanHosts();
		System.out.println(adresses.get(0));
		
		
		Client client = this.network.getClient();
		client.addListener(this.generateListener());
		this.network.connectToHost(adresses.get(0));
	}
	
	private Listener generateListener() {
		return new Listener() {			
			@Override
			public void connected(Connection conection) {
				System.out.println("I got connected!");
			}
			
			@Override
			public void received(Connection connection, Object value) {
				synchronized (lock) {
					if(value instanceof EntityNetworkIDsetMessage) {
						EntityNetworkIDsetMessage message = (EntityNetworkIDsetMessage)value;
						networkID = message.networkID;		
					} else {
						messages.add(value);
					}
				}
			}
			
			@Override
			public void disconnected(Connection connection) {
				System.out.println("Disconnected!");
			}		
		};
	}


}
