package tests;

import java.io.IOException;

import math.Vector2;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import components.*;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entitySystems.*;

import utils.Camera2D;
import utils.Circle;
import utils.ContentManager;
import utils.Rectangle;

import gameMap.*;
import graphics.*;

public class CameraAndMapTest {
	private SystemTester tester;
	private SpriteBatch graphics;
	private final Rectangle screenDim;
	private final boolean isFullScreen;
	private MapTester mapTester;
	private Camera2D camera;
	
	public static void main(String[] args) throws IOException, LWJGLException{
		new CameraAndMapTest(new Rectangle(0,0, 1280, 720), true).start();
	}

	public CameraAndMapTest(Rectangle screenDim, boolean fullScreen) throws IOException{
		this.screenDim = screenDim;
		this.isFullScreen = fullScreen;

	}

	public final void start() throws IOException  {
		try {
			Display.setDisplayMode(new DisplayMode(screenDim.Width,screenDim.Height));
			Display.setFullscreen(isFullScreen);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		this.graphics = new SpriteBatch(this.screenDim);	
		this.tester = new SystemTester();
		this.mapTester = new MapTester(this.graphics);
		this.camera = new Camera2D(new Rectangle(0,0,10000,10000), screenDim);
		initializeSystems();
		initializeEntities(this.tester.getWorld().getEntityManager());

		tester.startTesting();

		while(!Display.isCloseRequested()) {
			tester.updateWorld(1.0f / 60f);

			graphics.clearScreen(new Color(157, 150, 101, 255));
			graphics.begin(null, camera.getTransformation());
			
			mapTester.draw();
			tester.renderWorld();
			
			graphics.end();

			tester.getWorld().getEntityManager().destoryKilledEntities();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				camera.move(new Vector2(5, 0));
			} 
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				camera.move(new Vector2(-5, 0));
			} 
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				camera.move(new Vector2(0, -5));
			} 
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				camera.move(new Vector2(0, 5));
			} 
			if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
				camera.zoomIn(0.001f);
			} 
			
			
			Display.update();
			Display.sync(60);
		}

		Display.destroy(); 
	}

	public void initializeSystems() {
		tester.addLogicubSystem(new CharacterControllerSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new InputSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new PhysicsSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new AttackResolveSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new StatusChangeSystem(this.tester.getWorld()));
		tester.addRenderSubSystem(new HealthBarRenderSystem(this.tester.getWorld(), this.graphics));
		tester.addRenderSubSystem(new RenderingSystem(this.tester.getWorld(), this.graphics));
		tester.addRenderSubSystem(new DebugAttackRenderSystem(this.tester.getWorld(), this.graphics));
		tester.addRenderSubSystem(new DebugSpatialRenderSystem(this.tester.getWorld(),this.graphics));
		tester.addRenderSubSystem(new HUDRenderingSystem(this.tester.getWorld(), this.graphics, "Player"));
	}

	public void initializeEntities(IEntityManager manager) {
		IEntity house = manager.createEmptyEntity();
		house.setLabel("House");
		TransformationComp housePosComp = new TransformationComp();
		housePosComp.setPosition(1337, 1337);
		SpatialComponent houseSpatComp = new SpatialComponent(new Circle(new Vector2(30,20),50f));
		RenderingComponent houseRendComp = new RenderingComponent();
		houseRendComp.setTexture(ContentManager.loadTexture("house.png"));
		PhysicsComponent housePhysComp = new PhysicsComponent();
		housePhysComp.setMass(99999999);
		
		house.addComponent(houseRendComp);
		house.addComponent(houseSpatComp);
		house.addComponent(housePosComp);
		house.addComponent(housePhysComp);
		
		house.refresh();
		
		IEntity player = manager.createEmptyEntity();
		player.addToGroup("Enemies");
		player.setLabel("Player");
		PhysicsComponent physComp = new PhysicsComponent();
		InputComponent inpComp = new InputComponent();
		TransformationComp posComp = new TransformationComp();
		posComp.setPosition(new Vector2(Display.getHeight()/2,Display.getWidth()/2));
		SpatialComponent spatComp = new SpatialComponent(new Circle(Vector2.Zero,30f));
		RenderingComponent rendComp = new RenderingComponent();
		HealthComponent healthComp = new HealthComponent(100, 2, 89);

		rendComp.setColor(new Color(42,200,255, 255));
		rendComp.setTexture(ContentManager.loadTexture("PPieLauncher.png"));

		player.addComponent(rendComp);
		player.addComponent(inpComp);
		player.addComponent(physComp);
		player.addComponent(posComp);
		player.addComponent(spatComp);
		player.addComponent(healthComp);

		player.refresh();
		
		
	}
}
