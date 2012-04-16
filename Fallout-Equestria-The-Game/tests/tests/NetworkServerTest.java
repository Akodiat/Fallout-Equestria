package tests;

import java.util.ArrayList;
import java.util.List;

import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entitySystems.CollisionSystem;
import entitySystems.HealthBarRenderSystem;
import entitySystems.InputSystem;
import entitySystems.PhysicsSystem;
import entitySystems.RegenSystem;
import entitySystems.RenderingSystem;
import graphics.Color;
import graphics.SpriteBatch;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import ability.AbilityBuilder;
import ability.AbilitySystem;

import content.ContentManager;

import utils.Camera2D;
import utils.Rectangle;
import utils.Timer;

public class NetworkServerTest {

	protected SystemTester tester;
	protected SpriteBatch graphics;
	private final Rectangle screenDim;
	private final boolean isFullScreen;
	private Camera2D camera;
	private List<NetworkClientTest> clients;
	
	/**
	 * @param args
	 */
	
	public NetworkServerTest(Rectangle screenDim, boolean fullScreen) {
		this.screenDim = screenDim;
		this.isFullScreen = fullScreen;
		this.camera = new Camera2D(screenDim, screenDim);
	}
	
	public final void start()  {
			try {
				Display.setDisplayMode(new DisplayMode(screenDim.Width,screenDim.Height));
				Display.setFullscreen(isFullScreen);
				Display.create();
			} catch (LWJGLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.graphics = new SpriteBatch(this.screenDim);	
			this.tester = new SystemTester();
			
			initializeSystems();
			initializeEntities(this.tester.getWorld().getEntityManager());
			
			tester.startTesting();
			
			while(!Display.isCloseRequested()) {
				tester.updateWorld(1.0f / 60f);
				Timer.updateTimers(1f / 60f);
				
				graphics.clearScreen(new Color(157, 150, 101, 255));
				graphics.begin();
				tester.renderWorld();
				graphics.end();
				
				tester.getWorld().getEntityManager().destoryKilledEntities();
				Display.update();
				Display.sync(60);
			}
			
			Display.destroy(); 
	}
	
	public void initializeSystems(){
		tester.addLogicSubSystem(new PhysicsSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new CollisionSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new RegenSystem(this.tester.getWorld(), 0.5f));
		tester.addLogicSubSystem(new AbilitySystem(this.tester.getWorld(), AbilityBuilder.build()));
		
	}
	public void initializeEntities(IEntityManager manager){
		IEntity player = manager.createEntity(ContentManager.loadArchetype("Player.archetype"));
	}
	public void registerClient(NetworkClientTest client){
		if(this.clients == null)
			this.clients = new ArrayList<NetworkClientTest>();
		this.clients.add(client);
	}

}

