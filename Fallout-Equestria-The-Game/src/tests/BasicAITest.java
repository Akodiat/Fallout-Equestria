package tests;

import math.Vector2;

import org.lwjgl.opengl.Display;

import ability.BulletAbility;

import components.*;
import content.ContentManager;
import debuggsystem.DebugAttackRenderSystem;
import debuggsystem.DebugSpatialRenderSystem;

import entityFramework.*;
import entitySystems.*;
import graphics.Color;
import utils.Circle;
import utils.Rectangle;

public class BasicAITest extends AbstractSystemTest{
	private final int numEnemies = 10;

	public BasicAITest(Rectangle screenDim, boolean fullScreen) {
		super(screenDim, fullScreen);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new BasicAITest(new Rectangle(0,0, 800, 600), false).start();

	}

	@Override
	public void initializeSystems() {
		tester.addLogicubSystem(new CharacterControllerSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new InputSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new PhysicsSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new CollisionSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new AttackResolveSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new RegenSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new BasicAISystem(this.tester.getWorld()));
		tester.addLogicubSystem(new DeathSystem(this.tester.getWorld()));
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
		PhysicsComponent physComp = new PhysicsComponent();
		InputComponent inpComp = new InputComponent();
		TransformationComp posComp = new TransformationComp();
		posComp.setPosition(new Vector2(Display.getHeight()/2,Display.getWidth()/2));
		SpatialComponent spatComp = new SpatialComponent(new Circle(Vector2.Zero,30f));
		RenderingComponent rendComp = new RenderingComponent();
		HealthComponent healthComp = new HealthComponent(100, 2, 89);
		ActionPointsComponent apComp = new ActionPointsComponent();
		

		rendComp.setColor(new Color(42,200,255, 255));
		rendComp.setTexture(ContentManager.loadTexture("PPieLauncher.png"));
		
		

		posComp.setOrigin(new Vector2(rendComp.getTexture().Width / 2,
										rendComp.getTexture().Height / 2));	
		
		player.addComponent(rendComp);
		player.addComponent(inpComp);
		player.addComponent(physComp);
		player.addComponent(posComp);
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
