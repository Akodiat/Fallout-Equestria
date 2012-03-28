package tests;

import math.Vector2;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import graphics.Color;

import components.InputComp;
import components.PhysicsComp;
import components.RenderingComp;
import components.TransformationComp;
import content.ContentManager;

import utils.Rectangle;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entitySystems.RenderingSystem;
import entitySystems.CharacterControllerSystem;
import entitySystems.InputSystem;
import entitySystems.PhysicsSystem;
/**
 * 
 * @author Lukas
 *
 */
public class CharacterMovementTest extends AbstractSystemTest{

	public CharacterMovementTest(Rectangle screenDim, boolean fullScreen) {
		super(screenDim, fullScreen);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeSystems() {
		tester.addLogicSubSystem(new CharacterControllerSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new InputSystem(this.tester.getWorld()));
		tester.addLogicSubSystem(new PhysicsSystem(this.tester.getWorld()));
		tester.addRenderSubSystem(new RenderingSystem(this.tester.getWorld(), this.graphics));
	}

	@Override
	public void initializeEntities(IEntityManager manager) {
		IEntity entity = manager.createEmptyEntity();
		
		TransformationComp posC = new TransformationComp();
		posC.setPosition(new Vector2(0,0));
		
		PhysicsComp physC = new PhysicsComp();
		physC.setVelocity(new Vector2(0,0));
		
		InputComp inpC = new InputComp();

		inpC.setBackButton(Keyboard.KEY_S);
		inpC.setLeftButton(Keyboard.KEY_A);
		inpC.setForwardButton(Keyboard.KEY_W);
		inpC.setRightButton(Keyboard.KEY_D);
		inpC.setGallopButton(Keyboard.KEY_LSHIFT);
		inpC.setPipBuckButton(Keyboard.KEY_TAB);
		
		RenderingComp rendC = new RenderingComp();
		rendC.setColor(new Color(255,255,255,255));
		rendC.setTexture(ContentManager.loadTexture("HEJHEJ.png"));
	
		
		entity.addComponent(rendC);
		entity.addComponent(inpC);
		entity.addComponent(physC);
		entity.addComponent(posC);
		
		entity.refresh();
		System.out.println(	this.tester.getWorld().getDatabase().getEntityCount());
	}
	
	public static void main(String[] args) throws LWJGLException {
		new CharacterMovementTest(new Rectangle(0,0, 800, 600), false).start();
	}

}
