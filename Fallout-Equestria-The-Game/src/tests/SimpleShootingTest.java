package tests;

import java.io.IOException;

import math.Vector2;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import components.InputComponent;
import components.PhysicsComponent;
import components.RenderingComponent;
import components.SpatialComponent;
import components.TransformationComp;

import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entitySystems.AttackResolveSystem;
import entitySystems.RenderingSystem;
import entitySystems.CharacterControllerSystem;
import entitySystems.InputSystem;
import entitySystems.PhysicsSystem;
import graphics.Color;
import graphics.TextureLoader;
import utils.Circle;
import utils.Rectangle;

public class SimpleShootingTest extends AbstractSystemTest{
	
	public static void main(String[] args) throws LWJGLException{
		new SimpleShootingTest(new Rectangle(0,0, 800, 600), false).start();
	}

	public SimpleShootingTest(Rectangle screenDim, boolean fullScreen) {
		super(screenDim, fullScreen);
	}

	@Override
	public void initializeSystems() {
		tester.addLogicubSystem(new CharacterControllerSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new InputSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new PhysicsSystem(this.tester.getWorld()));
		tester.addLogicubSystem(new AttackResolveSystem(this.tester.getWorld()));
		tester.addRenderSubSystem(new RenderingSystem(this.tester.getWorld(), this.graphics));
	}

	@Override
	public void initializeEntities(IEntityManager manager) {
		IEntity player = manager.createEmptyEntity();
		player.addToGroup("Enemies");
		PhysicsComponent physComp = new PhysicsComponent();
		InputComponent inpComp = new InputComponent();
		TransformationComp posComp = new TransformationComp();
		posComp.setPosition(new Vector2(Display.getHeight()/2,Display.getWidth()/2));
		SpatialComponent spatComp = new SpatialComponent(new Circle(posComp.getPosition(),30f));
		RenderingComponent rendComp = new RenderingComponent();

		rendComp.setColor(new Color(42,200,255, 255));
		try {
			rendComp.setTexture(TextureLoader.loadTexture(TextureTest.class.getResourceAsStream("PPieLauncher.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		player.addComponent(rendComp);
		player.addComponent(inpComp);
		player.addComponent(physComp);
		player.addComponent(posComp);
		player.addComponent(spatComp);
		
		player.refresh();
	}

}
