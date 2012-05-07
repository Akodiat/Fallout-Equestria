package entitySystems;
import utils.Keyboard;
import utils.Mouse;

import entityFramework.*;
import components.InputComp;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class InputSystem extends EntitySingleProcessingSystem{

	private Mouse mouse;
	private Keyboard keyboard;
	
	
	public InputSystem(IEntityWorld world, Mouse mouse, Keyboard keyboard) {
		super(world, InputComp.class);
		this.mouse = mouse;
		this.keyboard = keyboard;
	}
	private ComponentMapper<InputComp> CM;
	@Override
	public void initialize() {
		CM = ComponentMapper.create(this.getWorld().getDatabase(), InputComp.class);
		
	}

	@Override
	protected void processEntity(IEntity entity) {
		InputComp inputComponent = CM.getComponent(entity);		
		inputComponent.setKeyboard(this.keyboard);
		inputComponent.setMouse(this.mouse);
	}

}
