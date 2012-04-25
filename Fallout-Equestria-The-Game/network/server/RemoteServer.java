package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
import demos.ServerWorld;
import demos.WorldBuilder;
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
	private final int fps;
	private final String playerAsset = "Player.archetype";
	private Rectangle screenDim = new Rectangle(0,0,800,600);
	
	private IEntity player;
	private IEntityWorld world;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Clock clock;
	
	private Map<String, String> clientMap;
	private Map<String, IEntityArchetype> archetypeMap;

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
		this.world.update(time);
		this.world.getEntityManager().destoryKilledEntities();
		
	}

	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);
		this.spriteBatch.begin(null, this.camera.getTransformation());
		this.world.render();
		this.spriteBatch.end();
		
	}
	
	protected void initialize() {
	
		scene = ContentManager.load("MaseScenev0.xml", Scene.class); 		//TODO Load scene from server?
		camera = new Camera2D(scene.getWorldBounds(), screenDim);
		clock = new Clock();
		spriteBatch = new SpriteBatch(screenDim);
		clientMap = new HashMap<String,String>();
		archetypeMap = new HashMap<String,IEntityArchetype>();
		
		Injector injector = Guice.createInjector(new EntityModule());
		IEntityManager manager = injector.getInstance(IEntityManager.class);
		
		IEntityDatabase db = injector.getInstance(IEntityDatabase.class);
		
		IEntitySystemManager sm = injector.getInstance(IEntitySystemManager.class);
		
		world = WorldBuilder.buildServerWorld(camera, scene, spriteBatch, true, "Player");
		world.initialize();
		

		IEntityArchetype archetype = ContentManager.loadArchetype(playerAsset);
		this.player = manager.createEntity(archetype);
		player.addComponent(new BehaviourComp(new PlayerScript()));
		player.addToGroup(CameraControlSystem.GROUP_NAME);
		player.refresh();
		
		archetypeMap.put(archetype.getLabel(), archetype); //Adds player with server to the map so that clients can get it.
		
		sm.initialize();
	}

	@Override
	public void setNewInpComp(InputComp inpComp, String playerLabel) throws RemoteException {
		IEntity player = this.world.getEntityManager().getEntity(playerLabel);
		player.addComponent(inpComp);
	}
	
	public Map<String, TransformationComp> getTranspComps(){ //TODO Make a rectangle input parameter which specifies the area the entities should be in...
		List<IEntity> list = tester.getWorld().getDatabase().getEntitysContainingComponent(TransformationComp.class).asList();
		Map<String, TransformationComp> map = new HashMap<String, TransformationComp>();
		
		for (IEntity entity : list) {
			map.put(entity.getLabel(), entity.getComponent(TransformationComp.class));
		}
		
		return map;
	}
	
	public Map<String, PhysicsComp> getPhysComps(){ //TODO Make a rectangle input parameter which specifies the area the entities should be in...
		List<IEntity> list = tester.getWorld().getDatabase().getEntitysContainingComponent(PhysicsComp.class).asList();
		Map<String, PhysicsComp> map = new HashMap<String, PhysicsComp>();
		
		for (IEntity entity : list) {
			map.put(entity.getLabel(), entity.getComponent(PhysicsComp.class));
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
	public void registerClient(){
		String label = "player";
		int playerCount = 1;
		while(this.clientMap.containsKey(label+playerCount))
			playerCount++;
		
		try {
			clientMap.put(RemoteServer.getClientHost(),(label+playerCount));
		} catch (ServerNotActiveException e) {
			throw new RuntimeException("Server says it isn't active... sounds lazy... wait! I am the server :O");
		}
	}
	public String getClientLabel(){
		try {
			return this.clientMap.get(RemoteServer.getClientHost());
		} catch (ServerNotActiveException e) {
			throw new RuntimeException("Server says it isn't active... sounds lazy... wait! I am the server :O");
		}
	}
	
	private void addPlayer(IEntityArchetype archetype){
		IEntityManager manager  = this.world.getEntityManager();
		String label = "";
		try {
			label = this.clientMap.get(RemoteServer.getClientHost());
		} catch (ServerNotActiveException e) {
			e.printStackTrace();
		}
		archetype.setLabel(label); //Sets the label to the value from client register map. E.g. "player#"
	
		manager.createEntity(archetype);
		
		this.archetypeMap.put(label, archetype);
		System.out.println("Added new player with the label:"+label);
	}
	public List<String> getOtherPlayerArchetypes(){
		ArrayList<String> labelList = new ArrayList<String>(this.clientMap.values());
		labelList.add("Player"); //Add the player that hosts the server
		try {
			labelList.remove(clientMap.get(RemoteServer.getClientHost()));
		} catch (ServerNotActiveException e) {
			e.printStackTrace();
		}
		ArrayList<String> archList = new ArrayList<String>();
		
		for (String label : labelList) {
			EntityArchetypeLoader archLoader = new EntityArchetypeLoader();
			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			archLoader.save(ostream, this.archetypeMap.get(label));
			
			String archString = ostream.toString();
			archList.add(archString);
		}
		System.out.print("The list of archetypes sent to client contains "+archList.size()+" items. The first is "+archList.get(0));
		return archList;
	}

	@Override
	public IEntityDatabase getDatabase() throws RemoteException {
		System.out.println("Server is about to return database...");
		return this.world.getDatabase();
	}

	@Override
	public String test(String s) throws RemoteException {
		System.out.print("Hello, this is the server. Client said: "+s+" to me.");
		return "Message from server: Hi, you (my client) said: "+s+" to me.";
	}

}
