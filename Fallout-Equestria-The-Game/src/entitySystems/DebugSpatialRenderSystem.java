package entitySystems;

import math.Vector2;
import utils.ContentManager;

import com.google.common.collect.ImmutableSet;
import components.SpatialComponent;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;

public class DebugSpatialRenderSystem extends EntityProcessingSystem {

	private Texture2D circleTexture;
	private SpriteBatch graphics;

	public DebugSpatialRenderSystem(IEntityWorld world, SpriteBatch graphics) {
		super(world, SpatialComponent.class, TransformationComp.class);
		this.graphics = graphics;
	}

	private ComponentMapper<SpatialComponent> sCM;
	private ComponentMapper<TransformationComp> tCM;

	@Override
	public void initialize() {
		sCM = ComponentMapper.create(this.getWorld().getDatabase(),
				SpatialComponent.class);
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);

		this.circleTexture = ContentManager.loadTexture("Circle100pxGrey.png");
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {

		for (IEntity entity : entities) {
			SpatialComponent spatiCom = sCM.getComponent(entity);
			TransformationComp transCom = tCM.getComponent(entity);

			// Rectangle source = new
			// Rectangle((int)attaCom.getBounds().getPosition().X,(int)attaCom.getBounds().getPosition().Y,(int)attaCom.getBounds().getRadius()*2,(int)attaCom.getBounds().getRadius()*2);
			float scale = 2*spatiCom.getBounds().getRadius() / 100;
			graphics.draw(circleTexture, transCom.getPosition(), Color.Green, null,
					transCom.getOrigin(), scale, 0, false);

		}
	}

}
