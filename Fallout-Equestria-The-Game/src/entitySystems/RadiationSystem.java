package entitySystems;

import com.google.common.collect.ImmutableSet;

import utils.Circle;

import components.RadiationComp;
import components.SpatialComp;

import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class RadiationSystem extends EntitySingleProcessingSystem{

	protected RadiationSystem(IEntityWorld world,
			Class<? extends IComponent>[] componentsClasses) {
		super(world, componentsClasses);
	}

	@Override
	public void initialize() {
		
	}

	@Override
	protected void processEntity(IEntity entity) {
		ImmutableSet<IEntity> radiatingEntities = this.getWorld().getDatabase().getEntitysContainingComponent(RadiationComp.class);
		
		Circle entityBounds = entity.getComponent(SpatialComp.class).getBounds();
		
		for(IEntity i : radiatingEntities) {
			Circle radiationBounds = i.getComponent(RadiationComp.class).getBounds();
			
			if(Circle.intersects(entityBounds, radiationBounds) && i.getComponent(RadiationComp.class).getRadiationLevel() > 20f) {
				
				radiate(entity, Circle.distance(entityBounds, entityBounds.getPosition(), 
						                        radiationBounds, radiationBounds.getPosition()), 
						                        i.getComponent(RadiationComp.class).getRadiationLevel());
			}
		}
	}
	
	private void radiate(IEntity entity, float position, float radiationLevel) {
		if(!entity.getComponents().contains(RadiationComp.class)) {
			entity.addComponent(new RadiationComp(entity.getComponent(SpatialComp.class).getBounds(),
					-position*radiationLevel));
		} else {
			entity.getComponent(RadiationComp.class).addRadiation(-position*radiationLevel);
		}
	}
}
