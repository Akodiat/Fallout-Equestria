package entitySystems;

import math.MathHelper;
import math.Vector2;
import components.BasicAIComp;
import components.PhysicsComponent;
import components.TransformationComp;

import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class BasicAISystem extends EntitySingleProcessingSystem{

	public BasicAISystem(IEntityWorld world) {
		super(world, BasicAIComp.class, PhysicsComponent.class, TransformationComp.class);
	}
	
	private ComponentMapper<BasicAIComp> basicAICM;
	private ComponentMapper<PhysicsComponent> physCM;
	private ComponentMapper<TransformationComp> transCM;
	

	@Override
	public void initialize() {
		basicAICM  = ComponentMapper.create(this.getWorld().getDatabase(), BasicAIComp.class);
		physCM = ComponentMapper.create(this.getWorld().getDatabase(), PhysicsComponent.class);
		transCM = ComponentMapper.create(this.getWorld().getDatabase(), TransformationComp.class);	
	}

	@Override
	protected void processEntity(IEntity entity) {
		BasicAIComp AIComp = basicAICM.getComponent(entity);
		PhysicsComponent physComp = physCM.getComponent(entity);
		TransformationComp transComp = transCM.getComponent(entity);
		
		Vector2 target = Vector2.norm(Vector2.subtract(AIComp.getTarget(), transComp.getPosition()));
		physComp.setVelocity(Vector2.rotate(target, ((float) Math.random()*MathHelper.TwoPi-MathHelper.Pi)));
		
	}

}
