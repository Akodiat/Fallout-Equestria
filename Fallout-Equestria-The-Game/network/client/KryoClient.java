package client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import scripting.PlayerScript;
import utils.Camera2D;
import utils.Clock;
import utils.GameTime;
import utils.Rectangle;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import common.IRemoteServer;
import common.Network;
import common.NewPlayerMessage;
import common.PlayerCharacteristics;
import common.Utils;
import components.BehaviourComp;
import components.InputComp;
import components.RenderingComp;
import components.SpecialComp;
import content.ContentManager;
import content.EntityArchetypeLoader;
import demos.WorldBuilder;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityWorld;
import entitySystems.CameraControlSystem;
import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;

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

	public static void main(String[] args) throws IOException{
		KryoClient kryoClient = new KryoClient(new Rectangle(0,0,800,600), 60);
		kryoClient.start();
	}

	public KryoClient(Rectangle screenDim, int fps) throws IOException {
		this.client = new Client();
		this.client.start();
		Network.registerClasses(this.client);

		this.client.connect(5000, "localhost", 54555, 54777);

		this.client.addListener(this.generateNewListener());

		this.screenDim = screenDim;
		this.fps = fps;
		this.clock = new Clock();
	}

	public void start() {
		System.out.println("Hello, this is playerClient. I'm running. I appearantly have the ID "+this.client.getID());
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
		}
	}

	private void gameLoop() {
		this.clock.update();
		GameTime time = this.clock.getGameTime();
		this.update(time);
		this.render(time);

		this.client.sendUDP(this.player.getComponent(InputComp.class));
	}

	protected void initialize() {

		scene = ContentManager.load("MaseScenev0.xml", Scene.class);  		//TODO Load scene from server?
		camera = new Camera2D(scene.getWorldBounds(), screenDim);
		clock = new Clock();
		spriteBatch = new SpriteBatch(screenDim);

		String label = Utils.getPlayerLabel(this.client.getID());

		world = WorldBuilder.buildServerWorld(camera, scene, spriteBatch, true, label);
		world.initialize();

		IEntityArchetype archetype = ContentManager.loadArchetype(playerAsset);
		this.player = world.getEntityManager().createEntity(archetype);

		player.addComponent(new BehaviourComp(new PlayerScript()));
		player.setLabel(label);
		player.addToGroup(CameraControlSystem.GROUP_NAME);
		player.refresh();

		NewPlayerMessage message = new NewPlayerMessage();
		message.specialComp = player.getComponent(SpecialComp.class);
		message.playerCharacteristics = new PlayerCharacteristics();

		message.playerCharacteristics.name = "P"+(int)(Math.random()*21);
		message.playerCharacteristics.color= new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255), 255);

		//Not to be here:
		player.getComponent(RenderingComp.class).setColor(message.playerCharacteristics.color); player.refresh();

		message.playerCharacteristics.archetypePath = playerAsset;

		this.client.sendTCP(message);

	}

	public void update(GameTime time) {
		this.world.update(time);
		this.world.getEntityManager().destoryKilledEntities();	
	}



	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);
		this.spriteBatch.begin(null, this.camera.getTransformation());
		this.world.render();
		this.spriteBatch.end();

	}

	private Listener generateNewListener(){
		return new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof NewPlayerMessage){
					NewPlayerMessage message = (NewPlayerMessage) object;

					IEntity player = world.getEntityManager().createEntity(
							ContentManager.loadArchetype(
									message.playerCharacteristics.archetypePath));
					player.setLabel(Utils.getPlayerLabel(connection.getID()));
					player.getComponent(RenderingComp.class).setColor(message.playerCharacteristics.color);
					player.refresh();
				}
			};
		};
	}
}
