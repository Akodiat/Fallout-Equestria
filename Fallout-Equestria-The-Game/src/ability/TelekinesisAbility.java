package ability;

import java.util.List;

import com.google.common.collect.ImmutableSet;

import components.AbilityPointsComp;
import components.AntiGravityComp;
import components.InputComp;
import components.TransformationComp;

import math.Vector2;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class TelekinesisAbility extends Ability{

	private static final float pickUpRadius = 20; //TODO: Make depend on magic skill?
	private boolean notPicking = true; //TODO: Change this so that is makes sense. 

	public TelekinesisAbility(int apCost, float cooldown) {
		super(apCost, cooldown);
	}

	@Override
	public void use(IEntity sourceEntity, Vector2 targetPos, IEntityManager manager) {
		Vector2 sourcePos = sourceEntity.getComponent(TransformationComp.class).getPosition();
		Vector2 mousePos = sourceEntity.getComponent(InputComp.class).getMousePosition();
		
		ImmutableSet<IEntity> pickupableSet = manager.getEntityGroup("Pickup-able");
		List<IEntity> pickupable = pickupableSet.asList();


		if(pickupable.isEmpty()){
			return;
		}

		IEntity toPickUp = null;
		float minCloseness = pickUpRadius;

		for(IEntity entity: pickupable){
			float currentCloseness = Vector2.distance(entity.getComponent(TransformationComp.class).getPosition(), mousePos);
			System.out.println("Current closeness: "+ currentCloseness);
			System.out.println("Minimum closeness: "+ minCloseness);
			if(currentCloseness < minCloseness ){ //If a entity that is pickup-able is found closer to the target, use it instead
				toPickUp = entity;
				minCloseness = currentCloseness;
			}
		}
		if(toPickUp != null){
			toPickUp.addComponent(new AntiGravityComp(sourceEntity.getUniqueID()));
			toPickUp.refresh();
			this.notPicking = false;
		}
	}
	
	@Override
	protected boolean canUse(IEntity sourceEntity, Vector2 targetPos, IEntityManager manager, AbilityPointsComp apComp){
		return this.notPicking; //TODO: Change this so that is makes sense. 
	}
}
