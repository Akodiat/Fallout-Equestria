package entitySystems;


import math.Vector2;

import com.google.common.collect.ImmutableSet;

import utils.Circle;

import components.RadiationComp;
import components.SpatialComp;
import components.TransformationComp;

import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class RadiationSystem extends EntitySingleProcessingSystem {
	
	public RadiationSystem(IEntityWorld world) {
		super(world);
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

			if(!i.equals(entity) 
					&& Circle.intersects(entityBounds, entity.getComponent(TransformationComp.class).getPosition(), radiationBounds, i.getComponent(TransformationComp.class).getPosition()) 
					&& i.getComponent(RadiationComp.class).getRadiationLevel() > 0) {
				
				radiate(entity, i,
						Circle.distance(entityBounds, entityBounds.getPosition(), radiationBounds, radiationBounds.getPosition()));
				
			}
		}
	}

	private void radiate(IEntity entityToRadiate, IEntity radiatingEntity, float distanceToSource) {
		
		if(entityToRadiate.getComponent(RadiationComp.class) == null) {
			float radius = entityToRadiate.getComponent(SpatialComp.class).getBounds().getRadius();

			entityToRadiate.addComponent(new RadiationComp(new Circle(Vector2.Zero, radius), 1));
			entityToRadiate.refresh();
		} else {
			int receiverLevel = entityToRadiate.getComponent(RadiationComp.class).getRadiationLevel();
			int transmitterLevel = radiatingEntity.getComponent(RadiationComp.class).getRadiationLevel();

			if(receiverLevel < transmitterLevel) {
				entityToRadiate.getComponent(RadiationComp.class).radiate();
				
			}
		}
	}
}
