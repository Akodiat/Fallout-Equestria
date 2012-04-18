package entitySystems;

import components.AnimationComp;
import components.RenderingComp;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.SpriteBatch;

public class AnimationSystem extends EntitySingleProcessingSystem {

	private SpriteBatch batch;
	
	public AnimationSystem(IEntityWorld world, SpriteBatch batch) {
		super(world, AnimationComp.class, TransformationComp.class,
				RenderingComp.class);
		this.batch = batch;
	}

	private ComponentMapper<AnimationComp> aCM;
	private ComponentMapper<TransformationComp> tCM;
	private ComponentMapper<RenderingComp> rCM;

	@Override
	public void initialize() {
		aCM = ComponentMapper.create(this.getWorld().getDatabase(),
				AnimationComp.class);
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);
		rCM = ComponentMapper.create(this.getWorld().getDatabase(),
				RenderingComp.class);
	}
	@Override
	public void process(){
		super.process();
	}

	@Override
	protected void processEntity(IEntity entity) {
		AnimationComp animationC = this.aCM.getComponent(entity);
		TransformationComp positionC = this.tCM.getComponent(entity);
		RenderingComp renderC = this.rCM.getComponent(entity);
		
		animationC.getAnimationPlayer().update(this.getWorld().getTime().DeltaTime);
		animationC.getAnimationPlayer().draw(batch, positionC.getPosition(), positionC.getMirror(), 
				positionC.getRotation(), renderC.getColor(), positionC.getScale());
	}

}
