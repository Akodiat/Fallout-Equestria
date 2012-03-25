package entitySystems;

import java.util.List;

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

		//Set target to the nearest entity from the group "Friends"
		List<IEntity> targetList= this.getWorld().getEntityManager().getEntityGroup("Friends").asList();
		IEntity target = targetList.get(0);
		for(IEntity possibleTarget: targetList){
			if (Vector2.distance(transComp.getPosition(), transCM.getComponent(possibleTarget).getPosition()) <
					Vector2.distance(transComp.getPosition(), transCM.getComponent(target).getPosition()))
				target = possibleTarget;

			AIComp.setTarget(transCM.getComponent(target).getPosition());
		}
		
		Vector2 targetDirection = Vector2.norm(Vector2.subtract(AIComp.getTarget(), transComp.getPosition()));
		//physComp.setVelocity(Vector2.rotate(targetDirection, ((float) Math.random()*MathHelper.TwoPi-MathHelper.Pi)));
		physComp.setVelocity(targetDirection);
	}

}
