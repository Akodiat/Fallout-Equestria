package tests;

import math.Vector2;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import components.ActionPointsComponent;
import components.DeathComp;
import components.HealthComponent;
import components.InputComponent;
import components.PhysicsComponent;
import components.RenderingComponent;
import components.SpatialComponent;
import components.TransformationComp;
import content.ContentManager;
import death.PPDeathAction;
import debuggsystem.DebugAttackRenderSystem;
import debuggsystem.DebugSpatialRenderSystem;

import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entitySystems.*;
import graphics.Color;
import utils.Circle;
import utils.Rectangle;

public class SimpleShootingTest extends AbstractSystemTest {

	public static void main(String[] args) throws LWJGLException {
		new SimpleShootingTest(new Rectangle(0, 0, 800, 600), false).start();
	}

	public SimpleShootingTest(Rectangle screenDim, boolean fullScreen) {
		super(screenDim, fullScreen);
	}

	@Override
	public void initializeSystems() {
		this.tester.addLogicubSystem(new CharacterControllerSystem(this.tester
				.getWorld()));
		this.tester.addLogicubSystem(new InputSystem(this.tester.getWorld()));
		this.tester.addLogicubSystem(new PhysicsSystem(this.tester.getWorld()));
		this.tester.addLogicubSystem(new AttackResolveSystem(this.tester
				.getWorld()));
		this.tester.addLogicubSystem(new RegenSystem(this.tester.getWorld()));
		this.tester.addRenderSubSystem(new HealthBarRenderSystem(this.tester
				.getWorld(), this.graphics));
		this.tester.addRenderSubSystem(new RenderingSystem(this.tester
				.getWorld(), this.graphics));
		this.tester.addRenderSubSystem(new HUDRenderingSystem(this.tester
				.getWorld(), this.graphics, "Player"));
		
		this.tester.addLogicubSystem(new DeathSystem(this.tester.getWorld()));

		this.tester.addRenderSubSystem(new DebugSpatialRenderSystem(this.tester
				.getWorld(), this.graphics));
		this.tester.addRenderSubSystem(new DebugAttackRenderSystem(this.tester
				.getWorld(), this.graphics));
		
		this.tester.addLogicubSystem(new DeathResolveSystem(this.tester.getWorld()));

	}

	@Override
	public void initializeEntities(IEntityManager manager) {
		IEntity player = manager.createEmptyEntity();
		player.addToGroup("Enemies");
		player.setLabel("Player");
		PhysicsComponent physComp = new PhysicsComponent();
		InputComponent inpComp = new InputComponent();
		DeathComp deathComp = new DeathComp();
		deathComp.addDeathAction(new PPDeathAction());
		ActionPointsComponent apComp = new ActionPointsComponent(100, 100, 10f);
		
		SpatialComponent spatComp = new SpatialComponent(new Circle(
				Vector2.Zero, 30f));
		RenderingComponent rendComp = new RenderingComponent();
		HealthComponent healthComp = new HealthComponent(100, 2, 89);

		rendComp.setColor(new Color(42, 200, 255, 255));
		rendComp.setTexture(ContentManager.loadTexture("HEJHEJ.png"));

		TransformationComp posComp = new TransformationComp(new Vector2(
				Display.getHeight() / 2, Display.getWidth() / 2), Vector2.One,
				0, new Vector2(rendComp.getTexture().Width / 2,
						rendComp.getTexture().Height / 2));

		player.addComponent(rendComp);
		player.addComponent(inpComp);
		player.addComponent(physComp);
		player.addComponent(posComp);
		player.addComponent(spatComp);
		player.addComponent(healthComp);
		player.addComponent(deathComp);
		player.addComponent(apComp);

		player.refresh();
	}
}
