package entitySystems;

import math.Vector2;
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
		
		Vector2 meassuredString = Vector2.mul(0.5f, renderC.getFont().meassureString(renderC.getText()));
		
		this.batch.drawString(renderC.getFont(), renderC.getText(), transC.getPosition(),
							  renderC.getColor(), meassuredString, transC.getScale());	
	}
}
