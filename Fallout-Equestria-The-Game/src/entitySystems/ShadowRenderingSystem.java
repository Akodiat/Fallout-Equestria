package entitySystems;

import utils.HeightMap;
import utils.Rectangle;
import math.Vector2;

import com.google.common.collect.ImmutableSet;

import components.ShadowComp;
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

public class ShadowRenderingSystem extends EntityProcessingSystem {

	private Texture2D shadowTexture;
	private HeightMap heightMap;
	private SpriteBatch spriteBatch;
	private ContentManager contentManager;
	private ComponentMapper<TransformationComp> transCM;
	private ComponentMapper<SpatialComp> spatialCM;
	
	
	public ShadowRenderingSystem(IEntityWorld world, ContentManager contentManager, SpriteBatch spriteBatch, HeightMap heightMap) {
		super(world, TransformationComp.class, SpatialComp.class, ShadowComp.class);
		this.contentManager = contentManager;
		this.spriteBatch = spriteBatch;
		this.heightMap = heightMap;
	}

	@Override
	public void initialize() {
		this.shadowTexture = this.contentManager.loadTexture("shadow.png");
		this.transCM = ComponentMapper.create(getDatabase(), TransformationComp.class);
		this.spatialCM = ComponentMapper.create(getDatabase(), SpatialComp.class);
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			TransformationComp trans = this.transCM.getComponent(entity);
			SpatialComp spatial = this.spatialCM.getComponent(entity);
			
			float heightMapHeight = heightMap.getHeightAt(trans.getPosition());
			
			Vector2 pos = new Vector2(trans.getPosition().X - trans.getOrigin().X,
									  trans.getPosition().Y - heightMapHeight);
			
			float scale = 1 - 0.001f * (trans.getHeight() - heightMapHeight) ;
			Rectangle dest = new Rectangle((int)pos.X, (int)pos.Y, (int)(spatial.getBounds().getWidth() * scale), this.shadowTexture.Height);
			
			
			this.spriteBatch.draw(shadowTexture, dest, Color.White, null, shadowTexture.getBounds().getCenter(), 0f, !trans.getMirror());	
		}
						
	}
}
