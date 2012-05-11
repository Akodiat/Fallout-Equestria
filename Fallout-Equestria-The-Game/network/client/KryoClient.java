package client;

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

import com.esotericsoftware.kryonet.*;
import common.*;
import components.*;
import content.ContentManager;
import demos.WorldBuilder;
import entityFramework.*;
import entitySystems.CameraControlSystem;
import gameMap.Scene;
import gameMap.SceneNode;
import gameMap.TexturedSceneNode;
import graphics.*;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class KryoClient {

	private final int fps;
	private final String playerAsset = "Player.archetype";
	private Rectangle screenDim = new Rectangle(0,0,800,600);

	private Client client;

	private IEntity player;
	private IEntityWorld world;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Clock clock;
	private Scene scene;

	private Mouse mouse;
	private Keyboard keyboard;

	private Object lock = new Object();

	private ContentManager contentManager;
	private IEntityNetworkIDManager entityNetworkIDManager;
	private List<EntityMovedMessage> movementsToImplement;

	public static void main(String[] args) throws IOException{
		KryoClient kryoClient = new KryoClient(new Rectangle(0,0,800,600), 60);
		kryoClient.start();
	}

	public KryoClient(Rectangle screenDim, int fps){
		this.screenDim = screenDim;
		this.fps = fps;
		this.clock = new Clock();
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
			client.stop();
		}
	}

	private void gameLoop() {
//		if(!this.client.isConnected())
//			try {
//				this.client.reconnect();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		this.clock.update();
		GameTime time = this.clock.getGameTime();
		this.update(time);
		this.render(time);

		this.client.sendUDP(this.player.getComponent(InputComp.class));

		this.moveEntities();
	}

	protected void initialize() throws IOException {
		contentManager = new ContentManager("resources");
		entityNetworkIDManager = new EntityNetworkIDManager();
		movementsToImplement = new ArrayList<EntityMovedMessage>();

		scene = contentManager.load("PerspectiveV5.xml", Scene.class);  		//TODO Load scene from server?
		camera = new Camera2D(scene.getWorldBounds(), screenDim);
		clock = new Clock();
		spriteBatch = new SpriteBatch(screenDim);
		mouse = new Mouse();
		keyboard = new Keyboard();

		this.client = new Client(1638400, 2048000);
		this.client.start();
		Network.registerClasses(this.client);

		String label = Utils.getPlayerLabel(this.client.getID());
		SoundManager soundManager = new SoundManager(this.contentManager,1.0f,1.0f,1.0f);		
		world = WorldBuilder.buildServerWorld(camera, scene, mouse, keyboard, contentManager,soundManager, spriteBatch, true, label);
		world.initialize();



		this.client.connect(5000, "localhost", 54555, 54777);

		this.client.addListener(this.generateNewListener());








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

		player.setLabel(label);
		player.addToGroup(CameraControlSystem.GROUP_NAME);
		player.refresh();

		NewPlayerMessage message = new NewPlayerMessage();
		message.specialComp = player.getComponent(SpecialComp.class);
		message.playerCharacteristics = new PlayerCharacteristics();
		message.localClientID = player.getUniqueID();

		message.playerCharacteristics.name = "P"+(int)(Math.random()*21);
		message.playerCharacteristics.color= new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255), 255);


		message.playerCharacteristics.archetypePath = playerAsset;





		this.client.sendTCP(message);


	}

	public void update(GameTime time) {
		this.mouse.poll(screenDim);
		this.keyboard.poll();

		this.world.update(time);
		this.world.getEntityManager().destoryKilledEntities();	
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
	private void moveEntities(){
		synchronized(lock){
			for (EntityMovedMessage message : movementsToImplement) {
				IEntity entity = entityNetworkIDManager.getEntityFromNetworkID(message.entityID);
				System.out.println(entity);
				if(entity 
						!= null){
					entity.getComponent(TransformationComp.class).setAllFieldsToBeLike(message.newTransfComp);
					entity.getComponent(PhysicsComp.class).setAllFieldsToBeLike(message.newPhysComp);

					entity.refresh();
				}
			}
			movementsToImplement.clear();
		}
	}

	private Listener generateNewListener(){
		return new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof NewPlayerMessage){
					NewPlayerMessage message = (NewPlayerMessage) object;
	
					
					IEntity player = world.getEntityManager().createEntity(
							new EntityArchetypeProxy(contentManager.loadArchetype(
									message.playerCharacteristics.archetypePath)));

					player.setLabel(Utils.getPlayerLabel(message.networkID));
					System.out.println(world.getEntityManager().getEntity(Utils.getPlayerLabel(message.networkID)));
					System.out.println(player);
					
					player.getComponent(RenderingComp.class).setColor(message.playerCharacteristics.color);
					player.refresh();
				}
				else if (object instanceof EntityNetworkIDsetMessage){
					EntityNetworkIDsetMessage message = (EntityNetworkIDsetMessage) object;
					entityNetworkIDManager.setNetworkIDToEntity(world.getEntityManager().getEntity(message.localClientID), message.networkID);				
				}
				else if (object instanceof EntityMovedMessage){
					EntityMovedMessage message = (EntityMovedMessage) object;
					synchronized(lock){
						movementsToImplement.add(message);
					}
				}
			};
		};
	}
}
