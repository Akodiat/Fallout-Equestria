package server;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;

import tests.EntityModule;
import tests.SystemTester;
import utils.*;
import common.IRemoteServer;
import components.*;
import content.EntityArchetypeLoader;
import entityFramework.*;
import entitySystems.*;
import gameMap.Scene;
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
	

	protected RemoteServer() throws RemoteException {
		//Super constructor throws exception
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("server", new RemoteServer());
			System.out.println("Server says: Yaaawn!");
			new RemoteServer().start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public final void start()  {	
		this.tester = new SystemTester();
		this.world = tester.getWorld();
		System.out.println("Hello, this is Server. I am running");
		initialize();

		tester.startTesting();

		while(true) {							//TODO Find good stopping condition
			tester.updateWorld(1.0f / 60f);
			Timer.updateTimers(1f / 60f);

		/*	graphics.clearScreen(new Color(157, 150, 101, 255));
			graphics.begin();
			tester.renderWorld();
			graphics.end();
		*/
			tester.getWorld().getEntityManager().destoryKilledEntities();
		} 
	}
	
	protected void initialize() {
	
		Injector injector = Guice.createInjector(new EntityModule());
		IEntityManager manager = injector.getInstance(IEntityManager.class);
		
		IEntityDatabase db = injector.getInstance(IEntityDatabase.class);
		IEntitySystemManager sm = injector.getInstance(IEntitySystemManager.class);
		
		
		this.initializeSystems();
		
		
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
		IEntityManager manager  = this.tester.getWorld().getEntityManager();
		manager.createEntity(archetype);
	}

	@Override
	public IEntityDatabase getDatabase() throws RemoteException {
		System.out.println("Server is about to return database...");
		return this.tester.getWorld().getDatabase();
	}

	@Override
	public String test(String s) throws RemoteException {
		System.out.print("Hello, this is the server. Client said: "+s+" to me.");
		return "Message from server: Hi, you (my client) said: "+s+" to me.";
	}

}
