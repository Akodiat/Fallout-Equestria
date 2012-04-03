package entitySystems;

import components.TextRenderingComp;
import components.TransformationComp;

import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.SpriteBatch;

public class TextRenderingSystem extends EntitySingleProcessingSystem{

	private SpriteBatch batch;
	
	public TextRenderingSystem(IEntityWorld world, SpriteBatch batch) {
		super(world, TransformationComp.class, TextRenderingComp.class);
		this.batch = batch;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void processEntity(IEntity entity) {
		TransformationComp transC = entity.getComponent(TransformationComp.class);
		TextRenderingComp renderC = entity.getComponent(TextRenderingComp.class);
		
		this.batch.drawString(renderC.getFont(), renderC.getText(), transC.getPosition(),
							  renderC.getColor(), transC.getOrigin(), transC.getScale());	
	}
}
