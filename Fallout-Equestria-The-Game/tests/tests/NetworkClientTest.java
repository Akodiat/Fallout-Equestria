package tests;

import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entitySystems.InputSystem;
import entitySystems.RenderingSystem;
import graphics.Color;
import graphics.SpriteBatch;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import content.ContentManager;

import utils.Camera2D;
import utils.Rectangle;
import utils.Timer;

public class NetworkClientTest {

	protected SystemTester tester;
	protected SpriteBatch graphics;
	private final Rectangle screenDim;
	private final boolean isFullScreen;
	private Camera2D camera;
	private NetworkServerTest server;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new NetworkClientTest(new Rectangle(0, 0, 800, 600), false).start();
	}
	
	public NetworkClientTest(Rectangle screenDim, boolean fullScreen) {
		this(screenDim, fullScreen, new NetworkServerTest(new Rectangle(0, 0, 800, 600), false));
		this.server.start();
	}
	
	public NetworkClientTest(Rectangle screenDim, boolean fullScreen, NetworkServerTest server) {
		this.server = server;
		this.server.registerClient(this);
		
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
		tester.addLogicSubSystem(new InputSystem(this.tester.getWorld(), this.camera));
		tester.addRenderSubSystem(new RenderingSystem(this.tester.getWorld(), this.graphics));
		
	}
	public void initializeEntities(IEntityManager manager){
		IEntity player = manager.createEntity(ContentManager.loadArchetype("Player.archetype"));
	}

}
