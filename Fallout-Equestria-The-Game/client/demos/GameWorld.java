package demos;
import utils.Camera2D;
import utils.Clock;
import utils.GameTime;
import ability.AbilityBuilder;
import ability.AbilitySystem;
import debugsystems.AbilityDebugLogicSystem;
import debugsystems.AbilityDebugRenderSystem;
import debugsystems.DebugAttackRenderSystem;
import debugsystems.DebugSpatialRenderSystem;
import entityFramework.EntityWorld;
import entityFramework.IEntityDatabase;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entitySystems.*;
import gameMap.Scene;
import graphics.SpriteBatch;


public class GameWorld extends EntityWorld {

	private final Camera2D camera;
	private final SpriteBatch spriteBatch;
	private final Scene scene;
	
	
	public GameWorld(IEntityManager entityManager,
			IEntitySystemManager systemManager, 
			IEntityDatabase database, 
			Camera2D camera, 
			SpriteBatch spriteBatch, 
			Scene scene) {
		super(entityManager, systemManager, database);
		this.camera 	 = camera;
		this.spriteBatch = spriteBatch;
		this.scene 		 = scene;
	}
	
	@Override
	public void initialize() {
		IEntitySystemManager manager = this.getSystemManager();
		
		manager.addLogicEntitySystem(new AnimationSystem(this));
		
		//Logic systems!
		manager.addLogicEntitySystem(new AbilitySystem(this, AbilityBuilder.build()));
		manager.addLogicEntitySystem(new AnimationSystem(this));
		manager.addLogicEntitySystem(new RegenSystem(this, 0.4f));
		manager.addLogicEntitySystem(new AttackResolveSystem(this));
		manager.addLogicEntitySystem(new CameraControlSystem(this, camera));
		manager.addLogicEntitySystem(new CollisionSystem(this));
		manager.addLogicEntitySystem(new CharacterControllerSystem(this));
		manager.addLogicEntitySystem(new DeathSystem(this));
		manager.addLogicEntitySystem(new ExistanceSystem(this));
		manager.addLogicEntitySystem(new InputSystem(this, camera));
		manager.addLogicEntitySystem(new MapCollisionSystem(this, camera.getWorldBounds()));
		manager.addLogicEntitySystem(new PhysicsSystem(this));
		
		//Debug logic systems!
		manager.addLogicEntitySystem(new AbilityDebugLogicSystem(this, "Player"));
		
		//Rendering systems!
		manager.addRenderEntitySystem(new SceneRenderSystem(this, scene, spriteBatch, camera));
		manager.addRenderEntitySystem(new RenderingSystem(this, spriteBatch));
		manager.addRenderEntitySystem(new TextRenderingSystem(this, spriteBatch));
		manager.addRenderEntitySystem(new HUDRenderingSystem(this, spriteBatch, "Player"));
		
		
		//Debugg systems!
		manager.addRenderEntitySystem(new DebugAttackRenderSystem(this, spriteBatch));
		manager.addRenderEntitySystem(new DebugSpatialRenderSystem(this, spriteBatch));
				
	}
}
