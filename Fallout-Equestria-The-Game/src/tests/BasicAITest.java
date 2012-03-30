package tests;

import math.Vector2;

import org.lwjgl.opengl.Display;

import components.*;
import content.ContentManager;
import debugsystems.DebugAttackRenderSystem;
import debugsystems.DebugSpatialRenderSystem;

import entityFramework.*;
import entitySystems.*;
import graphics.Color;
import utils.Camera2D;
import utils.Circle;
import utils.Rectangle;

public class BasicAITest extends AbstractSystemTest{
	private final int numEnemies = 10;
	private Camera2D camera;
	
	public BasicAITest(Rectangle screenDim, boolean fullScreen) {
		super(screenDim, fullScreen);
		this.camera = new Camera2D(screenDim, screenDim);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new BasicAITest(new Rectangle(0,0, 800, 600), false).start();

	}

	@Override
	public void initializeSystems() {
		tester.addLogicSubSystem(new CharacterControllerSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new InputSystem(this.tester.getWorld(), this.camera));
		tester.addLogicSubSystem(new PhysicsSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new CollisionSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new AttackResolveSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new RegenSystem(this.tester.getWorld(), 0.5f));
		tester.addLogicSubSystem(new BasicAISystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new DeathSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new ExistanceSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new AnimationSystem(this.tester.getWorld()));
		tester.addRenderSubSystem(new HealthBarRenderSystem(this.tester.getWorld(), this.graphics));
		tester.addRenderSubSystem(new RenderingSystem(this.tester.getWorld(), this.graphics));
		tester.addRenderSubSystem(new DebugAttackRenderSystem(this.tester.getWorld(), this.graphics));
		tester.addRenderSubSystem(new DebugSpatialRenderSystem(this.tester.getWorld(),this.graphics));
		tester.addRenderSubSystem(new HUDRenderingSystem(this.tester.getWorld(), this.graphics, "Player"));	
	}

	@Override
	public void initializeEntities(IEntityManager manager) {
		IEntity player = manager.createEmptyEntity();
		
		player.addToGroup("Friends");
		player.setLabel("Player");
		PhysicsComp physComp = new PhysicsComp();
		InputComp inpComp = new InputComp();
		TransformationComp posComp = new TransformationComp();
		posComp.setPosition(new Vector2(Display.getHeight()/2,Display.getWidth()/2));
		SpatialComp spatComp = new SpatialComp(new Circle(Vector2.Zero,30f));
		RenderingComp rendComp = new RenderingComp();
		HealthComp healthComp = new HealthComp(100, 2, 89);
		AbilityComp apComp = new AbilityComp();
		
		WeaponComp weapon = new WeaponComp();

		

		rendComp.setColor(new Color(42,200,255, 255));
		rendComp.setTexture(ContentManager.loadTexture("PPieLauncher.png"));
		
		

		posComp.setOrigin(new Vector2(rendComp.getTexture().Width / 2,
										rendComp.getTexture().Height / 2));	
		
		player.addComponent(rendComp);
		player.addComponent(inpComp);
		player.addComponent(physComp);
		player.addComponent(posComp);
		player.addComponent(weapon);
		player.addComponent(spatComp);
		player.addComponent(healthComp);
		player.addComponent(apComp);

		player.refresh();
		
		generateRandomEnemies(manager);
		
	}
	private void generateRandomEnemies(IEntityManager manager) {
		IEntityArchetype arch = ContentManager.loadArchetype("BasicAI.archetype"); 
		for (int i = 0; i < numEnemies; i++) {
			IEntity enemy = manager.createEntity(arch);
			enemy.setLabel("Enemy" + i);
			enemy.addToGroup("Enemies");	
			
			enemy.refresh();
		}
	}

}
