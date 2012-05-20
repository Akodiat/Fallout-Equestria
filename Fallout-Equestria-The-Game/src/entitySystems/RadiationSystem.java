package entitySystems;


import math.Vector2;

import com.google.common.collect.ImmutableSet;

import utils.BoundingBox;
import utils.Circle;

import components.RadiationComp;
import components.SpatialComp;
import components.TransformationComp;

import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class RadiationSystem extends EntitySingleProcessingSystem {
	private static final float RADIATIONRATE = 0.001f;
	private BoundingBox entityBounds;
	
	public RadiationSystem(IEntityWorld world) {
		super(world, SpatialComp.class, TransformationComp.class);
	}

	@Override
	public void initialize() {
		
	}

	@Override
	protected void processEntity(IEntity entity) {
		/*ImmutableSet<IEntity> radiatingEntities = this.getWorld().getDatabase().getEntitysContainingComponent(RadiationComp.class);

		entityBounds = entity.getComponent(SpatialComp.class).getBounds();

		for(IEntity i : radiatingEntities) {
			Circle radiationBounds = i.getComponent(RadiationComp.class).getBounds();

			if(!i.equals(entity) 
					&& BoundingBox.intersects(entityBounds, entity.getComponent(TransformationComp.class).getPosition(), radiationBounds, i.getComponent(TransformationComp.class).getPosition()) 
					&& i.getComponent(RadiationComp.class).getRadiationLevel() > 0) {
				
				RadiationComp eRad = entity.getComponent(RadiationComp.class);
				
				if(eRad == null) {
					assignRadiationComp(entity, i);
				} else {
					if(eRad.getRadiationLevel() < i.getComponent(RadiationComp.class).getRadiationLevel() - RADIATIONRATE) {
						eRad.radiate(RADIATIONRATE, entityBounds);
					}
				}
			}
		}*/
	}

	private void assignRadiationComp(IEntity entityToRadiate, IEntity radiatingEntity) {
		if(entityToRadiate.getComponent(RadiationComp.class) == null) {
	/*		float radius = entityToRadiate.getComponent(SpatialComp.class).getBounds().getRadius();

			entityToRadiate.addComponent(new RadiationComp(new Circle(Vector2.Zero, radius), 1));
			entityToRadiate.refresh();*/
		} 
	}
}
