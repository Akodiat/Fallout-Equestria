package entitySystems;

import java.util.List;

import ability.Ability;
import ability.BulletAbility;

import math.Vector2;
import components.*;
import content.ContentManager;
import entityFramework.*;

//TODO should revisit since we have changed the Animation Component. 
public class BasicAISystem extends EntitySingleProcessingSystem{

	public BasicAISystem(IEntityWorld world) {
		super(world, BasicAIComp.class, PhysicsComponent.class, TransformationComp.class);
	}

	private ComponentMapper<BasicAIComp> basicAICM;
	private ComponentMapper<PhysicsComponent> physCM;
	private ComponentMapper<TransformationComp> transCM;
	
	private Ability pinkieRocket;

	@Override
	public void initialize() {
		basicAICM  = ComponentMapper.create(this.getWorld().getDatabase(), BasicAIComp.class);
		physCM = ComponentMapper.create(this.getWorld().getDatabase(), PhysicsComponent.class);
		transCM = ComponentMapper.create(this.getWorld().getDatabase(), TransformationComp.class);

		IEntityArchetype archetype = ContentManager.loadArchetype("ppieBullet.archetype");
		this.pinkieRocket = new BulletAbility(archetype, 10, 1, 4);
	}

	@Override
	protected void processEntity(IEntity entity) {
		BasicAIComp AIComp = basicAICM.getComponent(entity);
		PhysicsComponent physComp = physCM.getComponent(entity);
		TransformationComp transComp = transCM.getComponent(entity);

		//Set target to the nearest entity from the group "Friends"
		List<IEntity> targetList= this.getWorld().getEntityManager().getEntityGroup("Friends").asList();
		if(targetList.isEmpty())
			return; //No point in doing things if there isn't even a player to attack...
		
		IEntity target = targetList.get(0);
		for(IEntity possibleTarget: targetList){
			if (Vector2.distance(transComp.getPosition(), transCM.getComponent(possibleTarget).getPosition()) <
					Vector2.distance(transComp.getPosition(), transCM.getComponent(target).getPosition()))
				target = possibleTarget;

			AIComp.setTarget(transCM.getComponent(target).getPosition());
		}

		Vector2 targetDirection = Vector2.norm(Vector2.subtract(AIComp.getTarget(), transComp.getPosition()));
		//physComp.setVelocity(Vector2.rotate(targetDirection, (float) Math.random()*MathHelper.Pi-MathHelper.Pi/2));//((float) Math.random()*MathHelper.TwoPi-MathHelper.Pi)));
		physComp.setVelocity(targetDirection);

		if(Math.random()<0.003)
			this.pinkieRocket.useAbility(entity, transCM.getComponent(target).getPosition(), this.getWorld().getEntityManager());
			//shoot(targetDirection, transComp.getPosition());
	}
	
	/*private void shoot(Vector2 targetDirection, Vector2 position){
		IEntity attack = this.getWorld().getEntityManager().createEmptyEntity();			
		AttackComponent attackComp = new AttackComponent(new Circle(Vector2.Zero,10f),80, ImmutableList.of("Friends"));

		PhysicsComponent attackPhysComp = new PhysicsComponent(targetDirection);
		TransformationComp attackPosComp = new TransformationComp();
		SpatialComponent attackSpatComp = new SpatialComponent(new Circle(Vector2.Zero,5f)); 

		RenderingComponent attackRendComp = new RenderingComponent();
		attackRendComp.setColor(Color.Crimson);
		attackRendComp.setTexture(attackTexture);

		attackPosComp.setRotation(targetDirection.angle());
		attackPosComp.setPosition(Vector2.add(position, Vector2.mul(60, targetDirection)));
		attackPosComp.setOrigin(new Vector2(attackTexture.Width/2,attackTexture.Height/2));

		attack.addComponent(attackPosComp);
		attack.addComponent(attackPhysComp);
		attack.addComponent(attackComp);
		attack.addComponent(attackRendComp);
		//attack.addComponent(attackSpatComp);

		attack.refresh();
	}*/

}
