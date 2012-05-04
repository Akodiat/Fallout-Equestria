package debugsystems;

import math.Vector2;

import com.google.common.collect.ImmutableSet;
import components.SpatialComp;
import components.TransformationComp;
import content.ContentManager;

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

	private ContentManager contentManager;
	public DebugSpatialRenderSystem(IEntityWorld world,ContentManager contentManager, SpriteBatch graphics) {
		super(world, SpatialComp.class, TransformationComp.class);
		this.graphics = graphics;
		this.contentManager = contentManager;
	}

	private ComponentMapper<SpatialComp> sCM;
	private ComponentMapper<TransformationComp> tCM;

	@Override
	public void initialize() {
		sCM = ComponentMapper.create(this.getWorld().getDatabase(),
				SpatialComp.class);
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);

		this.circleTexture = contentManager.loadTexture("Circle100pxGrey.png");
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {

		for (IEntity entity : entities) {
			SpatialComp spatiCom = sCM.getComponent(entity);
			TransformationComp transCom = tCM.getComponent(entity);

			// Rectangle source = new
			// Rectangle((int)attaCom.getBounds().getPosition().X,(int)attaCom.getBounds().getPosition().Y,(int)attaCom.getBounds().getRadius()*2,(int)attaCom.getBounds().getRadius()*2);
			float radius = spatiCom.getBounds().getRadius();
			float scale = radius / 50f;
			graphics.draw(circleTexture, transCom.getPosition(), new Color(Color.Green, 0.3f), null, new Vector2(50,50), scale, 0, false);

		}
	}

}
