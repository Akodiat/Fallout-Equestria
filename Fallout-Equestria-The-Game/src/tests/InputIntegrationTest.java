package tests;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import components.InputComponent;

public class InputIntegrationTest {
	
	public static void main(){
		InputComponent comp = new InputComponent();
		
		comp.setBackButton(Keyboard.KEY_S);
		comp.setLeftButton(Keyboard.KEY_A);
		comp.setForwardButton(Keyboard.KEY_W);
		comp.setRightButton(Keyboard.KEY_D);
		comp.setGallopButton(Keyboard.KEY_LSHIFT);
		comp.setPipBuckButton(Keyboard.KEY_TAB);
		
		while(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			Display.update();
		}
	}	

}
