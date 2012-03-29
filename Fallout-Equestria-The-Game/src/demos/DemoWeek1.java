package demos;

import java.io.IOException;

import math.Vector2;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import ability.BulletAbility;

import tests.SystemTester;
import utils.Camera2D;
import utils.Circle;
import utils.Rectangle;
import utils.Timer;

import components.*;
import content.ContentManager;
import death.CorpseDeathAction;
import debugsystems.DebugAttackRenderSystem;
import debugsystems.DebugSpatialRenderSystem;

import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;
import entitySystems.*;
import gameMap.MapTester;
import graphics.Color;
import graphics.SpriteBatch;

public class DemoWeek1 {
	private SystemTester tester;
	private SpriteBatch graphics;
	private final Rectangle screenDim;
	private final boolean isFullScreen;
	private MapTester mapTester;
	private Camera2D camera;
	
	private final int numEnemies = 400;
	
	public static void main(String[] args) throws IOException, LWJGLException{
		new DemoWeek1(new Rectangle(0,0, 1366, 768), true).start();
	}

	public DemoWeek1(Rectangle screenDim, boolean fullScreen) throws IOException{
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
		this.camera = new Camera2D(new Rectangle(0,0,2000, 2000), screenDim);
		initializeSystems();
		initializeEntities(this.tester.getWorld().getEntityManager());

		tester.startTesting();

		while(!Display.isCloseRequested()) {
			tester.updateWorld(1.0f / 30f);
			Timer.updateTimers(1.0f / 30f);

			graphics.clearScreen(new Color(157, 150, 101, 255));
			graphics.begin(null, camera.getTransformation());
			
			mapTester.draw();
			tester.renderWorld();
			
			graphics.end();

			tester.getWorld().getEntityManager().destoryKilledEntities();

			Display.update();
			Display.sync(30);
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
		tester.addLogicSubSystem(new AnimationSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new ExistanceSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new DeathResolveSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new DeathSystem(this.tester.getWorld()));
		tester.addRenderSubSystem(new HealthBarRenderSystem(this.tester.getWorld(), this.graphics));
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
		SpatialComp spatComp = new SpatialComp(new Circle(Vector2.Zero,10f));
		RenderingComp rendComp = new RenderingComp();
		HealthComp healthComp = new HealthComp(100, 2, 89);
		AbilityPointsComp apComp = new AbilityPointsComp(100, 50, 15.0f);
		
		IEntityArchetype archetype = ContentManager.loadArchetype("ppieBullet.archetype");
		
		WeaponComp weapon = new WeaponComp(new BulletAbility(archetype, 13, 0.3f, 5f), null);

		rendComp.setColor(new Color(42,200,255, 255));
		rendComp.setTexture(ContentManager.loadTexture("PPieLauncher.png"));
		

		posComp.setOrigin(new Vector2(rendComp.getTexture().Width / 2,
										rendComp.getTexture().Height / 2));	
		
		DeathComp deathComp = new DeathComp();
		deathComp.addDeathAction(new CorpseDeathAction(ContentManager.loadTexture("HEJHEJDEAD.png")));
		
		player.addComponent(rendComp);
		player.addComponent(inpComp);
		player.addComponent(physComp);
		player.addComponent(new StatusComp());
		player.addComponent(posComp);
		player.addComponent(spatComp);
		player.addComponent(healthComp);
		player.addComponent(apComp);
		player.addComponent(weapon);
		player.addComponent(deathComp);

		player.refresh();
		
		IEntity house = manager.createEmptyEntity();
		house.setLabel("House");
		TransformationComp housePosComp = new TransformationComp();
		housePosComp.setPosition(1337, 1337);
		SpatialComp houseSpatComp = new SpatialComp(new Circle(Vector2.Zero,50f));
		RenderingComp houseRendComp = new RenderingComp();
		houseRendComp.setTexture(ContentManager.loadTexture("house.png"));
		PhysicsComp housePhysComp = new PhysicsComp();
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
