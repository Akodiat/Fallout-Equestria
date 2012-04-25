package demos;

import ability.AbilityBuilder;
import ability.AbilitySystem;

import com.google.inject.Guice;
import com.google.inject.Injector;

import debugsystems.AbilityDebugLogicSystem;
import debugsystems.DebugAttackRenderSystem;
import debugsystems.DebugSpatialRenderSystem;
import debugsystems.DebuggMapCollisionGrid;

import entityFramework.IEntityDatabase;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entityFramework.IEntityWorld;
import entitySystems.AnimationSystem;
import entitySystems.CameraControlSystem;
import entitySystems.DeathSystem;
import entitySystems.ExistanceSystem;
import entitySystems.HUDRenderingSystem;
import entitySystems.InputSystem;
import entitySystems.MapCollisionSystem;
import entitySystems.PhysicsSystem;
import entitySystems.RegenSystem;
import entitySystems.RenderingSystem;
import entitySystems.SceneRenderSystem;
import entitySystems.ScriptCollisionSystem;
import entitySystems.ScriptMouseSystem;
import entitySystems.ScriptSystem;
import entitySystems.TextRenderingSystem;
import gameMap.Scene;
import graphics.SpriteBatch;
import tests.EntityModule;
import utils.Camera2D;

public class WorldBuilder {

	public static IEntityWorld buildGameWorld(Camera2D camera, Scene scene, SpriteBatch spriteBatch, boolean debugging) {
		IEntityWorld world = buildEmptyWorld();
		IEntitySystemManager manager = world.getSystemManager();
		
		
		manager.addLogicEntitySystem(new ScriptSystem(world));
		manager.addLogicEntitySystem(new ScriptCollisionSystem(world));
		manager.addLogicEntitySystem(new ScriptMouseSystem(world, camera));
		
		
		//Logic systems!
		manager.addLogicEntitySystem(new AbilitySystem(world, AbilityBuilder.build()));
		manager.addLogicEntitySystem(new RegenSystem(world, 0.4f));
		manager.addLogicEntitySystem(new CameraControlSystem(world, camera));
		manager.addLogicEntitySystem(new ExistanceSystem(world));
		manager.addLogicEntitySystem(new InputSystem(world, camera));
		manager.addLogicEntitySystem(new PhysicsSystem(world));
		manager.addLogicEntitySystem(new MapCollisionSystem(world, scene));
		
		
		//Rendering systems!
		manager.addRenderEntitySystem(new SceneRenderSystem(world, scene, spriteBatch, camera));
		manager.addRenderEntitySystem(new RenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new TextRenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new HUDRenderingSystem(world, spriteBatch, "Player"));
		manager.addRenderEntitySystem(new AnimationSystem(world, spriteBatch, camera));
		

		//Debug systems!
		if(debugging) {
			manager.addRenderEntitySystem(new DebugAttackRenderSystem(world, spriteBatch));
			manager.addRenderEntitySystem(new DebuggMapCollisionGrid(world, scene, spriteBatch,camera));
			manager.addRenderEntitySystem(new DebugSpatialRenderSystem(world, spriteBatch));
		}
	
		return world;
	}

	
	public static IEntityWorld buildEmptyWorld() {
		Injector injector = Guice.createInjector(new EntityModule());
		return injector.getInstance(IEntityWorld.class);
	}
}
