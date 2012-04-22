package entitySystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

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
		
		
		if(renderC.getEffect() != null) {
			this.spriteBatch.end();
			ShaderEffect effect = this.spriteBatch.getActiveEffect();
			Matrix4 view = this.spriteBatch.getView();
			
			this.spriteBatch.begin(renderC.getEffect(),this.spriteBatch.getView());

			draw(renderC, positionC);
			
			this.spriteBatch.end();
			this.spriteBatch.begin(effect, view);
		} else {
			this.draw(renderC, positionC);
		}
	}

	private void draw(RenderingComp renderC, TransformationComp transformation) {
		this.spriteBatch.draw(renderC.getTexture(), transformation.getPosition(), renderC.getColor(), renderC.getSource(),
							  new Vector2(transformation.getOrigin().X*transformation.getScale().X,transformation.getOrigin().Y*transformation.getScale().Y), transformation.getScale(), transformation.getRotation(), transformation.getMirror());
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		
		TreeSet<IEntity> treeSet = new TreeSet<>(new RenderSorter());
		treeSet.addAll(entities);
		
		for (IEntity entity : treeSet) {
			this.processEntity(entity);
		}
	}
	
	private class RenderSorter implements Comparator<IEntity> {

		@Override
		public int compare(IEntity o1, IEntity o2) {
			TransformationComp trans1 = posCM.getComponent(o1);
			TransformationComp trans2 = posCM.getComponent(o2);
			return (int) ((trans1.getPosition().Y - trans2.getPosition().Y));
		}
	
	}

}
