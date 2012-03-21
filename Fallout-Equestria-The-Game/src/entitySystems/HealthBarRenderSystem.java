package entitySystems;

import math.Vector2;

import com.google.common.collect.ImmutableSet;
import components.HealthComponent;
import components.RenderingComponent;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.SpriteBatch;
import graphics.Texture2D;
import utils.Rectangle;

public class HealthBarRenderSystem extends EntityProcessingSystem {

	private SpriteBatch spriteBatch;

	@SuppressWarnings("unchecked")
	public HealthBarRenderSystem(IEntityWorld world, SpriteBatch graphics) {
		super(world, HealthComponent.class, RenderingComponent.class,
				TransformationComp.class);
		this.spriteBatch = graphics;
	}

	private ComponentMapper<HealthComponent> hCM;
	private ComponentMapper<RenderingComponent> rCM;
	private ComponentMapper<TransformationComp> tCM;

	@Override
	public void initialize() {
		hCM = ComponentMapper.create(this.getWorld().getDatabase(),
				HealthComponent.class);
		rCM = ComponentMapper.create(this.getWorld().getDatabase(),
				RenderingComponent.class);
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);
	}

	@Override
	protected void processEntitys(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			HealthComponent healthC = this.hCM.getComponent(entity);
			RenderingComponent renderC = this.rCM.getComponent(entity);
			TransformationComp positionC = this.tCM.getComponent(entity);
			
			Rectangle healthBar = new Rectangle((int)-healthC.getHealthPoints()/2,(int)(renderC.getTexture().Height*positionC.getScale().Y)/2 + 30,(int)healthC.getHealthPoints(), 10 );		
			
			Texture2D texture = Texture2D.getPixel();
			this.spriteBatch.draw(texture, healthBar, renderC.getColor(), null);
		}
	}
}
