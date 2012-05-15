package server;

import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import math.MathHelper;
import math.Vector2;
import misc.IEventListener;
import misc.SoundManager;

import animation.AnimationPlayer;
import animation.PonyColorChangeHelper;
import client.ClientTest;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import common.EntityCreatedMessage;
import common.EntityDestroyedMessage;
import common.EntityEventArgs;
import common.EntityMovedMessage;
import common.EntityNetworkIDsetMessage;
import common.InputMessage;
import common.NetworkedEntityFactory;
import common.NewPlayerMessage;
import common.PlayerCharacteristics;
import components.AnimationComp;
import components.BehaviourComp;
import components.HealthComp;
import components.InputComp;
import components.PhysicsComp;
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
import entityFramework.EntityDestroyedListener;
import entityFramework.EntityNetworkIDManager;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;
import entityFramework.IEntityWorld;
import entitySystems.CameraControlSystem;

public class ServerTest extends Demo {

	private static final Rectangle sr = new Rectangle(0,0, 800, 600);
	private static int connectionCount = 0;
	private Server server;
	
	private IEntityWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Scene scene;
	private Mouse mouse;
	private Keyboard keyboard;
	private SoundManager soundManager;
	
	private Map<IEntity, EntityEventArgs> newEntityMessages = new HashMap<>();
	private Map<Integer,NewPlayerMessage> playerMessages = new HashMap<>();
	private EntityNetworkIDManager idManager;
	
	
	public static void main(String[] args) {
		new ServerTest().start();
	}
	
	public ServerTest() {
		super(sr, 30);
		idManager = new EntityNetworkIDManager();
	}
	
	@Override
	protected void end() {
		server.stop();
	}

	@Override
	public void update(GameTime time) {
		if(Math.random() < 0.01) {	
			double f = (float)(MathHelper.Tau * Math.random());
			this.server.sendToAllTCP(new Vector2(MathHelper.sin(f), MathHelper.cos(f)));
		}
		
		this.mouse.poll(sr);
		this.keyboard.poll();
		this.gameWorld.update(time);
		this.gameWorld.getEntityManager().destoryKilledEntities();
		
		sendPositions();
	}

	private void sendPositions() {	
		for (IEntity entity : idManager.getEntities()) {
			EntityMovedMessage message = new EntityMovedMessage();
			
			message.entityID = entity.getUniqueID();
			message.newPhysComp = entity.getComponent(PhysicsComp.class);
			message.newTransfComp = entity.getComponent(TransformationComp.class);
			this.server.sendToAllTCP(message);	
		}
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
		server = new Server();

		this.startAndBindServer();
		server.addListener(getListener());
		
		
		createWorld();
		createPlayer();
		
		this.setup();
	}
	
	private void setup() {
		NetworkedEntityFactory factory = (NetworkedEntityFactory)this.gameWorld.getEntityManager().getFactory();
		factory.addCreatedListener(new IEventListener<EntityEventArgs>() {
			@Override
			public void onEvent(Object sender, EntityEventArgs e) {
				if(!e.getEntity().isInGroup("Players") && containsNetworkedComponents(e.getEntity())) {
					newEntityMessages.put(e.getEntity(), e);
					sendEntity(e);
					idManager.setNetworkIDToEntity(e.getEntity(), e.getEntity().getUniqueID());
					System.out.println("Gasp");
				}
			}
		});
		
		IEntityManager manager = this.gameWorld.getEntityManager();
		manager.addEntityDestoryedListener(new EntityDestroyedListener() {
			
			public void entityDestroyed(IEntity entity) {
				sendRemoveEntity(entity);
				newEntityMessages.remove(entity);
				playerMessages.remove(entity);
			}
		});
		
	}

	
	protected void sendRemoveEntity(IEntity entity) {
		idManager.removeNetworkIDEntity(entity);
		EntityDestroyedMessage message = new EntityDestroyedMessage();
		message.entityUniqueID = entity.getUniqueID();
		this.server.sendToAllTCP(message);
	}

	private void createWorld() {
		this.scene = this.ContentManager.load("PerspectiveV5.xml", Scene.class);
		this.mouse = new Mouse();
		this.keyboard = new Keyboard();
		this.soundManager = new SoundManager(this.ContentManager, 0.1f,1.0f,1.0f);
		this.spriteBatch = new SpriteBatch(sr);
		this.camera = new Camera2D(scene.getWorldBounds(), sr);
		
		this.gameWorld = WorldBuilder.buildServerWorld(camera, scene, mouse, keyboard, ContentManager, this.soundManager, spriteBatch, false, "Player0");
		this.gameWorld.initialize();
	}

	protected void sendEntity(EntityEventArgs entity) {
		EntityCreatedMessage message = createNewEntityMessage(entity);
		this.server.sendToAllTCP(message);
	}

