package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.google.inject.Guice;
import com.google.inject.Injector;

import scripting.PlayerScript;
import tests.EntityModule;
import tests.SystemTester;
import utils.*;
import common.IRemoteServer;
import components.*;
import content.ContentManager;
import content.EntityArchetypeLoader;
import demos.GameWorld;
import entityFramework.*;
import entitySystems.*;
import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;
/**
 * 
 * @author Joakim Johansspn
 *
 */
@SuppressWarnings("serial")
public class RemoteServer extends UnicastRemoteObject implements IRemoteServer{
	
	protected SystemTester tester;
	private Scene scene;
	private IEntityWorld world;
	
	private final int fps;
	private final String playerAsset = "Player.archetype";
	private Rectangle screenDim = new Rectangle(0,0,800,600);
	
	private IEntity player;
	private GameWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Clock clock;
	

	protected RemoteServer(Rectangle screenDim, int fps) throws RemoteException {
		this.screenDim = screenDim;
		this.fps = fps;
		this.clock = new Clock();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry();
			RemoteServer server = new RemoteServer(new Rectangle(0,0,800,600), 60);
			registry.bind("server", server);
			System.out.println("Server says: Yaaawn!");
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public Clock getClock() {
		return this.clock;
	}
	
	public final void start()  {
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
		
//		this.tester = new SystemTester();
//		this.world = tester.getWorld();
//		System.out.println("Hello, this is Server. I am running");
//		initialize();
//
//		tester.startTesting();
//
//		while(true) {							//TODO Find good stopping condition
//			tester.updateWorld(1.0f / 60f);
//			Timer.updateTimers(1f / 60f);
//
//		/*	graphics.clearScreen(new Color(157, 150, 101, 255));
//			graphics.begin();
//			tester.renderWorld();
//			graphics.end();
//		*/
//			tester.getWorld().getEntityManager().destoryKilledEntities();
//		} 
	}
	private void gameLoop() {
		this.clock.update();
		GameTime time = this.clock.getGameTime();
		this.update(time);
		this.render(time);
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
	
	protected void initialize() {
	
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
		player.addComponent(new ScriptComp(new PlayerScript()));
		player.addToGroup(CameraControlSystem.GROUP_NAME);
		player.refresh();
		
		sm.initialize();
	}

	public void initializeSystems(){
		tester.addLogicSubSystem(new PhysicsSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new CollisionSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new RegenSystem(this.tester.getWorld(), 0.5f));
		//tester.addLogicSubSystem(new AbilitySystem(this.tester.getWorld(), AbilityBuilder.build()));

	}

	@Override
	public void setNewInpComp(InputComp inpComp, int PlayerEntityID) throws RemoteException {
		IEntity player = this.getDatabase().getEntity(PlayerEntityID);
		player.removeComponent(InputComp.class);
		player.addComponent(inpComp);
		player.refresh();
	}
	
	public Map<Integer, TransformationComp> getTranspComps(){ //TODO Make a rectangle input parameter which specifies the area the entities should be in...
		List<IEntity> list = tester.getWorld().getDatabase().getEntitysContainingComponent(TransformationComp.class).asList();
		Map<Integer, TransformationComp> map = new HashMap<Integer, TransformationComp>();
		
		for (IEntity entity : list) {
			map.put(Integer.valueOf(entity.getUniqueID()), entity.getComponent(TransformationComp.class));
		}
		
		return map;
	}
	
	public Map<Integer, PhysicsComp> getPhysComps(){ //TODO Make a rectangle input parameter which specifies the area the entities should be in...
		List<IEntity> list = tester.getWorld().getDatabase().getEntitysContainingComponent(PhysicsComp.class).asList();
		Map<Integer, PhysicsComp> map = new HashMap<Integer, PhysicsComp>();
		
		for (IEntity entity : list) {
			map.put(Integer.valueOf(entity.getUniqueID()), entity.getComponent(PhysicsComp.class));
		}
		
		return map;
	}

	@Override
	public void addPlayer(String playerArchString) throws RemoteException {
		System.out.println("Trying to add a player");
		EntityArchetypeLoader archLoader = new EntityArchetypeLoader();
		InputStream istream = new ByteArrayInputStream(playerArchString.getBytes());
		try {	
			this.addPlayer(archLoader.loadContent(istream));
		} catch (Exception e1) {
			System.out.println("Tried to add player to server but failed att loading archetype");
			e1.printStackTrace();
		}
	}
	
	private void addPlayer(IEntityArchetype archetype){
		IEntityManager manager  = this.gameWorld.getEntityManager();
		String label = "player";
		int playerCount = 1;
		while(manager.getEntity(label+playerCount)!=null)
			playerCount++;
		archetype.setLabel(label+=playerCount);
	
		manager.createEntity(archetype);
		System.out.println("Added new player with the label:"+label);
	}

	@Override
	public IEntityDatabase getDatabase() throws RemoteException {
		System.out.println("Server is about to return database...");
		return this.gameWorld.getDatabase();
	}

	@Override
	public String test(String s) throws RemoteException {
		System.out.print("Hello, this is the server. Client said: "+s+" to me.");
		return "Message from server: Hi, you (my client) said: "+s+" to me.";
	}

}
