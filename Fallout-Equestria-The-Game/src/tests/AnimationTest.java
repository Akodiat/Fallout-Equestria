package tests;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import math.Vector2;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import ability.Ability;
import ability.BulletAbility;
import ability.CircleProjectileAbility;

import com.google.common.collect.ImmutableList;

import components.*;
import content.ContentManager;
import death.PPDeathAction;
import debugsystems.DebugAttackRenderSystem;
import debugsystems.DebugSpatialRenderSystem;
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

			Timer.updateTimers(1f/60f);
			
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
		tester.addLogicSubSystem(new CharacterControllerSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new InputSystem(this.tester.getWorld(), this.camera));
		tester.addLogicSubSystem(new PhysicsSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new CollisionSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new CameraControlSystem(this.tester.getWorld(), camera));
		tester.addLogicSubSystem(new AttackResolveSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new RegenSystem(this.tester.getWorld(), 0.5f));
		tester.addLogicSubSystem(new MapCollisionSystem(this.tester.getWorld(), new Vector2(this.camera.worldBounds.Width, this.camera.worldBounds.Height)));
		tester.addLogicSubSystem(new ExistanceSystem(this.tester.getWorld()));
		tester.addRenderSubSystem(new HealthBarRenderSystem(this.tester.getWorld(), this.graphics));
		tester.addRenderSubSystem(new AnimationSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new DeathSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new DeathResolveSystem(this.tester.getWorld()));
		tester.addRenderSubSystem(new RenderingSystem(this.tester.getWorld(), this.graphics));
		tester.addRenderSubSystem(new DebugAttackRenderSystem(this.tester.getWorld(), this.graphics));
		tester.addRenderSubSystem(new DebugSpatialRenderSystem(this.tester.getWorld(),this.graphics));
		tester.addRenderSubSystem(new HUDRenderingSystem(this.tester.getWorld(), this.graphics, "Player"));
 
	}

	public void initializeEntities(IEntityManager manager) {

		IEntity player = manager.createEmptyEntity();
		player.addToGroup("Enemies");
		player.addToGroup(CameraControlSystem.GROUP_NAME);

		player.setLabel("Player");
		PhysicsComp physComp = new PhysicsComp();
		InputComp inpComp = new InputComp();
		TransformationComp posComp = new TransformationComp();
		posComp.setPosition(new Vector2(Display.getHeight()/2,Display.getWidth()/2));
		SpatialComp spatComp = new SpatialComp(new Circle(Vector2.Zero,30f));

		HealthComp healthComp = new HealthComp(100, 2, 89);
		AbilityPointsComp apComp = new AbilityPointsComp();
		DeathComp deathComp = new DeathComp();
		deathComp.addDeathAction(new PPDeathAction());
		
		WeaponComp weaponComp = new WeaponComp();
		Ability primaryAbility = new CircleProjectileAbility(ContentManager.loadArchetype("spinProjectile.archetype"),30,1,5,20);
		weaponComp.setPrimaryAbility(primaryAbility);

		//ANIMATIONCOMPONENT
		
		/*Frame frame1 = new Frame(new Rectangle(0,0,44,38),new Vector2(22,19), false);
		Frame frame2 = new Frame(new Rectangle(44,0,44,38),new Vector2(22,19), false);
		Frame frame3 = new Frame(new Rectangle(92,0,40,38),new Vector2(22,19), false);*/
		ImmutableList<Frame> frames = Frame.generateFrames(new Vector2(0,0), new Vector2(83 + 1.0f/3.0f, 75), 24, true);
		Animation animation = new Animation(frames, 0.1f);
		Map<String, Animation> animations = new HashMap<String, Animation>();
		animations.put("walk", animation);
		AnimationComp aniComp = new AnimationComp(animations,"walk");

		//rendcomp
		RenderingComp rendComp = new RenderingComp();
		rendComp.setColor(Color.White);
		rendComp.setTexture(ContentManager.loadTexture("pinkiewalkweaponspriteSCALED.png"));

		player.addComponent(rendComp);
		player.addComponent(aniComp);
		player.addComponent(inpComp);
		player.addComponent(new StatusComp());
		player.addComponent(physComp);
		player.addComponent(posComp);
		player.addComponent(spatComp);
		player.addComponent(healthComp);
		player.addComponent(apComp);
		player.addComponent(deathComp);
		player.addComponent(weaponComp);
		player.refresh();
/*
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
		


		generateRandomEnemies(manager);*/
	}

	private void generateRandomEnemies(IEntityManager manager) {
		for (int i = 0; i < this.numEnemies; i++) {
			IEntity enemy = manager.createEmptyEntity();
			enemy.setLabel("Enemy" + i);

			TransformationComp transComp = new TransformationComp();
			transComp.setPosition((float)Math.random() * 10000,(float)Math.random() * 10000);
			SpatialComp spatComp = new SpatialComp(new Circle(Vector2.Zero,50f));
			RenderingComp rendComp = new RenderingComp();
			rendComp.setTexture(ContentManager.loadTexture("house.png"));
			PhysicsComp physComp = new PhysicsComp();
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
