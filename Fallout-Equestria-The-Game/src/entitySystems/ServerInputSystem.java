package entitySystems;
import utils.input.Keyboard;
import utils.input.Mouse;

import entityFramework.*;
import components.InputComp;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class ServerInputSystem extends LabelEntitySystem{

	private Mouse mouse;
	private Keyboard keyboard;
	
	public ServerInputSystem(IEntityWorld world, Mouse mouse, Keyboard keyboard, String label) {
		super(world, label, InputComp.class);
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

	@Override
	protected void processMissingEntity() {
		// TODO Auto-generated method stub
		
	}

}
