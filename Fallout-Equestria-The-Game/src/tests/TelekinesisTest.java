package tests;

import java.util.HashMap;

import org.lwjgl.opengl.Display;

import ability.Ability;
import ability.TelekinesisAbility;

import math.Vector2;

import components.AbilityComp;
import components.ActionPointsComponent;
import components.HealthComponent;
import components.InputComponent;
import components.PhysicsComponent;
import components.RenderingComponent;
import components.SpatialComponent;
import components.TransformationComp;
import components.WeaponComponent;

import content.ContentManager;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entitySystems.*;
import debuggsystem.*;
import graphics.Color;
import utils.Circle;
import utils.Rectangle;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class TelekinesisTest extends AbstractSystemTest{

	private final int numPickupables = 42;

	public TelekinesisTest(Rectangle screenDim, boolean fullScreen) {
		super(screenDim, fullScreen);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TelekinesisTest(new Rectangle(0, 0, 800, 600), false).start();
	}

	@Override
	public void initializeSystems() {
		tester.addLogicubSystem(new CharacterControllerSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new InputSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new PhysicsSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new CollisionSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new RegenSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new TelekinesisSystem(this.tester.getWorld()));
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
	//	player.addToGroup("Pickup-able");
		player.setLabel("Player");
		PhysicsComponent physComp = new PhysicsComponent();
		InputComponent inpComp = new InputComponent();
		TransformationComp posComp = new TransformationComp();
		posComp.setPosition(new Vector2(Display.getHeight()/2,Display.getWidth()/2));
		SpatialComponent spatComp = new SpatialComponent(new Circle(Vector2.Zero,30f));
		RenderingComponent rendComp = new RenderingComponent();
		HealthComponent healthComp = new HealthComponent(100, 2, 89);
		ActionPointsComponent apComp = new ActionPointsComponent();
		WeaponComponent weapComp = new WeaponComponent();
		
		HashMap<Class<? extends Ability>, Ability> map = new HashMap<Class<? extends Ability>, Ability>();
		map.put(TelekinesisAbility.class, new TelekinesisAbility(2,1));
		
		AbilityComp abComp = new AbilityComp(map, (Ability) map.get(TelekinesisAbility.class));
		
		
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
		player.addComponent(abComp);

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
			SpatialComponent spatComp = new SpatialComponent(new Circle(Vector2.Zero,40f));
			RenderingComponent rendComp = new RenderingComponent();
			rendComp.setTexture(ContentManager.loadTexture("Circle100pxGrey.png"));
			rendComp.setColor(new Color((float) Math.random(),(float) Math.random(),(float) Math.random(), (float) Math.random()));
			PhysicsComponent physComp = new PhysicsComponent();
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
