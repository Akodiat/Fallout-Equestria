package tests;

import java.io.IOException;

import math.Vector2;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import components.HealthComponent;
import components.InputComponent;
import components.PhysicsComponent;
import components.RenderingComponent;
import components.SpatialComponent;
import components.TransformationComp;

import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entitySystems.*;
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
		this.tester.addLogicubSystem(new CharacterControllerSystem(this.tester.getWorld()));
		this.tester.addLogicubSystem(new InputSystem(this.tester.getWorld()));
		this.tester.addLogicubSystem(new PhysicsSystem(this.tester.getWorld()));
		this.tester.addLogicubSystem(new AttackResolveSystem(this.tester.getWorld()));
		this.tester.addLogicubSystem(new StatusChangeSystem(this.tester.getWorld()));
		this.tester.addLogicubSystem(new RegenSystem(this.tester.getWorld()));
		this.tester.addRenderSubSystem(new HealthBarRenderSystem(this.tester.getWorld(), this.graphics));
		this.tester.addRenderSubSystem(new RenderingSystem(this.tester.getWorld(), this.graphics));
		this.tester.addRenderSubSystem(new HUDRenderingSystem(this.tester.getWorld(), this.graphics, "Player"));

	}

	@Override
	public void initializeEntities(IEntityManager manager) {
		IEntity player = manager.createEmptyEntity();
		player.addToGroup("Enemies");
		player.setLabel("Player");
		PhysicsComponent physComp = new PhysicsComponent();
		InputComponent inpComp = new InputComponent();
		TransformationComp posComp = new TransformationComp();
		posComp.setPosition(new Vector2(Display.getHeight()/2,Display.getWidth()/2));
		SpatialComponent spatComp = new SpatialComponent(new Circle(posComp.getPosition(),30f));
		RenderingComponent rendComp = new RenderingComponent();
		HealthComponent healthComp = new HealthComponent(100, 2, 89);
		
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
		player.addComponent(healthComp);
		
		player.refresh();
	}
}
