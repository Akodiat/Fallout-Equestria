package entitySystems;
import math.Vector2;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import entityFramework.*;
import components.InputComponent;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class InputSystem extends EntitySingleProcessingSystem{

	public InputSystem(IEntityWorld world) {
		super(world, InputComponent.class);
		// TODO Auto-generated constructor stub
	}
	private ComponentMapper<InputComponent> CM;
	@Override
	public void initialize() {
		CM = ComponentMapper.create(this.getWorld().getDatabase(), InputComponent.class);
		
	}

	@Override
	protected void processEntity(IEntity entity) {
		InputComponent inputComponent = CM.getComponent(entity);
		inputComponent.setPipBuckButtonPressed(Keyboard.isKeyDown(inputComponent.getPipBuckButton().getKeyID()));
		inputComponent.setBackButtonPressed(Keyboard.isKeyDown(inputComponent.getBackButton().getKeyID()));
		inputComponent.setForwardButtonPressed(Keyboard.isKeyDown(inputComponent.getForwardButton().getKeyID()));
		inputComponent.setGallopButtonPressed(Keyboard.isKeyDown(inputComponent.getGallopButton().getKeyID()));
		inputComponent.setLeftButtonPressed(Keyboard.isKeyDown(inputComponent.getLeftButton().getKeyID()));
		inputComponent.setRightButtonPressed(Keyboard.isKeyDown(inputComponent.getRightButton().getKeyID()));
		
		inputComponent.setMousePosition(new Vector2(Mouse.getX(),Display.getHeight()-Mouse.getY())); //TODO: Find nicer solution
		inputComponent.setLeftMouseButtonDown(Mouse.isButtonDown(0));
		inputComponent.setRightMouseButtonDown(Mouse.isButtonDown(1));
	}

}
