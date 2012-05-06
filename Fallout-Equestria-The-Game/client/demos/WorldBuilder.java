package demos;

import content.ContentManager;
import debugsystems.DebugSpatialRenderSystem;
import debugsystems.DebuggMapCollisionGrid;
import entityFramework.*;
import entitySystems.*;
import gameMap.Scene;
import graphics.SpriteBatch;
import utils.Camera2D;

public class WorldBuilder {

	public static IEntityWorld buildGameWorld(Camera2D camera, Scene scene,ContentManager contentManager, SpriteBatch spriteBatch, boolean debugging) {
		IEntityWorld world = buildEmptyWorld();
		IEntitySystemManager manager = world.getSystemManager();
		
		
		manager.addLogicEntitySystem(new ScriptSystem(world, contentManager));
		manager.addLogicEntitySystem(new ScriptCollisionSystem(world));
		
		
		//Logic systems!
		manager.addLogicEntitySystem(new RegenSystem(world, 0.4f));
		manager.addLogicEntitySystem(new CameraControlSystem(world, camera));
		manager.addLogicEntitySystem(new InputSystem(world, camera));
		manager.addLogicEntitySystem(new PhysicsSystem(world));
		manager.addLogicEntitySystem(new MapCollisionSystem(world, scene));
		manager.addLogicEntitySystem(new TimerSystem(world));
		
		
		//Rendering systems!
		manager.addRenderEntitySystem(new SceneRenderSystem(world, scene, spriteBatch, camera));
		manager.addRenderEntitySystem(new RenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new TextRenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new HUDRenderingSystem(world, contentManager, spriteBatch, "Player"));
		manager.addRenderEntitySystem(new AnimationSystem(world, spriteBatch, camera));
		

		//Debug systems!
		if(debugging) {
			manager.addRenderEntitySystem(new DebuggMapCollisionGrid(world, scene, spriteBatch,camera));
			manager.addRenderEntitySystem(new DebugSpatialRenderSystem(world,contentManager, spriteBatch));
		}
	
		return world;
	}
	public static IEntityWorld buildServerWorld(Camera2D camera, Scene scene, ContentManager contentManager, SpriteBatch spriteBatch, boolean debugging, String label) {
		IEntityWorld world = buildEmptyWorld();
		IEntitySystemManager manager = world.getSystemManager();
		
		
		manager.addLogicEntitySystem(new ScriptSystem(world, contentManager));
		manager.addLogicEntitySystem(new ScriptCollisionSystem(world));
		
		
		//Logic systems!
		manager.addLogicEntitySystem(new RegenSystem(world, 0.4f));
		manager.addLogicEntitySystem(new CameraControlSystem(world, camera));
		manager.addLogicEntitySystem(new ServerInputSystem(world, camera, label));
		manager.addLogicEntitySystem(new PhysicsSystem(world));
		manager.addLogicEntitySystem(new MapCollisionSystem(world, scene));
		manager.addLogicEntitySystem(new CollisionSystem(world));
		
		
		//Rendering systems!
		manager.addRenderEntitySystem(new SceneRenderSystem(world, scene, spriteBatch, camera));
		manager.addRenderEntitySystem(new RenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new TextRenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new HUDRenderingSystem(world, contentManager, spriteBatch, label));
		manager.addRenderEntitySystem(new AnimationSystem(world, spriteBatch, camera));
		

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
