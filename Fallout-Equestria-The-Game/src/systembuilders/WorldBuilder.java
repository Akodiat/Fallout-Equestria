package systembuilders;

import sounds.SoundManager;
import utils.Camera2D;
import utils.input.Keyboard;
import utils.input.Mouse;
import common.NetworkedEntityFactory;

import content.ContentManager;
import debugsystems.DebugSpatialRenderSystem;
import debugsystems.DebuggMapCollisionGrid;
import entityFramework.*;
import entitySystems.*;
import gameMap.Scene;
import graphics.SpriteBatch;

public class WorldBuilder {

	public static IEntityWorld buildGameWorld(Camera2D camera, Scene scene, Mouse mouse, Keyboard keyboard, 
											   ContentManager contentManager,SoundManager soundManager, 
											   SpriteBatch spriteBatch, boolean debugging, String label) {
		IEntityWorld world = buildEmptyWorld();
		IEntitySystemManager manager = world.getSystemManager();
		
		
		manager.addLogicEntitySystem(new ScriptSystem(world, contentManager, soundManager));
		manager.addLogicEntitySystem(new ScriptCollisionSystem(world));
			
		//Logic systems!
		manager.addLogicEntitySystem(new RegenSystem(world, 1f));
		manager.addLogicEntitySystem(new CameraControlSystem(world, camera));
		manager.addLogicEntitySystem(new InputSystem(world, mouse, keyboard));
		manager.addLogicEntitySystem(new PhysicsSystem(world, scene.getHeightMap()));
		manager.addLogicEntitySystem(new MapCollisionSystem(world, scene));
		manager.addLogicEntitySystem(new TimerSystem(world));
		manager.addLogicEntitySystem(new AbilitySystem(world));
		manager.addLogicEntitySystem(new DeathSystem(world));
		
		
		//Rendering systems!
		manager.addRenderEntitySystem(new SceneRenderSystem(world, scene, spriteBatch, camera));
		manager.addRenderEntitySystem(new ShadowRenderingSystem(world, contentManager, spriteBatch, scene.getHeightMap()));
		manager.addRenderEntitySystem(new RenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new TextRenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new AnimationSystem(world, spriteBatch, camera));
		manager.addRenderEntitySystem(new HealthBarRenderSystem(world, contentManager, spriteBatch));

		
		manager.addRenderEntitySystem(new HUDRenderingSystem(world, contentManager, spriteBatch, "Player"));

		//Debug systems!
		if(debugging) {
			manager.addRenderEntitySystem(new DebuggMapCollisionGrid(world, scene, spriteBatch,camera));
			manager.addRenderEntitySystem(new DebugSpatialRenderSystem(world, spriteBatch));
		}
	
		return world;
	}
	
	public static IEntityWorld buildClientWorld(Camera2D camera, Scene scene,  Mouse mouse, Keyboard keyboard, 
			ContentManager contentManager,SoundManager soundManager, 
			SpriteBatch spriteBatch, boolean debugging, String label) {
		
		IEntityWorld world = buildEmptyWorld();
		IEntitySystemManager manager = world.getSystemManager();
		
		
		manager.addLogicEntitySystem(new ServerInputSystem(world, mouse, keyboard, label));
		manager.addLogicEntitySystem(new CameraControlSystem(world, camera));
		
		
		manager.addRenderEntitySystem(new SceneRenderSystem(world, scene, spriteBatch, camera));
		manager.addRenderEntitySystem(new ShadowRenderingSystem(world, contentManager, spriteBatch, scene.getHeightMap()));
		manager.addRenderEntitySystem(new RenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new TextRenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new AnimationSystem(world, spriteBatch, camera));
		manager.addRenderEntitySystem(new HealthBarRenderSystem(world, contentManager, spriteBatch));
		manager.addRenderEntitySystem(new HUDRenderingSystem(world, contentManager, spriteBatch, label));		
		
		if(debugging) {
			manager.addRenderEntitySystem(new DebuggMapCollisionGrid(world, scene, spriteBatch,camera));
			manager.addRenderEntitySystem(new DebugSpatialRenderSystem(world, spriteBatch));
		}
		
		return world;
		
	}
	
	public static IEntityWorld buildEmptyNetworkedWorld(ContentManager contentManager) {
		IEntityDatabase database = new EntityDatabase(new ComponentTypeManager());
		IEntityManager entityManager = new EntityManager(new EntityLabelManager(), 
												   new EntityGroupManager(), 
												   new NetworkedEntityFactory(contentManager), database);
		IEntitySystemManager manager = new EntitySystemManager(entityManager);

		return new EntityWorld(entityManager, manager, database);
	}
	
	
	public static IEntityWorld buildServerWorld(Camera2D camera, Scene scene,  Mouse mouse, Keyboard keyboard, 
												ContentManager contentManager,SoundManager soundManager, 
												SpriteBatch spriteBatch, boolean debugging, String label) {

		IEntityDatabase database = new EntityDatabase(new ComponentTypeManager());
		IEntityManager entityManager = new EntityManager(new EntityLabelManager(), 
												   new EntityGroupManager(), 
												   new NetworkedEntityFactory(contentManager), database);
		IEntitySystemManager manager = new EntitySystemManager(entityManager);

		IEntityWorld world = new EntityWorld(entityManager, manager, database);
		
		
		manager.addLogicEntitySystem(new ScriptSystem(world, contentManager, soundManager));
		manager.addLogicEntitySystem(new ScriptCollisionSystem(world));
			
		//Logic systems!
		manager.addLogicEntitySystem(new RegenSystem(world, 1f));
		manager.addLogicEntitySystem(new CameraControlSystem(world, camera));
		manager.addLogicEntitySystem(new ServerInputSystem(world, mouse, keyboard, label));
		manager.addLogicEntitySystem(new PhysicsSystem(world, scene.getHeightMap()));
		manager.addLogicEntitySystem(new MapCollisionSystem(world, scene));
		manager.addLogicEntitySystem(new TimerSystem(world));
		manager.addLogicEntitySystem(new AbilitySystem(world));
		manager.addLogicEntitySystem(new DeathSystem(world));
		
		
		//Rendering systems!
		manager.addRenderEntitySystem(new SceneRenderSystem(world, scene, spriteBatch, camera));
		manager.addRenderEntitySystem(new ShadowRenderingSystem(world, contentManager, spriteBatch, scene.getHeightMap()));
		manager.addRenderEntitySystem(new RenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new TextRenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new AnimationSystem(world, spriteBatch, camera));
		manager.addRenderEntitySystem(new HealthBarRenderSystem(world, contentManager, spriteBatch));

		
		manager.addRenderEntitySystem(new HUDRenderingSystem(world, contentManager, spriteBatch, label));

		//Debug systems!
		if(debugging) {
			manager.addRenderEntitySystem(new DebuggMapCollisionGrid(world, scene, spriteBatch,camera));
			manager.addRenderEntitySystem(new DebugSpatialRenderSystem(world, spriteBatch));
		}
		
		return world;
	
	}


	public static IEntityWorld buildEmptyWorld() {
		IEntityDatabase database = new EntityDatabase(new ComponentTypeManager());
		IEntityManager manager = new EntityManager(new EntityLabelManager(), 
												   new EntityGroupManager(), 
												   new EntityFactory(), database);
		IEntitySystemManager systemManager = new EntitySystemManager(manager);
		IEntityWorld world = new EntityWorld(manager, systemManager, database);
		return world;
	}
}
