package entitySystems;

import com.google.common.collect.ImmutableSet;

import components.HealthComponent;
import components.StatusChangeComponent;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class StatusChangeSystem extends EntityProcessingSystem {

	public StatusChangeSystem(IEntityWorld world) {
		super(world, StatusChangeComponent.class, HealthComponent.class);
	}

	private ComponentMapper<StatusChangeComponent> sCCM;
	private ComponentMapper<HealthComponent> hCM;

	@Override
	public void initialize() {

		sCCM = ComponentMapper.create(this.getWorld().getDatabase(),
				StatusChangeComponent.class);
		hCM = ComponentMapper.create(this.getWorld().getDatabase(),
				HealthComponent.class);
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			HealthComponent healthCom = hCM.getComponent(entity);
			StatusChangeComponent staChaCom = sCCM.getComponent(entity);
			healthCom.addHealthPoints(-staChaCom.getDamageToTake());
			
			entity.removeComponent(StatusChangeComponent.class);
			entity.refresh();
		}
	}

}
