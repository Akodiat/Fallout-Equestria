package entitySystems;

import utils.Rectangle;
import math.Vector2;

import com.google.common.collect.ImmutableSet;

import components.SpatialComp;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class MapCollisionSystem extends EntityProcessingSystem {

	private Rectangle worldBounds;

	public MapCollisionSystem(IEntityWorld world, Rectangle worldBounds) {
		super(world, TransformationComp.class, SpatialComp.class);
		this.worldBounds = worldBounds;
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
			
			//Check collision against west border
			int left = this.worldBounds.getLeft();
			if (posiCom.getPosition().X - spatiCom.getBounds().getRadius() < left ){
				posiCom.setPosition(new Vector2(spatiCom.getBounds().getRadius() + left ,posiCom.getPosition().Y ));
			}
			
			//Check collision against North border
			int top = this.worldBounds.getTop();
			if (posiCom.getPosition().Y - spatiCom.getBounds().getRadius() < top){
				posiCom.setPosition(new Vector2(posiCom.getPosition().X,spatiCom.getBounds().getRadius() + top));
			}
			
			//Check collision against east border
			int right = this.worldBounds.getRight();
			if (posiCom.getPosition().X + spatiCom.getBounds().getRadius() > right){
				posiCom.setPosition(new Vector2(right - spatiCom.getBounds().getRadius(), posiCom.getPosition().Y));
			}
			
			//Check collision against south border
			int bottom = this.worldBounds.getBottom();
			if (posiCom.getPosition().Y + spatiCom.getBounds().getRadius() > bottom){
				posiCom.setPosition(new Vector2(posiCom.getPosition().X,bottom - spatiCom.getBounds().getRadius()));
			}
			
		}

	}

}
