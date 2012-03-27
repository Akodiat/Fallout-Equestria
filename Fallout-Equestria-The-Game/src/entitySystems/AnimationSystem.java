package entitySystems;

import components.AnimationComp;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import graphics.Color;
import graphics.SpriteBatch;

public class AnimationSystem extends EntitySingleProcessingSystem {

	private SpriteBatch graphics;

	public AnimationSystem(IEntityWorld world, SpriteBatch graphics) {
		super(world, AnimationComp.class, TransformationComp.class/*,
				RenderingComponent.class*/);
		this.graphics = graphics;
	}

	private ComponentMapper<AnimationComp> aCM;
	private ComponentMapper<TransformationComp> tCM;
	//private ComponentMapper<RenderingComponent> rCM;

	@Override
	public void initialize() {
		aCM = ComponentMapper.create(this.getWorld().getDatabase(),
				AnimationComp.class);
		tCM = ComponentMapper.create(this.getWorld().getDatabase(),
				TransformationComp.class);
		/*rCM = ComponentMapper.create(this.getWorld().getDatabase(),
				RenderingComponent.class);*/
	}

	@Override
	protected void processEntity(IEntity entity) {
		AnimationComp animationC = this.aCM.getComponent(entity);
		TransformationComp positionC = this.tCM.getComponent(entity);
		//RenderingComponent renderC = this.rCM.getComponent(entity);

		animationC.getActiveAnimation().getTimer().updateTimers(1f/60f);

		this.draw(positionC, animationC);

	}

	private void draw(TransformationComp positionC,
			AnimationComp animationC) {
		this.graphics.draw(animationC.getSpriteSheet(),
				positionC.getPosition(), Color.White, 
				animationC.getActiveAnimation().getActiveFrame().getSourceRect(),
				animationC.getActiveAnimation().getActiveFrame().getOrigin(),
				1f, 0f, false);
	}

}
