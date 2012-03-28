package entitySystems;

import math.Vector2;

import com.google.common.collect.ImmutableSet;

import components.SpatialComp;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class MapCollisionSystem extends EntityProcessingSystem {

	private Vector2 worldDimensions;

	public MapCollisionSystem(IEntityWorld world, Vector2 worldDimensions) {
		super(world, TransformationComp.class, SpatialComp.class);
		this.worldDimensions = worldDimensions;
	}

	private ComponentMapper<SpatialComp> sCM;
	private ComponentMapper<TransformationComp> tCM;

	@Override
	public void initialize() {
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);
		sCM = ComponentMapper.create(this.getWorld().getDatabase(),
				SpatialComp.class);
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			TransformationComp posiCom = tCM.getComponent(entity);
			SpatialComp spatiCom = sCM.getComponent(entity);
			
			//Check collision against north border
			if (posiCom.getPosition().X - spatiCom.getBounds().getRadius()<0){
				posiCom.setPosition(new Vector2(spatiCom.getBounds().getRadius(),posiCom.getPosition().Y ));
			}
			
			//Check collision against west border
			if (posiCom.getPosition().Y - spatiCom.getBounds().getRadius()<0){
				posiCom.setPosition(new Vector2(posiCom.getPosition().X,spatiCom.getBounds().getRadius()));
			}
			
			//Check collision against east border
			if (posiCom.getPosition().X + spatiCom.getBounds().getRadius()>worldDimensions.X){
				posiCom.setPosition(new Vector2(worldDimensions.X - spatiCom.getBounds().getRadius(),posiCom.getPosition().Y));
			}
			
			//Check collision against south border
			if (posiCom.getPosition().Y + spatiCom.getBounds().getRadius()>worldDimensions.Y){
				posiCom.setPosition(new Vector2(posiCom.getPosition().X,worldDimensions.Y - spatiCom.getBounds().getRadius()));
			}
			
		}

	}

}
