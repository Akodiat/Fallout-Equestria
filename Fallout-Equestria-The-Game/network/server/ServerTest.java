package server;

import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;
import java.io.IOException;
import misc.SoundManager;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import common.InputMessage;
import common.NetworkSystemBuilder;
import common.PlayerCharacteristics;
import components.InputComp;
import utils.Camera2D;
import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.Network;
import utils.Rectangle;
import demos.Demo;
import demos.WorldBuilder;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class ServerTest extends Demo {

	private static final Rectangle sr = new Rectangle(0,0, 800, 600);
	private Server server;
	private IEntityWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Scene scene;
	private Mouse mouse;
	private Keyboard keyboard;
	private SoundManager soundManager;
	
	public static void main(String[] args) {
		new ServerTest().start();
	}
	
	public ServerTest() {
		super(sr, 30);
	}
	
	@Override
	protected void end() {
		server.stop();
	}

	@Override
	public void update(GameTime time) {		
		this.mouse.poll(sr);
		this.keyboard.poll();
		this.gameWorld.update(time);
		this.gameWorld.getEntityManager().destoryKilledEntities();
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
		
		createWorld();		
	}
	

	private void createWorld() {
		this.scene = this.ContentManager.load("PerspectiveV5.xml", Scene.class);
		this.mouse = new Mouse();
		this.keyboard = new Keyboard();
		this.soundManager = new SoundManager(this.ContentManager, 0,0,0f);
		this.spriteBatch = new SpriteBatch(sr);
		this.camera = new Camera2D(scene.getWorldBounds(), sr);
		
		
		PlayerCharacteristics playerChars = new PlayerCharacteristics();
		playerChars.eyeColor = Color.Green;
		playerChars.bodyColor = Color.Gray;
		playerChars.maneColor = Color.Brown;
		
		
		this.gameWorld = WorldBuilder.buildServerWorld(camera, scene, mouse, keyboard, ContentManager, this.soundManager, spriteBatch, true, "Player0");
		NetworkSystemBuilder.createServerSystems(gameWorld, server, soundManager, ContentManager, playerChars);
		
		this.gameWorld.initialize();
	}
	
	private void startAndBindServer() {
		try {
			server.start();
			Network.registerClasses(server);
			server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
