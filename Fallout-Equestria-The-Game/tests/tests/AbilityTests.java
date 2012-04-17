package tests;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import ability.Abilities;
import ability.AbilityBuilder;
import ability.AbilityInfo;
import ability.AbilitySystem;

import com.google.inject.Guice;
import com.google.inject.Injector;

import components.InputComp;
import components.ScriptComp;
import components.TransformationComp;
import components.WeaponComp;

import content.ContentManager;
import debugsystems.AbilityDebugLogicSystem;
import debugsystems.AbilityDebugRenderSystem;
import debugsystems.DebugAttackRenderSystem;
import debugsystems.DebugSpatialRenderSystem;

import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entityFramework.IEntityWorld;
import scripting.PlayerScript;
import utils.Camera2D;
import utils.Clock;
import utils.GameTime;
import utils.Rectangle;
import graphics.Color;
import graphics.SpriteBatch;
import entitySystems.*;

public class AbilityTests {
	
	private static final int FPS = 60;
	private static final String playerAsset = "Player.archetype";
	private static final Rectangle screenBounds = new Rectangle(0,0,1280,720);
	private static final Rectangle worldBounds = new Rectangle(0,0,1280 * 2,720 * 2);
	
	
	private SpriteBatch spriteBatch;
	private Camera2D camera;
	private IEntityWorld world;
	private Clock clock;
	
	public AbilityTests() {

	}

	private void start() {
		this.initialize();
		
		while(!Display.isCloseRequested()) {
			this.update();
			this.draw();
			this.world.getEntityManager().destoryKilledEntities();
			
			Display.update();
			Display.sync(FPS);
		}
	}

	private void initialize() {
		this.spriteBatch = new SpriteBatch(screenBounds);
		this.camera = new Camera2D(worldBounds, screenBounds);
		this.clock = new Clock();
		
		Injector injector = Guice.createInjector(new EntityModule());
		this.world = injector.getInstance(IEntityWorld.class);

		initializeEntitysystems(this.world.getSystemManager());
		initializePlayer(this.world.getEntityManager());
		
	}


	private void initializePlayer(IEntityManager entityManager) {
		IEntityArchetype archetype = ContentManager.loadArchetype(playerAsset);
		IEntity entity = entityManager.createEntity(archetype);
		entity.addComponent(new ScriptComp(new PlayerScript()));
		entity.addToGroup(CameraControlSystem.GROUP_NAME);
		entity.refresh();
		
		//This cannot be stored in an abstract entity. :)
		TransformationComp trans = entity.getComponent(TransformationComp.class);
		trans.setPosition(screenBounds.getCenter());
		
		WeaponComp weapon = entity.getComponent(WeaponComp.class);
		weapon.setPrimaryAbility(new AbilityInfo(Abilities.None));
		
		//IEntity entity2 = entityManager.createEntity(ContentManager.loadArchetype("BasicAI.archetype"));
		//entity2.removeComponent(InputComp.class);
	}
	
	private void initializeEntitysystems(IEntitySystemManager systemManager) {
		//Logic systems!
		systemManager.addLogicEntitySystem(new AbilitySystem(world, AbilityBuilder.build()));
		systemManager.addLogicEntitySystem(new AnimationSystem(world));
		systemManager.addLogicEntitySystem(new RegenSystem(world, 0.4f));
		systemManager.addLogicEntitySystem(new AttackResolveSystem(world));
		systemManager.addLogicEntitySystem(new CameraControlSystem(world, camera));
		systemManager.addLogicEntitySystem(new CollisionSystem(world));
		systemManager.addLogicEntitySystem(new DeathSystem(world));
		systemManager.addLogicEntitySystem(new ExistanceSystem(world));
		systemManager.addLogicEntitySystem(new InputSystem(world, camera));
		systemManager.addLogicEntitySystem(new PhysicsSystem(world));
		systemManager.addLogicEntitySystem(new ScriptSystem(world));
		
		//Debug logic systems!
		systemManager.addLogicEntitySystem(new AbilityDebugLogicSystem(world, "Player"));
		
		//Rendering systems!
		systemManager.addRenderEntitySystem(new RenderingSystem(world, this.spriteBatch));
		systemManager.addRenderEntitySystem(new TextRenderingSystem(world, spriteBatch));
		systemManager.addRenderEntitySystem(new HUDRenderingSystem(world, spriteBatch, "Player"));
		
		//Debugg systems!
		systemManager.addRenderEntitySystem(new DebugAttackRenderSystem(world, this.spriteBatch));
		systemManager.addRenderEntitySystem(new DebugSpatialRenderSystem(world, this.spriteBatch));
		systemManager.addRenderEntitySystem(new AbilityDebugRenderSystem(world, "Player", this.spriteBatch));
		
		systemManager.initialize();
	}
	
	private void update() {
		this.clock.update();
		GameTime time = this.clock.getGameTime();
		this.world.update(time);		
	}

	private void draw() {
		this.spriteBatch.clearScreen(Color.CornflowerBlue);
		this.spriteBatch.begin(null, camera.getTransformation());
		this.world.render();
		this.spriteBatch.end();
	}
	
	public static void main(String[] args) {
		AbilityTests inst = new AbilityTests();
		try {
			Display.setDisplayMode(new DisplayMode(screenBounds.Width, screenBounds.Height));
			Display.create();
			inst.start();
		} catch(Exception e) {
			System.out.println("Something is very wrong!\n\n");
			e.printStackTrace();
		} finally {
			Display.destroy();
			System.exit(0);
		}
	}
}
