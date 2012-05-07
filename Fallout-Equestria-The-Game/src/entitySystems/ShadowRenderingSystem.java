package entitySystems;

import math.Vector2;

import com.google.common.collect.ImmutableSet;

import components.ShadowComp;
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
	private SpriteBatch spriteBatch;
	private ContentManager contentManager;
	private ComponentMapper<TransformationComp> transCM;
	
	public ShadowRenderingSystem(IEntityWorld world, ContentManager contentManager, SpriteBatch spriteBatch) {
		super(world, TransformationComp.class, ShadowComp.class);
		this.contentManager = contentManager;
		this.spriteBatch = spriteBatch;
	}

	@Override
	public void initialize() {
		this.shadowTexture = this.contentManager.loadTexture("UglyShadow.png");
		this.transCM = ComponentMapper.create(getDatabase(), TransformationComp.class);
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			TransformationComp trans = this.transCM.getComponent(entity);
			Vector2 pos = new Vector2(trans.getPosition().X - trans.getOrigin().X - 10,
									  trans.getPosition().Y + 15);
			
			this.spriteBatch.draw(shadowTexture, pos, Color.White);	
		}
	}
}
