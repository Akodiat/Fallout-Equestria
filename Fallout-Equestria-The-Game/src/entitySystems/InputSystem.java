package entitySystems;
import math.Vector2;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import utils.Camera2D;

import entityFramework.*;
import components.InputComp;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class InputSystem extends EntitySingleProcessingSystem{

	private Camera2D camera;
	
	public InputSystem(IEntityWorld world, Camera2D camera) {
		super(world, InputComp.class);
		this.camera = camera;
	}
	private ComponentMapper<InputComp> CM;
	@Override
	public void initialize() {
		CM = ComponentMapper.create(this.getWorld().getDatabase(), InputComp.class);
		
	}

	@Override
	protected void processEntity(IEntity entity) {
		InputComp inputComponent = CM.getComponent(entity);
		inputComponent.setPipBuckButtonPressed(Keyboard.isKeyDown(inputComponent.getPipBuckButton().getKeyID()));
		inputComponent.setBackButtonPressed(Keyboard.isKeyDown(inputComponent.getBackButton().getKeyID()));
		inputComponent.setForwardButtonPressed(Keyboard.isKeyDown(inputComponent.getForwardButton().getKeyID()));
		inputComponent.setGallopButtonPressed(Keyboard.isKeyDown(inputComponent.getGallopButton().getKeyID()));
		inputComponent.setLeftButtonPressed(Keyboard.isKeyDown(inputComponent.getLeftButton().getKeyID()));
		inputComponent.setRightButtonPressed(Keyboard.isKeyDown(inputComponent.getRightButton().getKeyID()));
		
		Vector2 mouseViewPos = new Vector2(Mouse.getX(),Display.getHeight()-Mouse.getY());
		Vector2 mouseWorldPos = camera.getViewToWorldCoords(mouseViewPos);
		
		inputComponent.setMousePosition(mouseWorldPos); //TODO: Find nicer solution
		inputComponent.setLeftMouseButtonDown(Mouse.isButtonDown(0));
		inputComponent.setRightMouseButtonDown(Mouse.isButtonDown(1));
	}

}
