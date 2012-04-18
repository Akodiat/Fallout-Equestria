package debugsystems;

import math.Vector2;

import com.google.common.collect.ImmutableSet;
import components.RadiationComp;
import components.TransformationComp;
import content.ContentManager;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;

public class DebugRadiationRenderSystem extends EntityProcessingSystem{
	
	private Texture2D circleTexture;
	private SpriteBatch graphics;
	
	private ComponentMapper<RadiationComp> rCM;

	public DebugRadiationRenderSystem(IEntityWorld world, SpriteBatch graphics) {
		super(world, RadiationComp.class);
		this.graphics = graphics;
	}

	@Override
	public void initialize() {
		rCM = ComponentMapper.create(this.getWorld().getDatabase(),
				RadiationComp.class);
		
		this.circleTexture = ContentManager.loadTexture("Circle100pxGrey.png");
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			RadiationComp radCom = rCM.getComponent(entity);
			TransformationComp transRadCom = entity.getComponent(TransformationComp.class);

			graphics.draw(circleTexture, transRadCom.getPosition(), new Color(Color.Cyan, 0.5f), null,
					new Vector2(radCom.getBounds().getRadius()), radCom.getBounds().getRadius()/50, 0, false);
		}
	}
}
