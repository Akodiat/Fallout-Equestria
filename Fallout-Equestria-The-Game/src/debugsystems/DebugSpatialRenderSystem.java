package debugsystems;

import utils.Rectangle;
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
		
			int x = (int) transCom.getOriginPosition().X;
			int y = (int)(transCom.getOriginPosition().Y - transCom.getHeight());
			int w = (int)(spatiCom.getBounds().Max.X - spatiCom.getBounds().Min.X);
			int h = (int)(spatiCom.getBounds().Max.Y - spatiCom.getBounds().Min.Y);
				
			Color c = getHeightColor(y);
			graphics.draw(Texture2D.getPixel(), new Rectangle(x  -w / 2,y - h/ 2, w,h) , new Color(c, 0.3f), null);
			
			h = (int)(spatiCom.getBounds().Max.Z - spatiCom.getBounds().Min.Z);

			graphics.draw(Texture2D.getPixel(), new Rectangle(x  -w / 2,y - h/ 2, w,h) , new Color(Color.Green, 0.3f), null);
		}
	}

	private Color getHeightColor(float height) {
		float max = 2000;
		
		float lerpVal = height / max;
		
		return Color.lerp(Color.Red, Color.Blue, lerpVal);
	}

}
