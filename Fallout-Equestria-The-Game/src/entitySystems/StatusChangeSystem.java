package entitySystems;

import com.google.common.collect.ImmutableSet;
import components.StatusChangeComponent;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class StatusChangeSystem extends EntityProcessingSystem {

	public StatusChangeSystem(IEntityWorld world) {
		super(world, StatusChangeComponent.class);
	}

	private ComponentMapper<StatusChangeComponent> pCM;
	
	@Override
	public void initialize() {
		pCM = ComponentMapper.create(this.getWorld().getDatabase(),
				StatusChangeComponent.class);
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		
	}
	
}
