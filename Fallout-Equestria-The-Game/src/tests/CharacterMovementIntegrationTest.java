package tests;

import java.io.IOException;

import graphics.Graphics;
import graphics.TextureLoader;
import graphics.TextureTest;

import math.Vector2;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Color;

import utils.Rectangle;

import components.InputComponent;
import components.PhysicsComponent;
import components.PositionComponent;
import components.RenderingComponent;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class CharacterMovementIntegrationTest {

	private static Graphics graphics;
	
	public static void main(String[] args) throws IOException, LWJGLException{
		//Display.setDisplayMode(new DisplayMode(800,400));
		Display.create();
		
		graphics = new Graphics(new Rectangle(0, 0, 1280, 720));
		
		RenderingComponent rendComp = new RenderingComponent();
		rendComp.setColor(new Color(255,255,255));
		rendComp.setTexture(TextureLoader.loadTexture(TextureTest.class.getResourceAsStream("HEJHEJ.png")));

		PositionComponent posComp = new PositionComponent();
		posComp.setPosition(new Vector2(Display.getWidth()/2,Display.getHeight()/2));

		PhysicsComponent physComp = new PhysicsComponent();
		physComp.setVelocity(new Vector2(0,0));

		InputComponent inpComp = new InputComponent();

		

		inpComp.setBackButton(Keyboard.KEY_S);
		inpComp.setLeftButton(Keyboard.KEY_A);
		inpComp.setForwardButton(Keyboard.KEY_W);
		inpComp.setRightButton(Keyboard.KEY_D);
		inpComp.setGallopButton(Keyboard.KEY_LSHIFT);
		inpComp.setPipBuckButton(Keyboard.KEY_TAB);

		while(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !Display.isCloseRequested()){
			
			graphics.clearScreen(new Color(255,255,234, 235));
			//Set buttons (from InputSystem)
			inpComp.setPipBuckButtonPressed(Keyboard.isKeyDown(inpComp.getPipBuckButton().getKeyID()));
			inpComp.setBackButtonPressed(Keyboard.isKeyDown(inpComp.getBackButton().getKeyID()));
			inpComp.setForwardButtonPressed(Keyboard.isKeyDown(inpComp.getForwardButton().getKeyID()));
			inpComp.setGallopButtonPressed(Keyboard.isKeyDown(inpComp.getGallopButton().getKeyID()));
			inpComp.setLeftButtonPressed(Keyboard.isKeyDown(inpComp.getLeftButton().getKeyID()));
			inpComp.setRightButtonPressed(Keyboard.isKeyDown(inpComp.getRightButton().getKeyID()));

			//Move accordingly (from CharacterControllerSystem)
			float speedFactor = 0.5f;
			if(inpComp.isGallopButtonPressed())
				speedFactor=1;
			
			Vector2 velocity = new Vector2(0,0);
			if (inpComp.isBackButtonPressed())
				velocity=Vector2.add(velocity, new Vector2(0,1));
			if (inpComp.isForwardButtonPressed())
				velocity=Vector2.add(velocity, new Vector2(0,-1));
			if (inpComp.isLeftButtonPressed())
				velocity=Vector2.add(velocity, new Vector2(-1,0));
			if (inpComp.isRightButtonPressed())
				velocity=Vector2.add(velocity, new Vector2(1,0));
			Vector2.mul((1/Vector2.distance(new Vector2(0,0), velocity)), velocity); //TODO: Create norm method in Vector2	
			velocity = Vector2.mul(speedFactor, velocity);
			physComp.setVelocity(velocity);
			
			//Update position (from PhysicsSystem)
			posComp.setPosition(Vector2.add(posComp.getPosition(), physComp.getVelocity()));
			
			//Render texture
			rendComp.setColor(new Color((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255), (int) (Math.random()*255)));
			graphics.draw(rendComp.getTexture(), posComp.getPosition(),  rendComp.getColor());
			
			//Update
			Display.update();
		}

	}
}
