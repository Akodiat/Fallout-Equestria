package tests;

import math.Vector2;

import org.lwjgl.opengl.Display;

import components.ActionPointsComponent;
import components.BasicAIComp;
import components.DeathComp;
import components.HealthComponent;
import components.InputComponent;
import components.PhysicsComponent;
import components.RenderingComponent;
import components.SpatialComponent;
import components.TransformationComp;
import content.ContentManager;

import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entitySystems.AttackResolveSystem;
import entitySystems.BasicAISystem;
import entitySystems.CameraControlSystem;
import entitySystems.CharacterControllerSystem;
import entitySystems.CollisionSystem;
import entitySystems.DeathSystem;
import entitySystems.DebugAttackRenderSystem;
import entitySystems.DebugSpatialRenderSystem;
import entitySystems.HUDRenderingSystem;
import entitySystems.HealthBarRenderSystem;
import entitySystems.InputSystem;
import entitySystems.PhysicsSystem;
import entitySystems.RegenSystem;
import entitySystems.RenderingSystem;
import entitySystems.StatusChangeSystem;
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
		tester.addLogicubSystem(new StatusChangeSystem(this.tester.getWorld()));
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
		for (int i = 0; i < this.numEnemies; i++) {
			IEntity enemy = manager.createEmptyEntity();
			enemy.setLabel("Enemy" + i);
			enemy.addToGroup("Enemies");
			TransformationComp transComp = new TransformationComp();
			transComp.setPosition((float)Math.random() * 800,(float)Math.random() * 600);
			SpatialComponent spatComp = new SpatialComponent(new Circle(Vector2.Zero,50f));
			HealthComponent healthComp = new HealthComponent(50,1,50);
			DeathComp deathComp = new DeathComp();
			RenderingComponent rendComp = new RenderingComponent();
			rendComp.setTexture(ContentManager.loadTexture("HEJHEJ.png"));
			rendComp.setColor(Color.Red);
			PhysicsComponent physComp = new PhysicsComponent();
			transComp.setOrigin(new Vector2(rendComp.getTexture().Width / 2,
											rendComp.getTexture().Height / 2));	
			BasicAIComp AIComp = new BasicAIComp();
			
			enemy.addComponent(rendComp);
			enemy.addComponent(spatComp);
			enemy.addComponent(healthComp);
			enemy.addComponent(deathComp);
			enemy.addComponent(transComp);
			enemy.addComponent(physComp);
			enemy.addComponent(AIComp);
			
			enemy.refresh();
		}
	}

}
