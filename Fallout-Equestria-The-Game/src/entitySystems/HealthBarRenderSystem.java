package entitySystems;

import org.lwjgl.util.Rectangle;

import math.Vector2;

import com.google.common.collect.ImmutableSet;
import components.ActionPointsComponent;
import components.HealthComponent;
import components.RenderingComponent;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.Color;
import graphics.SpriteBatch;
import graphics.Texture2D;

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
		this.spriteBatch.begin();
		for (IEntity entity : entities) {
			HealthComponent healthC = this.hCM.getComponent(entity);
			RenderingComponent renderC = this.rCM.getComponent(entity);
			TransformationComp positionC = this.tCM.getComponent(entity);
			
			Rectangle healthBar = new Rectangle((int)-healthC.getHealthPoints()/2,(int)(renderC.getTexture().Height*positionC.getScale().Y)/2 + 30,(int)healthC.getHealthPoints(), 10 );		
			//this.spriteBatch.draw(Texture2D.class, positionC.getPosition(), Color.Green, healthBar);
		}
		this.spriteBatch.end();
	}
}
