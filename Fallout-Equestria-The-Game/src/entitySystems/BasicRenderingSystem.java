package entitySystems;

import components.PositionComponent;
import components.RenderingComponent;

import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.Graphics;

public class BasicRenderingSystem extends EntitySingleProcessingSystem {

	private Graphics graphics;
	
	public BasicRenderingSystem(IEntityWorld world, Graphics graphics) {
		super(world, PositionComponent.class, RenderingComponent.class);
		this.graphics = graphics;
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
		graphics.draw(renderC.getTexture(), positionC.getPosition(), renderC.getColor());
	}

}
