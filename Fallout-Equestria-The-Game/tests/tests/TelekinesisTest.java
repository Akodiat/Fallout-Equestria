package tests;

import java.util.HashMap;

import org.lwjgl.opengl.Display;

import ability.Abilities;
import ability.AbilityBuilder;
import ability.AbilitySystem;
import ability.TelekinesisAbility;

import math.Vector2;

import components.AbilityComp;
import components.HealthComp;
import components.InputComp;
import components.PhysicsComp;
import components.RenderingComp;
import components.SpatialComp;
import components.TransformationComp;
import components.WeaponComp;

import content.ContentManager;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entitySystems.*;
import graphics.Color;
import utils.Camera2D;
import utils.Circle;
import utils.Rectangle;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class TelekinesisTest extends AbstractSystemTest{

	private final int numPickupables = 42;
	private Camera2D camera;

	public TelekinesisTest(Rectangle screenDim, boolean fullScreen) {
		super(screenDim, fullScreen);
		this.camera = new Camera2D(screenDim, screenDim);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TelekinesisTest(new Rectangle(0, 0, 800, 600), false).start();
	}

	@Override
	public void initializeSystems() {
		tester.addLogicSubSystem(new CharacterControllerSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new InputSystem(this.tester.getWorld(), this.camera));
		tester.addLogicSubSystem(new PhysicsSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new CollisionSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new RegenSystem(this.tester.getWorld(), 0.5f));
		tester.addLogicSubSystem(new TelekinesisSystem(this.tester.getWorld()));
		tester.addRenderSubSystem(new HealthBarRenderSystem(this.tester.getWorld(), this.graphics));
		tester.addRenderSubSystem(new RenderingSystem(this.tester.getWorld(), this.graphics));
		tester.addLogicSubSystem(new AbilitySystem(this.tester.getWorld(), AbilityBuilder.build()));
	//	tester.addRenderSubSystem(new DebugAttackRenderSystem(this.tester.getWorld(), this.graphics));
	//	tester.addRenderSubSystem(new DebugSpatialRenderSystem(this.tester.getWorld(),this.graphics));
		tester.addRenderSubSystem(new HUDRenderingSystem(this.tester.getWorld(), this.graphics, "Player"));

	}

	@Override
	public void initializeEntities(IEntityManager manager) {
		IEntity player = manager.createEmptyEntity();

		player.addToGroup("Friends");
	//	player.addToGroup("Pickup-able");
		player.setLabel("Player");
		PhysicsComp physComp = new PhysicsComp();
		InputComp inpComp = new InputComp();
		TransformationComp posComp = new TransformationComp();
		posComp.setPosition(new Vector2(Display.getHeight()/2,Display.getWidth()/2));
		SpatialComp spatComp = new SpatialComp(new Circle(Vector2.Zero,30f));
		RenderingComp rendComp = new RenderingComp();
		HealthComp healthComp = new HealthComp(100, 2, 89);
		AbilityComp apComp = new AbilityComp();
		WeaponComp weapComp = new WeaponComp();
		AbilityComp abComp = new AbilityComp();	
		
	//	weapComp.setPrimaryAbility(Abilities.Telekinesis);
		
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
		player.addComponent(weapComp);

		player.refresh();
		
		this.generateRandomPickupables(manager);
	}

	private void generateRandomPickupables(IEntityManager manager) {
		for (int i = 0; i < this.numPickupables; i++) {
			IEntity ball = manager.createEmptyEntity();
			ball.setLabel("Pickable" + i);

			ball.addToGroup("Pickup-able");

			TransformationComp transComp = new TransformationComp();
			transComp.setPosition((float)Math.random() * 800,(float)Math.random() * 600);
			SpatialComp spatComp = new SpatialComp(new Circle(Vector2.Zero,40f));
			RenderingComp rendComp = new RenderingComp();
			rendComp.setTexture(ContentManager.loadTexture("Circle100pxGrey.png"));
			rendComp.setColor(new Color((float) Math.random(),(float) Math.random(),(float) Math.random(), (float) Math.random()));
			PhysicsComp physComp = new PhysicsComp();
			physComp.setMass(2f);
			transComp.setOrigin(new Vector2(rendComp.getTexture().Width / 2,
					rendComp.getTexture().Height / 2));	
			ball.addComponent(rendComp);
			ball.addComponent(spatComp);
			ball.addComponent(transComp);
			ball.addComponent(physComp);

			ball.refresh();
		}
	}

}
