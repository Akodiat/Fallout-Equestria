package utils;

import misc.SoundManager;

import content.ContentManager;
import debugsystems.DebugSpatialRenderSystem;
import debugsystems.DebuggMapCollisionGrid;
import entityFramework.IEntitySystemManager;
import entityFramework.IEntityWorld;
import entitySystems.*;
import gameMap.Scene;
import graphics.SpriteBatch;

public class EntitySystemBuilder {
	
	
	public static void buildServerSystems(IEntityWorld world, Camera2D camera, Scene scene,  Mouse mouse, Keyboard keyboard, 
												ContentManager contentManager,SoundManager soundManager, 
												SpriteBatch spriteBatch, boolean debugging, String label) {
		IEntitySystemManager manager = world.getSystemManager();
	
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
	}

	
	public static IEntityWorld buildClientWorld(IEntityWorld world, Camera2D camera, Scene scene,  Mouse mouse, Keyboard keyboard, 
			ContentManager contentManager,SoundManager soundManager, 
			SpriteBatch spriteBatch, boolean debugging, String label) {
		
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
	
}
