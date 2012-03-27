package entitySystems;

import utils.Timer;
import components.AnimationComp;
import components.RenderingComponent;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class AnimationSystem extends EntitySingleProcessingSystem {

	public AnimationSystem(IEntityWorld world) {
		super(world, AnimationComp.class, TransformationComp.class,
				RenderingComponent.class);
	}

	private ComponentMapper<AnimationComp> aCM;
	private ComponentMapper<TransformationComp> tCM;
	private ComponentMapper<RenderingComponent> rCM;

	@Override
	public void initialize() {
		aCM = ComponentMapper.create(this.getWorld().getDatabase(),
				AnimationComp.class);
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);
		rCM = ComponentMapper.create(this.getWorld().getDatabase(),
				RenderingComponent.class);
	}
	@Override
	public void process(){
		super.process();
		Timer.updateTimers(1f/60f);
	}

	@Override
	protected void processEntity(IEntity entity) {
		AnimationComp animationC = this.aCM.getComponent(entity);
		TransformationComp positionC = this.tCM.getComponent(entity);
		RenderingComponent renderC = this.rCM.getComponent(entity);
		
		renderC.setSource(animationC.getActiveAnimation().getActiveFrame().getSourceRect());
		positionC.setOrigin(animationC.getActiveAnimation().getActiveFrame().getOrigin());
		
	}

}
