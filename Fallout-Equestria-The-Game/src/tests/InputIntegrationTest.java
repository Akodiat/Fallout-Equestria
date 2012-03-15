package tests;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import components.InputComponent;

public class InputIntegrationTest {
	
	public static void main(String[] args) throws LWJGLException{
		InputComponent comp = new InputComponent();
	
		Display.setDisplayMode(new DisplayMode(800,400));
		Display.create();
		
		System.out.println("Starting test...");
		comp.setBackButton(Keyboard.KEY_S);
		comp.setLeftButton(Keyboard.KEY_A);
		comp.setForwardButton(Keyboard.KEY_W);
		comp.setRightButton(Keyboard.KEY_D);
		comp.setGallopButton(Keyboard.KEY_LSHIFT);
		comp.setPipBuckButton(Keyboard.KEY_TAB);
		
		while(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			
			Display.update();
			
			comp.setPipBuckButtonPressed(Keyboard.isKeyDown(comp.getPipBuckButton().getKeyID()));
			comp.setBackButtonPressed(Keyboard.isKeyDown(comp.getBackButton().getKeyID()));
			comp.setForwardButtonPressed(Keyboard.isKeyDown(comp.getForwardButton().getKeyID()));
			comp.setGallopButtonPressed(Keyboard.isKeyDown(comp.getGallopButton().getKeyID()));
			comp.setLeftButtonPressed(Keyboard.isKeyDown(comp.getLeftButton().getKeyID()));
			comp.setRightButtonPressed(Keyboard.isKeyDown(comp.getRightButton().getKeyID()));
			
			String s="You ";
			if(comp.isGallopButtonPressed())
				s+="gallop ";
			else
				s+="trot ";
			if(comp.isBackButtonPressed())
				s+="south";
			if(comp.isForwardButtonPressed())
				s+="north";
			if(comp.isLeftButtonPressed())
				s+="west";
			if(comp.isRightButtonPressed())
				s+="east";
			System.out.println(s);
			if(comp.isPipBuckButtonPressed())
				System.out.println("You open your pipbuck");
		}
	}	

}
