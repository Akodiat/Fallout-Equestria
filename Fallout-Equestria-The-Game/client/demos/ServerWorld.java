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
import debugsystems.DebuggMapCollisionGrid;
import entityFramework.EntityWorld;
import entityFramework.IEntityDatabase;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entitySystems.*;
import gameMap.Scene;
import graphics.SpriteBatch;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class ServerWorld extends EntityWorld {

	private final Camera2D camera;
	private final SpriteBatch spriteBatch;
	private final Scene scene;
	private final String label;
	
	
	public ServerWorld(IEntityManager entityManager,
			IEntitySystemManager systemManager, 
			IEntityDatabase database, 
			Camera2D camera, 
			SpriteBatch spriteBatch, 
			Scene scene, String label) {
		super(entityManager, systemManager, database);
		this.camera 	 = camera;
		this.spriteBatch = spriteBatch;
		this.scene 		 = scene;
		this.label		 = label;
	}
	
	@Override
	public void initialize() {
		IEntitySystemManager manager = this.getSystemManager();
		

		
		//Logic systems!
		manager.addLogicEntitySystem(new AbilitySystem(this, AbilityBuilder.build()));
		manager.addLogicEntitySystem(new RegenSystem(this, 0.4f));
		manager.addLogicEntitySystem(new AttackResolveSystem(this));
		manager.addLogicEntitySystem(new CameraControlSystem(this, camera));
		manager.addLogicEntitySystem(new CollisionSystem(this));
		manager.addLogicEntitySystem(new DeathSystem(this));
		manager.addLogicEntitySystem(new ExistanceSystem(this));
		manager.addLogicEntitySystem(new ServerInputSystem(this, camera, this.label));
		manager.addLogicEntitySystem(new MapCollisionSystem(this, this.scene));
		manager.addLogicEntitySystem(new PhysicsSystem(this));
		manager.addLogicEntitySystem(new ScriptSystem(this));
		
		//Debug logic systems!
		manager.addLogicEntitySystem(new AbilityDebugLogicSystem(this, this.label));
		
		//Rendering systems!
		manager.addRenderEntitySystem(new SceneRenderSystem(this, scene, spriteBatch, camera));
		manager.addRenderEntitySystem(new RenderingSystem(this, spriteBatch));
		manager.addRenderEntitySystem(new TextRenderingSystem(this, spriteBatch));
		manager.addRenderEntitySystem(new HUDRenderingSystem(this, spriteBatch, this.label));
		manager.addRenderEntitySystem(new AnimationSystem(this, spriteBatch, camera));
		
		
		//Debugg systems!
		manager.addRenderEntitySystem(new DebugAttackRenderSystem(this, spriteBatch));
		manager.addRenderEntitySystem(new DebuggMapCollisionGrid(this, scene, spriteBatch,camera));
		manager.addRenderEntitySystem(new DebugSpatialRenderSystem(this, spriteBatch));
				
	}
}
