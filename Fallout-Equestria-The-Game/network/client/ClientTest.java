package client;

import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;

import java.io.IOException;
import java.util.ArrayList;

import math.Vector2;
import misc.SoundManager;

import animation.AnimationPlayer;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import common.EntityCreatedMessage;
import common.EntityDestroyedMessage;
import common.EntityMovedMessage;
import common.EntityNetworkIDsetMessage;
import common.InputMessage;
import common.Network;
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
import utils.Rectangle;
import demos.Demo;
import demos.WorldBuilder;
import entityFramework.EntityNetworkIDManager;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityWorld;
import entitySystems.CameraControlSystem;

public class ClientTest extends Demo {
	
	private static final Rectangle sr = new Rectangle(0,0, 800, 600);
	private Client client;
	
	
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
	private EntityNetworkIDManager idManager;
	
	public static void main(String[] args) {
		new ClientTest().start();
	}
	
	public ClientTest() {
		super(sr, 30);
		messages = new ArrayList<>();
		this.idManager = new EntityNetworkIDManager();
	}

	@Override
	protected void end() {
		client.stop();
	}
	@Override
	public void update(GameTime time) {
		synchronized (lock) {
			
			//System.out.println(this.messages.size());
			for (Object value : this.messages) {
				if(value instanceof NewPlayerMessage) {
					NewPlayerMessage message = (NewPlayerMessage)value;
					createNewPlayer(message);				
				} else if(value instanceof EntityMovedMessage) {
					EntityMovedMessage message = (EntityMovedMessage)value;
					moveEntity(message);
				} else if(value instanceof EntityCreatedMessage) {
					EntityCreatedMessage message = (EntityCreatedMessage)value;
					createNewEntity(message);
				} else if(value instanceof EntityDestroyedMessage) {
					EntityDestroyedMessage message = (EntityDestroyedMessage)value;
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
		idManager.setNetworkIDToEntity(entity, message.networkID);
	}
	
	private void removeEntity(EntityDestroyedMessage message) {
		IEntity entity = idManager.getEntityFromNetworkID(message.entityUniqueID);
		entity.kill();
		idManager.removeNetworkIDEntity(entity);
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
		client = new Client();
		this.connect();
			
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
		System.out.println("mw" + this.networkID);
		
		this.client.sendTCP(message);		
		
	}

	private void createWorld() {
		this.scene = this.ContentManager.load("PerspectiveV5.xml", Scene.class);
		this.mouse = new Mouse();
		this.keyboard = new Keyboard();
		this.soundManager = new SoundManager(this.ContentManager, 0.1f,1.0f,1.0f);
		this.spriteBatch = new SpriteBatch(sr);
		this.camera = new Camera2D(scene.getWorldBounds(), sr);
		
		this.gameWorld = WorldBuilder.buildClientWorld(camera, scene, mouse, keyboard, ContentManager, this.soundManager, spriteBatch, true, "Player" + this.networkID);
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
		comp.setTint(Color.Green);
				
		
		entity.addComponent(comp);
		entity.setLabel("Player" + message.senderID);
		if(message.senderID == this.networkID)
			entity.addToGroup(CameraControlSystem.GROUP_NAME);
		entity.refresh();		
		
		this.idManager.setNetworkIDToEntity(entity, message.networkID);
	}
	
	private void connect() {
		try {
			this.client.addListener(this.generateListener());
			client.start();
			Network.registerClasses(client);
			this.client.connect(5000, "localhost", 54555, 54777);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
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
					if(value instanceof Vector2) {
		
					} else if(value instanceof EntityNetworkIDsetMessage) {
						EntityNetworkIDsetMessage message = (EntityNetworkIDsetMessage)value;
						networkID = message.networkID;		
					} else if(value instanceof NewPlayerMessage) {
						NewPlayerMessage message = (NewPlayerMessage)value;
						System.out.println(message.networkID);
						messages.add(message);					
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
