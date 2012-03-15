package entitySystems;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ImmutableSet;

import components.AttackComponent;
import components.InputComponent;
import components.PositionComponent;
import components.SpatialComponent;

import entityFramework.ComponentMapper;
import entityFramework.Entity;
import entityFramework.EntitySystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

/**
 * 
 * @author Gustav
 * 
 */
public class AttackResolveSystem extends EntitySystem {

	protected AttackResolveSystem(IEntityWorld world,
			Class<? extends IComponent>[] componentsClasses) {
		super(world, componentsClasses);
	}

	private ComponentMapper<AttackComponent> ACM;
	private ComponentMapper<SpatialComponent> SCM;
	private ComponentMapper<PositionComponent> PCM;

	@Override
	public void initialize() {
		ACM = ComponentMapper.create(this.getWorld().getDatabase(),
				AttackComponent.class);
		PCM = ComponentMapper.create(this.getWorld().getDatabase(),
				PositionComponent.class);
		SCM = ComponentMapper.create(this.getWorld().getDatabase(),
				SpatialComponent.class);
	}

	@Override
	public void process() {

	}

}
