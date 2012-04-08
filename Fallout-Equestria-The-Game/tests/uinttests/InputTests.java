package uinttests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.lwjgl.input.Keyboard;

import components.InputComp;

public class InputTests {
	
	@Test
	public void testInputSequence(){
		InputComp comp = new InputComp();
		comp.setBackButton(Keyboard.KEY_S);
		comp.setLeftButton(Keyboard.KEY_A);
		comp.setForwardButton(Keyboard.KEY_W);
		
		comp.setBackButtonPressed(true);
		assertTrue(comp.getBackButton().isPressed());
		
		comp.setBackButtonPressed(false);
		assertTrue(!comp.getBackButton().isPressed());
		
		comp.setLeftButtonPressed(true);
		assertTrue(comp.getLeftButton().isPressed());
		
		comp.setForwardButtonPressed(true);
		assertTrue(comp.getForwardButton().isPressed());
		
		comp.setLeftButtonPressed(false);
		assertTrue(!comp.getLeftButton().isPressed());
	}

}
