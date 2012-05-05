package entitySystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import math.Vector2;

import utils.Camera2D;
import utils.Rectangle;

import animation.Bone;
import animation.Keyframe;

import com.google.common.collect.ImmutableSet;

import components.AnimationComp;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.SpriteBatch;

public class AnimationSystem extends EntityProcessingSystem {

	private SpriteBatch batch;
	private Camera2D camera;
	
	public AnimationSystem(IEntityWorld world, SpriteBatch batch, Camera2D camera) {
		super(world, AnimationComp.class, TransformationComp.class);
		this.batch = batch;
		this.camera = camera;
	}

	private ComponentMapper<AnimationComp> aCM;
	private ComponentMapper<TransformationComp> tCM;

	@Override
	public void initialize() {
		aCM = ComponentMapper.create(this.getWorld().getDatabase(),
				AnimationComp.class);
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);
	}
	@Override
	public void process(){
		super.process();
	}

	
	protected void processEntity(IEntity entity) {
		AnimationComp animationC = this.aCM.getComponent(entity);
		TransformationComp positionC = this.tCM.getComponent(entity);
		
		//The updating for animations should be in it's own system, however it does not make sense to do the 
		//Object culling 2 times so it's here until a better solution is available.
		
		float delta = (float)this.getWorld().getTime().getElapsedTime().getTotalSeconds();
		
		for (Keyframe frame : animationC.getAnimationPlayer().getCurrentAnimationAnimation().getKeyframes()) {
			Bone bone = frame.getRootBone();	
			bone.setRotation(positionC.getRotation());
		}
		
		animationC.getAnimationPlayer().update(delta);
		animationC.getAnimationPlayer().draw(batch, positionC.getPosition(), positionC.getMirror(), 
				0, animationC.getTint(), positionC.getScale());
	}
	
	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
			
		ArrayList<IEntity> sortedEntities = new ArrayList<>(entities.size());

		Rectangle visibleArea = camera.getVisibleArea();
		for (IEntity entity : entities) {
			if(isVisible(entity, visibleArea)) {
				sortedEntities.add(entity);
			}
		}
		
		
		//Sorts the entities based on their y position.
		//Sometimes for reason unown to me this does not work.
		Collections.sort(sortedEntities, new RenderSorter());
		
		for (IEntity entity : sortedEntities) {
			this.processEntity(entity);
		}
	}
	
	private boolean isVisible(IEntity entity, Rectangle visibleArea) {
		TransformationComp transComp = tCM.getComponent(entity);
	//	AnimationComp animationComp = aCM.getComponent(entity);
		
		//Here animation.getBounds should be.
		//Below is temporary hack until getBounds is implemented. 
		Vector2 pos = transComp.getPosition();
		
		Rectangle rect = new Rectangle((int)pos.X , (int)pos.Y , 1, 1);
		return visibleArea.intersects(rect);
		
	}

	private class RenderSorter implements Comparator<IEntity> {

		@Override
		public int compare(IEntity o1, IEntity o2) {
			TransformationComp trans1 = tCM.getComponent(o1);
			TransformationComp trans2 = tCM.getComponent(o2);

			return Float.compare(trans1.getPosition().Y, trans2.getPosition().Y);
		}
	
	}
}
