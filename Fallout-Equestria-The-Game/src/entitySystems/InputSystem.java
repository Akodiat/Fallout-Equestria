package entitySystems;
import org.lwjgl.input.Keyboard;

import utils.Key;

import entityFramework.*;
import components.InputComponent;

public class InputSystem extends EntitySingleProcessingSystem{

	protected InputSystem(IEntityWorld world,
			Class<? extends IComponent>[] componentsClasses) {
		super(world, componentsClasses);
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
		
	}

}
