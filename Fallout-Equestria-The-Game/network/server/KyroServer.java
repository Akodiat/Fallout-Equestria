package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import misc.SoundManager;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import scripting.PlayerScript;
import utils.Camera2D;
import utils.Clock;
import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.Rectangle;
import animation.AnimationPlayer;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import common.EntityMovedMessage;
import common.EntityNetworkIDsetMessage;
import common.Network;
import common.NewPlayerMessage;
import common.PlayerCharacteristics;
import common.Utils;
import components.AnimationComp;
import components.BehaviourComp;
import components.InputComp;
import components.PhysicsComp;
import components.RenderingComp;
import components.ShadowComp;
import components.SpecialComp;
import components.TransformationComp;
import content.ContentManager;
import demos.WorldBuilder;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityWorld;
import entitySystems.CameraControlSystem;
import gameMap.Scene;
import gameMap.SceneNode;
import gameMap.TexturedSceneNode;
import graphics.Color;
import graphics.SpriteBatch;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class KyroServer {
	private final int fps;
	private static final String playerAsset = "Player.archetype";
	private Rectangle screenDim = new Rectangle(0,0,800,600);

	private Server server;
	private List<NewPlayerMessage> addedPlayerMessages; //Used to notify new players of players added earlier.

	private IEntity player;
	private IEntityWorld world;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Clock clock;
	private Scene scene;
	
	private Mouse mouse;
	private Keyboard keyboard;
	
	private ContentManager contentManager;

	public KyroServer(Rectangle screenDim, int fps) throws IOException{
		this.server = new Server();
		this.server.start();
		this.server.bind(54555, 54777);

		Network.registerClasses(this.server);

		this.server.addListener(this.generateNewListener());

		this.screenDim = screenDim;
		this.fps = fps;
		this.clock = new Clock();
	}

	public static void main(String[] args) throws IOException{
		KyroServer kryoServer = new KyroServer(new Rectangle(0,0,800,600), 60);
		kryoServer.start();
	}

	public void start() {
		try {
			Display.setVSyncEnabled(true);
			Display.setDisplayMode(new DisplayMode(screenDim.Width, screenDim.Height));
			Display.create();
			this.initialize();

			while(!Display.isCloseRequested()) {
				this.gameLoop();
				Display.update();
				Display.sync(this.fps);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Display.destroy();
			server.stop();
		}
	}
	
	private void gameLoop() {
		this.clock.update();
		GameTime time = this.clock.getGameTime();
		this.update(time);
		this.render(time);
	}
	
	protected void initialize() {
		contentManager = new ContentManager("resources");

		scene = contentManager.load("PerspectiveV1.xml", Scene.class);  		//TODO Load scene from server?
		camera = new Camera2D(scene.getWorldBounds(), screenDim);
		clock = new Clock();
		spriteBatch = new SpriteBatch(screenDim);
		mouse = new Mouse();
		keyboard = new Keyboard();
		
		addedPlayerMessages = new ArrayList<NewPlayerMessage>();
		
		String label = "server";

		SoundManager soundManager = new SoundManager(this.contentManager,1.0f,1.0f,1.0f);
		
		
		world = WorldBuilder.buildServerWorld(camera, scene, mouse, keyboard, contentManager,soundManager, spriteBatch, true, label);
		world.initialize();

		//ANIMATION UGLY SHIT
				IEntityArchetype archetype = contentManager.loadArchetype(playerAsset);
				player = this.world.getEntityManager().createEntity(archetype);
				player.addComponent(new BehaviourComp(new PlayerScript()));
				player.addComponent(new ShadowComp());
				player.getComponent(TransformationComp.class).setPosition(1000,1000);
				player.getComponent(InputComp.class).setMouse(mouse);
				player.getComponent(InputComp.class).setKeyboard(keyboard);
				
				SceneNode playerPosNode = scene.getNodeByID("PlayerSpawnPosition");
				player.getComponent(TransformationComp.class).setPosition(playerPosNode.getPosition());
				addTexturedNodes();
				
				
				AnimationPlayer animPlayer = this.contentManager.loadAnimationSet("rdset.animset");
				AnimationComp comp = new AnimationComp(animPlayer);
				//comp.setTint(Color.Green);
				
				player.removeComponent(RenderingComp.class);	
				player.addComponent(comp);
		//END OF ANIMATION UGLY SHIT
		player.setLabel("Server");
		player.addToGroup(CameraControlSystem.GROUP_NAME);
		player.refresh();
		
		NewPlayerMessage message = new NewPlayerMessage();
		message.specialComp = player.getComponent(SpecialComp.class);
		message.playerCharacteristics = new PlayerCharacteristics();
		
		message.playerCharacteristics.name = "P"+(int)(Math.random()*21);
		message.playerCharacteristics.color = new Color(); //new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255), 255);
		message.playerCharacteristics.archetypePath = playerAsset;
		message.localClientID = player.getUniqueID();
		message.networkID = player.getUniqueID();
		
		
		this.addedPlayerMessages.add(message);
	}

	@SuppressWarnings("unchecked")
	public void update(GameTime time) {
		this.world.update(time);
		this.world.getEntityManager().destoryKilledEntities();
		
		this.mouse.poll(screenDim);
		this.keyboard.poll();
		
		for (IEntity entity: this.world.getDatabase().getEntitysContainingComponents(TransformationComp.class, PhysicsComp.class)) {
			//if(!entity.getComponent(PhysicsComp.class).getVelocity().equals(Vector2.Zero)){
				EntityMovedMessage message = new EntityMovedMessage();
				
				message.entityID = entity.getUniqueID();
				message.newPhysComp = entity.getComponent(PhysicsComp.class);
				message.newTransfComp = entity.getComponent(TransformationComp.class);
				
				server.sendToAllUDP(message);
			//}
				
		}
	}

	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);
		this.spriteBatch.begin(null, this.camera.getTransformation());
		this.world.render();
		this.spriteBatch.end();

	}
	
	private void addTexturedNodes() {
		for (TexturedSceneNode tNode : this.scene.getTexturedNodes()) {
			IEntity entity = this.world.getEntityManager().createEmptyEntity();
			TransformationComp transComp = new TransformationComp();
			transComp.setPosition(tNode.getPosition());
			
			RenderingComp renderComp = new RenderingComp();
			renderComp.setTexture(tNode.getTexture());
			
			entity.addComponent(transComp);
			entity.addComponent(renderComp);
			entity.refresh();
		}
		
		
	}

	private Listener generateNewListener(){
		return new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof InputComp) {
					InputComp message = (InputComp) object;
					IEntity player = world.getEntityManager().getEntity(Utils.getPlayerLabel(connection.getID()));
					player.getComponent(InputComp.class).setAllToBeLike(message);
				}
				else if (object instanceof NewPlayerMessage){
					for (NewPlayerMessage message : addedPlayerMessages) { //Sends messages from all other players to the newly connected player.
						System.out.println(message);
						connection.sendTCP(message);
					}
					EntityNetworkIDsetMessage iDMessage = new EntityNetworkIDsetMessage();
					iDMessage.localClientID = player.getUniqueID();
					iDMessage.networkID = player.getUniqueID();
					server.sendToTCP(connection.getID(), iDMessage);
					
					NewPlayerMessage message = (NewPlayerMessage) object;
					
					
					//ANIMATION UGLY SHIT
					IEntityArchetype archetype = contentManager.loadArchetype(playerAsset);
					IEntity player = world.getEntityManager().createEntity(
							contentManager.loadArchetype(
									message.playerCharacteristics.archetypePath));
					player.addComponent(new BehaviourComp(new PlayerScript()));
					player.addComponent(new ShadowComp());
					player.getComponent(TransformationComp.class).setPosition(1000,1000);
					player.getComponent(InputComp.class).setMouse(mouse);
					player.getComponent(InputComp.class).setKeyboard(keyboard);
					
					SceneNode playerPosNode = scene.getNodeByID("PlayerSpawnPosition");
					player.getComponent(TransformationComp.class).setPosition(playerPosNode.getPosition());
					addTexturedNodes();
					
					
					AnimationPlayer animPlayer = contentManager.loadAnimationSet("rdset.animset");
					AnimationComp comp = new AnimationComp(animPlayer);
					//comp.setTint(Color.Green);
					
					player.removeComponent(RenderingComp.class);	
					player.addComponent(comp);
			//END OF ANIMATION UGLY SHIT
					player.setLabel(Utils.getPlayerLabel(connection.getID()));

					player.refresh();
					
					message.networkID = player.getUniqueID();
					addedPlayerMessages.add(message);
					
					server.sendToAllExceptTCP(connection.getID(), message);
					
					EntityNetworkIDsetMessage returnMessage = new EntityNetworkIDsetMessage();
					returnMessage.localClientID = message.localClientID;
					returnMessage.networkID = message.networkID;
					
					server.sendToTCP(connection.getID(), returnMessage);
				}
			}
			@Override
			public void connected(Connection arg0) {
				super.connected(arg0);	
				
			}
		};
	}


}
