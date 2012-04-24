package client;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.opengl.*;

import com.google.inject.Guice;
import com.google.inject.Injector;

import scripting.PlayerScript;
import tests.EntityModule;
import utils.*;

import common.IRemoteServer;
import components.InputComp;
import components.BehaviourComp;
import content.ContentManager;
import content.EntityArchetypeLoader;
import demos.GameWorld;
import demos.MapDemo;
import entityFramework.*;
import entitySystems.CameraControlSystem;
import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class PlayerClient {

	private static IRemoteServer server;
	
	private final int fps;
	private final String playerAsset = "Player.archetype";
	private Rectangle screenDim = new Rectangle(0,0,800,600);
	
	private IEntity player;
	private GameWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Clock clock;
	private Scene scene;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			System.setSecurityManager(new RMISecurityManager());
			server = (IRemoteServer) Naming.lookup("rmi://localhost/server");
		
			System.out.println("Client says: Yawn! Umnumnnnnzzzzz... Gasp!... Hi!");
			
			PlayerClient client = new PlayerClient(new Rectangle(0,0,800,600), 60);
			client.start();
			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public PlayerClient(Rectangle screenDim, int fps) {
		this.screenDim = screenDim;
		this.fps = fps;
		this.clock = new Clock();
	}	
	
	public Clock getClock() {
		return this.clock;
	}
	
	public void start() {
			System.out.println("Hello, this is playerClient. I'm running");
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
		
		try {
			this.server.setNewInpComp(this.player.getComponent(InputComp.class), this.player.getLabel());
		} catch (RemoteException e) {
			System.out.println("Not able to send inputComp to server. Time="+time.getTotalTime().getTotalSeconds()); //TODO Better debug? throw further with message? Remove? Yes?
			e.printStackTrace();
		}
		
	}

	protected void initialize() {
		try {
			server.registerClient();
		} catch (RemoteException e2) {
			System.out.println("Failed to register client. RemoteException");
			e2.printStackTrace();
		}
		
		scene = ContentManager.load("SomeSortOfScene.xml", Scene.class); 		//TODO Load scene from server?
		camera = new Camera2D(scene.getWorldBounds(), screenDim);
		clock = new Clock();
		spriteBatch = new SpriteBatch(screenDim);
		
		Injector injector = Guice.createInjector(new EntityModule());
		IEntityManager manager = injector.getInstance(IEntityManager.class);
		
		IEntityDatabase db = injector.getInstance(IEntityDatabase.class);
		
		IEntitySystemManager sm = injector.getInstance(IEntitySystemManager.class);
		
		gameWorld = new GameWorld(manager, sm, db, camera, spriteBatch, scene);	//TODO Load GameWorld from server?
		gameWorld.initialize();
		

		IEntityArchetype archetype = ContentManager.loadArchetype(playerAsset);
		this.player = manager.createEntity(archetype);
		
		try {
			player.setLabel(server.getClientLabel());
		} catch (RemoteException e2) {
			System.out.println("Failed to get player label from server");
			e2.printStackTrace();
		}
		
		player.addComponent(new BehaviourComp(new PlayerScript()));
		player.addToGroup(CameraControlSystem.GROUP_NAME);
		player.refresh();
		
		System.out.println("Trying to say hello to the server");
		try {System.out.println(server.test("Hello?"));
		} catch (RemoteException e1) {
			System.out.println("...failed to say hello to the server :(");
			e1.printStackTrace();
		}
		
		System.out.println("Trying to add a player to server");
		try {
			EntityArchetypeLoader archLoader = new EntityArchetypeLoader();
			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			archLoader.save(ostream, archetype);
			
			String playerString = ostream.toString();
			System.out.println(playerString);
			server.addPlayer(playerString);
		} catch (RemoteException e) {
			System.out.println("Failed to add player to server");
			e.printStackTrace();
		} //TODO Move?
		
		sm.initialize();
		
		try {
			loadNewArchetypes(server.getOtherPlayerArchetypes());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void loadNewArchetypes(List<String> archetypeStringList){
		IEntityManager manager  = this.gameWorld.getEntityManager();
		for (String playerArchString : archetypeStringList) {
			EntityArchetypeLoader archLoader = new EntityArchetypeLoader();
			InputStream istream = new ByteArrayInputStream(playerArchString.getBytes());
			try {	
				manager.createEntity(archLoader.loadContent(istream));
			} catch (Exception e1) {
				System.out.println("Tried to add other players to "+this.player.getLabel()+" but failed att loading archetype");
				e1.printStackTrace();
			}
		}
		
	}

	public void update(GameTime time) {
		this.gameWorld.update(time);
		this.gameWorld.getEntityManager().destoryKilledEntities();
		
	}

	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);
		this.spriteBatch.begin(null, this.camera.getTransformation());
		this.gameWorld.render();
		this.spriteBatch.end();
		
	}

}
