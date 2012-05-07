package demos;

import misc.SoundManager;
import content.ContentManager;
import debugsystems.DebugSpatialRenderSystem;
import debugsystems.DebuggMapCollisionGrid;
import entityFramework.*;
import entitySystems.*;
import gameMap.Scene;
import graphics.SpriteBatch;
import utils.Camera2D;
import utils.Keyboard;
import utils.Mouse;

public class WorldBuilder {

	public static IEntityWorld buildGameWorld(Camera2D camera, Scene scene, Mouse mouse, Keyboard keyboard, 
											   ContentManager contentManager,SoundManager soundManager, 
											   SpriteBatch spriteBatch, boolean debugging) {
		IEntityWorld world = buildEmptyWorld();
		IEntitySystemManager manager = world.getSystemManager();
		
		
		manager.addLogicEntitySystem(new ScriptSystem(world, contentManager, soundManager));
		manager.addLogicEntitySystem(new ScriptCollisionSystem(world));
			
		//Logic systems!
		manager.addLogicEntitySystem(new RegenSystem(world, 1f));
		manager.addLogicEntitySystem(new CameraControlSystem(world, camera));
		manager.addLogicEntitySystem(new InputSystem(world, mouse, keyboard));
		manager.addLogicEntitySystem(new PhysicsSystem(world));
		manager.addLogicEntitySystem(new MapCollisionSystem(world, scene));
		manager.addLogicEntitySystem(new TimerSystem(world));
		manager.addLogicEntitySystem(new AbilitySystem(world));
		manager.addLogicEntitySystem(new DeathSystem(world));
		
		
		//Rendering systems!
		manager.addRenderEntitySystem(new SceneRenderSystem(world, scene, spriteBatch, camera));
		manager.addRenderEntitySystem(new ShadowRenderingSystem(world, contentManager, spriteBatch));
		manager.addRenderEntitySystem(new RenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new TextRenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new AnimationSystem(world, spriteBatch, camera));
		manager.addRenderEntitySystem(new HealthBarRenderSystem(world, contentManager, spriteBatch));

		
		manager.addRenderEntitySystem(new HUDRenderingSystem(world, contentManager, spriteBatch, "Player"));

		//Debug systems!
		if(debugging) {
			manager.addRenderEntitySystem(new DebuggMapCollisionGrid(world, scene, spriteBatch,camera));
			manager.addRenderEntitySystem(new DebugSpatialRenderSystem(world,contentManager, spriteBatch));
		}
	
		return world;
	}
	public static IEntityWorld buildServerWorld(Camera2D camera, Scene scene,  Mouse mouse, Keyboard keyboard, 
												ContentManager contentManager,SoundManager soundManager, 
												SpriteBatch spriteBatch, boolean debugging, String label) {
		IEntityWorld world = buildEmptyWorld();
		IEntitySystemManager manager = world.getSystemManager();
		

		
		
		
		//Logic systems!
		manager.addLogicEntitySystem(new RegenSystem(world, 1f));
		manager.addLogicEntitySystem(new CameraControlSystem(world, camera));
		manager.addLogicEntitySystem(new ServerInputSystem(world,mouse, keyboard, label));
		manager.addLogicEntitySystem(new PhysicsSystem(world));
		manager.addLogicEntitySystem(new MapCollisionSystem(world, scene));
		manager.addLogicEntitySystem(new CollisionSystem(world));
		manager.addLogicEntitySystem(new TimerSystem(world));
		manager.addLogicEntitySystem(new DeathSystem(world));
		
		manager.addLogicEntitySystem(new ScriptSystem(world, contentManager,soundManager));
		manager.addLogicEntitySystem(new ScriptCollisionSystem(world));
		
		//Rendering systems!
		manager.addRenderEntitySystem(new SceneRenderSystem(world, scene, spriteBatch, camera));
		manager.addRenderEntitySystem(new ShadowRenderingSystem(world, contentManager, spriteBatch));
		manager.addRenderEntitySystem(new RenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new TextRenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new AnimationSystem(world, spriteBatch, camera));
		manager.addRenderEntitySystem(new HealthBarRenderSystem(world, contentManager, spriteBatch));
		manager.addRenderEntitySystem(new HUDRenderingSystem(world, contentManager, spriteBatch, label));		

		//Debug systems!
		if(debugging) {
			manager.addRenderEntitySystem(new DebuggMapCollisionGrid(world, scene, spriteBatch,camera));
			manager.addRenderEntitySystem(new DebugSpatialRenderSystem(world,contentManager, spriteBatch));
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
