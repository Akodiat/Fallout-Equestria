package entitySystems;

import math.Vector2;
import components.PositionComponent;
import components.RenderingComponent;

import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.SpriteBatch;

public class BasicRenderingSystem extends EntitySingleProcessingSystem {

	private SpriteBatch spriteBatch;
	
	public BasicRenderingSystem(IEntityWorld world, SpriteBatch graphics) {
		super(world, PositionComponent.class, RenderingComponent.class);
		this.spriteBatch = graphics;
	}

	
	private ComponentMapper<PositionComponent> posCM;
	private ComponentMapper<RenderingComponent> renderCM; 
	
	
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		posCM = ComponentMapper.create(
				this.getWorld().getDatabase(), PositionComponent.class);
		
		renderCM = ComponentMapper.create(
				this.getWorld().getDatabase(), RenderingComponent.class);
	}

	@Override
	protected void processEntity(IEntity entity) {
		RenderingComponent renderC = this.renderCM.getComponent(entity);
		PositionComponent positionC = this.posCM.getComponent(entity);
		
		
		if(renderC.getEffect() != null) {
			this.spriteBatch.end();
			this.spriteBatch.begin(renderC.getEffect(),this.spriteBatch.getView());
		}

		this.spriteBatch.draw(renderC.getTexture(), positionC.getPosition(), renderC.getColor(), null,
							  renderC.getOrigin(), Vector2.One, positionC.getRotation(), false);
		
		if(renderC.getEffect() != null) {
			
		}
	}

}
