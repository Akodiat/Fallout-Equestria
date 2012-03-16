package tests;

import java.io.IOException;

import math.Vector2;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Color;

import utils.Circle;
import utils.Rectangle;

import components.InputComponent;
import components.PhysicsComponent;
import components.PositionComponent;
import components.RenderingComponent;
import components.SpatialComponent;

import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entitySystems.BasicRenderingSystem;
import entitySystems.CharacterControllerSystem;
import entitySystems.InputSystem;
import entitySystems.PhysicsSystem;
import graphics.TextureLoader;
import graphics.TextureTest;

public class CharacterDuellingTest extends AbstractSystemTest{

	public CharacterDuellingTest(Rectangle screenDim, boolean fullScreen) {
		super(screenDim, fullScreen);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeSystems() {
		tester.addLogicubSystem(new CharacterControllerSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new InputSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new PhysicsSystem(this.tester.getWorld()));
		tester.addRenderSubSystem(new BasicRenderingSystem(this.tester.getWorld(), this.graphics));
	}

	@Override
	public void initializeEntities(IEntityManager manager) {
		IEntity player1 = manager.createEmptyEntity();

		PositionComponent posC = new PositionComponent();
		posC.setPosition(new Vector2(Display.getWidth()/2-200,Display.getHeight()/2));

		PhysicsComponent physC = new PhysicsComponent();
		physC.setVelocity(new Vector2(0,0));

		InputComponent inpC = new InputComponent();

		inpC.setBackButton(Keyboard.KEY_S);
		inpC.setLeftButton(Keyboard.KEY_A);
		inpC.setForwardButton(Keyboard.KEY_W);
		inpC.setRightButton(Keyboard.KEY_D);
		inpC.setGallopButton(Keyboard.KEY_LSHIFT);
		inpC.setPipBuckButton(Keyboard.KEY_TAB);

		RenderingComponent rendC = new RenderingComponent();
		rendC.setColor(new Color(42,200,255));
		try {
			rendC.setTexture(TextureLoader.loadTexture(TextureTest.class.getResourceAsStream("HEJHEJ.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SpatialComponent spatC = new SpatialComponent(new Circle(new Vector2(0,0), 30));

		player1.addComponent(rendC);
		player1.addComponent(inpC);
		player1.addComponent(physC);
		player1.addComponent(posC);
		player1.addComponent(spatC);
		
		player1.refresh();
		
		//Player2:
		IEntity player2 = manager.createEmptyEntity();

		PositionComponent player2posC = new PositionComponent();
		player2posC.setPosition(new Vector2(Display.getWidth()/2+200,Display.getHeight()/2));

		PhysicsComponent player2physC = new PhysicsComponent();
		player2physC.setVelocity(new Vector2(0,0));

		InputComponent player2inpC = new InputComponent();

		player2inpC.setBackButton(Keyboard.KEY_DOWN);
		player2inpC.setLeftButton(Keyboard.KEY_LEFT);
		player2inpC.setForwardButton(Keyboard.KEY_UP);
		player2inpC.setRightButton(Keyboard.KEY_RIGHT);
		player2inpC.setGallopButton(Keyboard.KEY_RCONTROL);
		player2inpC.setPipBuckButton(Keyboard.KEY_M);

		RenderingComponent player2rendC = new RenderingComponent();
		player2rendC.setColor(new Color(42,255,42));
		try {
			player2rendC.setTexture(TextureLoader.loadTexture(TextureTest.class.getResourceAsStream("HEJHEJ.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SpatialComponent player2spatC = new SpatialComponent(new Circle(new Vector2(0,0), 30));

		player2.addComponent(player2rendC);
		player2.addComponent(player2inpC);
		player2.addComponent(player2physC);
		player2.addComponent(player2posC);
		player2.addComponent(player2spatC);
		
		player2.refresh();
	}

	public static void main(String[] args) throws LWJGLException {
		new CharacterDuellingTest(new Rectangle(0,0, 800, 600), false).start();
	}

}

