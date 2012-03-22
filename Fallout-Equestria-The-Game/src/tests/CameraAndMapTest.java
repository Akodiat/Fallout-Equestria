package tests;

import java.io.IOException;

import math.Vector2;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import components.*;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entitySystems.*;

import utils.Circle;
import utils.Rectangle;

import gameMap.*;
import graphics.*;

public class CameraAndMapTest {
	private SystemTester tester;
	private SpriteBatch graphics;
	private final Rectangle screenDim;
	private final boolean isFullScreen;
	private MapTester mapTester;
	public static void main(String[] args) throws IOException, LWJGLException{
		new CameraAndMapTest(new Rectangle(0,0, 800, 600), false).start();
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
		this.mapTester = new MapTester();

		initializeSystems();
		initializeEntities(this.tester.getWorld().getEntityManager());

		tester.startTesting();

		while(!Display.isCloseRequested()) {
			tester.updateWorld(1.0f / 60f);

			graphics.clearScreen(new Color(157, 150, 101, 255));
			graphics.begin();
			mapTester.draw();
			tester.renderWorld();
			graphics.end();

			tester.getWorld().getEntityManager().destoryKilledEntities();
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
		tester.addRenderSubSystem(new HUDRenderingSystem(this.tester.getWorld(), this.graphics, "Player"));
	}

	public void initializeEntities(IEntityManager manager) {
		IEntity player = manager.createEmptyEntity();
		player.addToGroup("Enemies");
		player.setLabel("Player");
		PhysicsComponent physComp = new PhysicsComponent();
		InputComponent inpComp = new InputComponent();
		TransformationComp posComp = new TransformationComp();
		posComp.setPosition(new Vector2(Display.getHeight()/2,Display.getWidth()/2));
		SpatialComponent spatComp = new SpatialComponent(new Circle(posComp.getPosition(),30f));
		RenderingComponent rendComp = new RenderingComponent();
		HealthComponent healthComp = new HealthComponent(100, 2, 89);

		rendComp.setColor(new Color(42,200,255, 255));
		try {
			rendComp.setTexture(TextureLoader.loadTexture(TextureTest.class.getResourceAsStream("PPieLauncher.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		player.addComponent(rendComp);
		player.addComponent(inpComp);
		player.addComponent(physComp);
		player.addComponent(posComp);
		player.addComponent(spatComp);
		player.addComponent(healthComp);

		player.refresh();
	}
}
