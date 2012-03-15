package entitySystems;

import java.util.BitSet;
import java.util.HashMap;

import components.AttackComponent;
import components.InputComponent;

import entityFramework.ComponentMapper;
import entityFramework.EntitySystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class AttackResolveSystem extends EntitySystem {

	protected AttackResolveSystem(IEntityWorld world,
			Class<? extends IComponent>[] componentsClasses) {
		super(world, componentsClasses);
	}

	private ComponentMapper<AttackComponent> CM;
	@Override
	public void initialize() {
		CM = ComponentMapper.create(this.getWorld().getDatabase(), AttackComponent.class);
	}

	@Override
	public void process() {
		
	}

}
