package tests;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import math.Vector2;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.google.common.collect.ImmutableList;

import components.*;
import content.ContentManager;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entitySystems.*;

import utils.Camera2D;
import utils.Circle;
import utils.Rectangle;
import utils.Timer;

import gameMap.*;
import graphics.*;
public class AnimationTest {

	private SystemTester tester;
	private SpriteBatch graphics;
	private final Rectangle screenDim;
	private final boolean isFullScreen;
	private MapTester mapTester;
	private Camera2D camera;
	
	private final int numEnemies = 400;
	
	public static void main(String[] args) throws IOException, LWJGLException{
		new AnimationTest(new Rectangle(0,0, 1280, 720), true).start();
	}

	public AnimationTest(Rectangle screenDim, boolean fullScreen) throws IOException{
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
		tester.addLogicubSystem(new CollisionSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new CameraControlSystem(this.tester.getWorld(), camera));
		tester.addLogicubSystem(new AttackResolveSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new StatusChangeSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new RegenSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new MapCollisionSystem(this.tester.getWorld(), new Vector2(this.camera.worldBounds.Width, this.camera.worldBounds.Height)));
		tester.addRenderSubSystem(new HealthBarRenderSystem(this.tester.getWorld(), this.graphics));
		tester.addRenderSubSystem(new AnimationSystem(this.tester.getWorld()));
		tester.addRenderSubSystem(new RenderingSystem(this.tester.getWorld(), this.graphics));
		tester.addRenderSubSystem(new DebugAttackRenderSystem(this.tester.getWorld(), this.graphics));
		tester.addRenderSubSystem(new DebugSpatialRenderSystem(this.tester.getWorld(),this.graphics));
		tester.addRenderSubSystem(new HUDRenderingSystem(this.tester.getWorld(), this.graphics, "Player"));

	}

	public void initializeEntities(IEntityManager manager) {
		
		IEntity player = manager.createEmptyEntity();
		player.addToGroup("Enemies");
		player.addToGroup("CAMERA_TARGET");
		
		player.setLabel("Player");
		PhysicsComponent physComp = new PhysicsComponent();
		InputComponent inpComp = new InputComponent();
		TransformationComp posComp = new TransformationComp();
		posComp.setPosition(new Vector2(Display.getHeight()/2,Display.getWidth()/2));
		SpatialComponent spatComp = new SpatialComponent(new Circle(Vector2.Zero,30f));

		HealthComponent healthComp = new HealthComponent(100, 2, 89);
		ActionPointsComponent apComp = new ActionPointsComponent();
		
		//ANIMATIONCOMPONENT
		Frame frame1 = new Frame(new Rectangle(0,0,44,38),new Vector2(22,19), false);
		Frame frame2 = new Frame(new Rectangle(44,0,44,38),new Vector2(22,19), false);
		Frame frame3 = new Frame(new Rectangle(92,0,40,38),new Vector2(22,19), false);
		ImmutableList<Frame> frames = ImmutableList.of(frame1,frame2,frame3);
		Timer aniTimer = new Timer(0.1f, Integer.MAX_VALUE);
		Animation animation = new Animation(frames, aniTimer);
		Map<String, Animation> animations = new HashMap<String, Animation>();
		animations.put("walk", animation);
		AnimationComp aniComp = new AnimationComp(animations);
		aniComp.setActiveAnimation("walk");
		aniComp.getActiveAnimation().getTimer().Start();
		
		//rendcomp
		RenderingComponent rendComp = new RenderingComponent();
		rendComp.setColor(new Color(42,200,255, 255));
		rendComp.setTexture(ContentManager.loadTexture("DerpyFlySheet.png"));

		player.addComponent(rendComp);
		player.addComponent(aniComp);
		player.addComponent(inpComp);
		player.addComponent(physComp);
		player.addComponent(posComp);
		player.addComponent(spatComp);
		player.addComponent(healthComp);
		player.addComponent(apComp);

		player.refresh();
		

		
		
		IEntity house = manager.createEmptyEntity();
		house.setLabel("House");
		TransformationComp housePosComp = new TransformationComp();
		housePosComp.setPosition(1337, 1337);
		SpatialComponent houseSpatComp = new SpatialComponent(new Circle(Vector2.Zero,50f));
		RenderingComponent houseRendComp = new RenderingComponent();
		houseRendComp.setTexture(ContentManager.loadTexture("house.png"));
		PhysicsComponent housePhysComp = new PhysicsComponent();
		housePhysComp.setMass(1f);
		housePosComp.setOrigin(new Vector2(houseRendComp.getTexture().Width / 2,
										   houseRendComp.getTexture().Height / 2));
		
		house.addComponent(houseRendComp);
		house.addComponent(houseSpatComp);
		house.addComponent(housePosComp);
		house.addComponent(housePhysComp);
		
		house.refresh();
		
		
		generateRandomEnemies(manager);
	}

	private void generateRandomEnemies(IEntityManager manager) {
		for (int i = 0; i < this.numEnemies; i++) {
			IEntity enemy = manager.createEmptyEntity();
			enemy.setLabel("Enemy" + i);
			
			TransformationComp transComp = new TransformationComp();
			transComp.setPosition((float)Math.random() * 10000,(float)Math.random() * 10000);
			SpatialComponent spatComp = new SpatialComponent(new Circle(Vector2.Zero,50f));
			RenderingComponent rendComp = new RenderingComponent();
			rendComp.setTexture(ContentManager.loadTexture("house.png"));
			PhysicsComponent physComp = new PhysicsComponent();
			physComp.setMass(100f);
			transComp.setOrigin(new Vector2(rendComp.getTexture().Width / 2,
											rendComp.getTexture().Height / 2));	
			enemy.addComponent(rendComp);
			enemy.addComponent(spatComp);
			enemy.addComponent(transComp);
			enemy.addComponent(physComp);
			
			enemy.refresh();
		}
	}
}
