package entitySystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.common.collect.ImmutableSet;

import math.Matrix4;
import math.Vector2;
import components.RenderingComp;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.ShaderEffect;
import graphics.SpriteBatch;

public class RenderingSystem extends EntityProcessingSystem {

	private SpriteBatch spriteBatch;
	
	public RenderingSystem(IEntityWorld world, SpriteBatch graphics) {
		super(world, TransformationComp.class, RenderingComp.class);
		this.spriteBatch = graphics;
	}

	private ComponentMapper<TransformationComp> posCM;
	private ComponentMapper<RenderingComp> renderCM; 
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		posCM = ComponentMapper.create(
				this.getWorld().getDatabase(), TransformationComp.class);
		
		renderCM = ComponentMapper.create(
				this.getWorld().getDatabase(), RenderingComp.class);
	}

	
	protected void processEntity(IEntity entity) {
		RenderingComp renderC = this.renderCM.getComponent(entity);
		TransformationComp positionC = this.posCM.getComponent(entity);
		
		this.draw(renderC, positionC);
	}

	private void draw(RenderingComp renderC, TransformationComp transformation) {
		this.spriteBatch.draw(renderC.getTexture(), 
							  new Vector2(transformation.getOriginPosition().X, transformation.getOriginPosition().Y - transformation.getHeight()), 
							  renderC.getColor(), 
							  renderC.getSource(),
							  transformation.getOrigin(),
							  transformation.getScale(),
							  transformation.getRotation(), 
							  transformation.getMirror());
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		
		ArrayList<IEntity> array = new ArrayList<>(entities);
		Collections.sort(array, new RenderSorter());
		for (IEntity entity : array) {
			this.processEntity(entity);
		}
	}
	
	private class RenderSorter implements Comparator<IEntity> {

		@Override
		public int compare(IEntity o1, IEntity o2) {
			TransformationComp trans1 = posCM.getComponent(o1);
			TransformationComp trans2 = posCM.getComponent(o2);
			return Float.compare(trans1.getPosition().Y,trans2.getPosition().Y);
		}
	
	}

}
