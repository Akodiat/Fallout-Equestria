package entitySystems;

import components.AnimationComp;
import components.RenderingComp;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class AnimationSystem extends EntitySingleProcessingSystem {

	public AnimationSystem(IEntityWorld world) {
		super(world, AnimationComp.class, TransformationComp.class,
				RenderingComp.class);
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
		
		animationC.getActiveAnimation().update(this.getWorld().getTime().DeltaTime);
		
		renderC.setSource(animationC.getActiveAnimation().getActiveFrame().getSourceRect());
		positionC.setOrigin(animationC.getActiveAnimation().getActiveFrame().getOrigin());
	}

}