	private EntityCreatedMessage createNewEntityMessage(EntityEventArgs entity) {
		EntityCreatedMessage message = new EntityCreatedMessage();
		message.networkID = entity.getEntity().getUniqueID();
		message.entityArchetypePath = entity.getEntityArchetype();
		message.transComp = entity.getEntity().getComponent(TransformationComp.class);
		return message;
	}
	
	protected void sendPlayer(NewPlayerMessage message, Connection connection) {
		connection.sendTCP(message);
	}
	
	protected void sendNewPlayer(NewPlayerMessage message) {
		this.server.sendToAllTCP(message);
	}
	

	protected void sendAllEntities(Connection connection) {
		for (EntityEventArgs entityArgs : this.newEntityMessages.values()) {
			EntityCreatedMessage message = this.createNewEntityMessage(entityArgs);
			connection.sendTCP(message);
		}
	}

	protected void sendAllPlayers(Connection connection) {
		for (NewPlayerMessage player : this.playerMessages.values()) {
			connection.sendTCP(player);
		}
	}
	
	protected boolean containsNetworkedComponents(IEntity entity) {
		
		return entity.hasComponent(TransformationComp.class) &&
			   entity.hasComponent(AnimationComp.class) ||
			   entity.hasComponent(RenderingComp.class) ||
			   entity.hasComponent(HealthComp.class);
	}

	private void createPlayer() {
		NewPlayerMessage message = new NewPlayerMessage();
		message.networkID = 0;
		message.playerCharacteristics = new PlayerCharacteristics();
		message.playerCharacteristics.bodyColor = Color.Gray;
		message.playerCharacteristics.eyeColor	= Color.Green;
		message.playerCharacteristics.maneColor = Color.Chocolate;
		//message.playerCharacteristics = "something something and something more!"

		this.playerMessages.put(0, message);
		this.createNewPlayer(message);		
	}
	
	
	
	private void startAndBindServer() {
		try {
			server.start();
			Network.registerClasses(server);
			server.bind(54555, 54777);
			System.out.println("yay");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void createNewPlayer(NewPlayerMessage message) {
		IEntityArchetype archetype = ContentManager.loadArchetype("Player.archetype");
		IEntity entity = this.gameWorld.getEntityManager().createEntity(archetype);
		entity.addComponent(new BehaviourComp(new PlayerScript()));
		entity.addComponent(new ShadowComp());
		entity.getComponent(TransformationComp.class).setPosition(1000,1000);
		
		AnimationPlayer player = this.ContentManager.loadAnimationSet("rdset.animset");
		AnimationComp comp = new AnimationComp(player);
		
		PonyColorChangeHelper.setBodyColor(message.playerCharacteristics.bodyColor, comp);
		PonyColorChangeHelper.setEyeColor( message.playerCharacteristics.eyeColor, comp);
		PonyColorChangeHelper.setManeColor(message.playerCharacteristics.maneColor, comp);
		
		entity.getComponent(InputComp.class).setKeyboard(new Keyboard());
		entity.getComponent(InputComp.class).setMouse(new Mouse());
		
		
		entity.removeComponent(RenderingComp.class);	
		entity.addComponent(comp);
		entity.setLabel("Player" + message.networkID);
		
		System.out.println("Player created" + message.networkID);
		
		if(entity.getUniqueID() == 0)
			entity.addToGroup(CameraControlSystem.GROUP_NAME);
		
		entity.refresh();
		
		message.networkID = entity.getUniqueID();		
		this.sendNewPlayer(message);
		idManager.setNetworkIDToEntity(entity, entity.getUniqueID());
	}

	protected void updatePlayerInput(InputMessage message) {
		IEntity entity = this.gameWorld.getEntityManager().getEntity("Player" + message.networkID);
		
		if(entity != null) {
			InputComp comp = entity.getComponent(InputComp.class);
			comp.setKeyboard(message.keyboard);
			comp.setMouse(message.mouse);
		}
	}	
	
	private Listener getListener() {
		return new Listener() {
			@Override
			public void connected(Connection connection) {
				EntityNetworkIDsetMessage message = new EntityNetworkIDsetMessage();
				message.networkID = ++connectionCount;
				connection.sendTCP(message);												
			}
			
			@Override
			public void received(Connection connection, Object value) {
				if(value instanceof NewPlayerMessage) {
					NewPlayerMessage message = (NewPlayerMessage)value;
					sendAllPlayers(connection);
					sendAllEntities(connection);

					playerMessages.put(connection.getID(), message);
					createNewPlayer(message);		
				} else if(value instanceof InputMessage) {
					InputMessage message = (InputMessage)value;
					updatePlayerInput(message);
				}
			}
			
			@Override
			public void disconnected(Connection connection) {
				NewPlayerMessage message = playerMessages.remove(connection.getID());
				if(message != null)
					removePlayer(message);
			}	
		};
	}

	protected void removePlayer(NewPlayerMessage message) {
		IEntity player = this.gameWorld.getEntityManager().getEntity(message.networkID);
		player.kill();
	}
}
