package tests;


import math.Vector2;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import graphics.Color;

import utils.Circle;
import utils.Rectangle;

import components.HealthComp;
import components.InputComp;
import components.PhysicsComp;
import components.RenderingComp;
import components.SpatialComp;
import components.TransformationComp;
import content.ContentManager;

import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entitySystems.AttackResolveSystem;
import entitySystems.CharacterControllerSystem;
import entitySystems.InputSystem;
import entitySystems.PhysicsSystem;
import entitySystems.RenderingSystem;

public class CharacterDuellingTest extends AbstractSystemTest{

	public CharacterDuellingTest(Rectangle screenDim, boolean fullScreen) {
		super(screenDim, fullScreen);
	}

	@Override
	public void initializeSystems() {
		tester.addLogicSubSystem(new CharacterControllerSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new InputSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new PhysicsSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new AttackResolveSystem(this.tester.getWorld()));
		tester.addRenderSubSystem(new RenderingSystem(this.tester.getWorld(), this.graphics));
	}

	@Override
	public void initializeEntities(IEntityManager manager) {
		IEntity player1 = manager.createEmptyEntity();
		player1.addToGroup("Enemies");

		TransformationComp posC = new TransformationComp();
		posC.setPosition(new Vector2(Display.getWidth()/2-200,Display.getHeight()/2));

		PhysicsComp physC = new PhysicsComp();
		physC.setVelocity(new Vector2(0,0));

		HealthComp healthC = new HealthComp(100,20f,100f);

		InputComp inpC = new InputComp();

		inpC.setBackButton(Keyboard.KEY_S);
		inpC.setLeftButton(Keyboard.KEY_A);
		inpC.setForwardButton(Keyboard.KEY_W);
		inpC.setRightButton(Keyboard.KEY_D);
		inpC.setGallopButton(Keyboard.KEY_LSHIFT);
		inpC.setPipBuckButton(Keyboard.KEY_TAB);

		RenderingComp rendC = new RenderingComp();
		rendC.setColor(new Color(42,200,255, 255));

		rendC.setTexture(ContentManager.loadTexture("PPieLauncher.png"));

		posC.setOrigin(new Vector2(rendC.getTexture().Width/2,rendC.getTexture().Height/2));
		SpatialComp spatC = new SpatialComp(new Circle(new Vector2(0,0), 40));

		player1.addComponent(rendC);
		player1.addComponent(inpC);
		player1.addComponent(physC);
		player1.addComponent(posC);
		player1.addComponent(spatC);
		player1.addComponent(healthC);

		player1.refresh();

		//Player2:
		IEntity player2 = manager.createEmptyEntity();
		player2.addToGroup("Enemies");

		TransformationComp player2posC = new TransformationComp();
		player2posC.setPosition(new Vector2(Display.getWidth()/2+200,Display.getHeight()/2));

		PhysicsComp player2physC = new PhysicsComp();
		player2physC.setVelocity(new Vector2(0,0));

		HealthComp player2healthC = new HealthComp(100,20f,100f);

		InputComp player2inpC = new InputComp();

		player2inpC.setBackButton(Keyboard.KEY_DOWN);
		player2inpC.setLeftButton(Keyboard.KEY_LEFT);
		player2inpC.setForwardButton(Keyboard.KEY_UP);
		player2inpC.setRightButton(Keyboard.KEY_RIGHT);
		player2inpC.setGallopButton(Keyboard.KEY_RCONTROL);
		player2inpC.setPipBuckButton(Keyboard.KEY_M);

		RenderingComp player2rendC = new RenderingComp();
		player2rendC.setColor(new Color(42,255,42, 255));

		player2rendC.setTexture(ContentManager.loadTexture("PPieLauncher.png"));

		player2posC.setOrigin(new Vector2(player2rendC.getTexture().Width/2,player2rendC.getTexture().Height/2));

		SpatialComp player2spatC = new SpatialComp(new Circle(new Vector2(0,0), 40));

		player2.addComponent(player2rendC);
		player2.addComponent(player2inpC);
		player2.addComponent(player2physC);
		player2.addComponent(player2posC);
		player2.addComponent(player2spatC);
		player2.addComponent(player2healthC); 

		player2.refresh();
	}

	public static void main(String[] args) throws LWJGLException {
		new CharacterDuellingTest(new Rectangle(0,0, 800, 600), false).start();
	}

}


