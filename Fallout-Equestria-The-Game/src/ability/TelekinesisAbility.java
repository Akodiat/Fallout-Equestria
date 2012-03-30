package ability;

import java.util.List;

import com.google.common.collect.ImmutableSet;

import components.AbilityComp;
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
public class TelekinesisAbility extends AbstractAbilityProcessor{

	private static final float pickUpRadius = 20; //TODO: Make depend on magic skill?
	private boolean notPicking = true; //TODO: Change this so that is makes sense. 

	public TelekinesisAbility() {
		super(Abilities.Telekinesis, TransformationComp.class);
	}

	@Override
	public void processEntity(IEntity entity, IEntityManager manager) {
		
		System.out.println("HEJEHEHEJHEJHEJHEJHEJHE");
		
		Vector2 sourcePos = entity.getComponent(TransformationComp.class).getPosition();
		Vector2 mousePos = entity.getComponent(InputComp.class).getMousePosition();
		
		ImmutableSet<IEntity> pickupableSet = manager.getEntityGroup("Pickup-able");
		List<IEntity> pickupables = pickupableSet.asList();


		if(pickupables.isEmpty()){
			return;
		}

		IEntity toPickUp = null;
		float minCloseness = pickUpRadius;

		for(IEntity pickupable: pickupables){
			float currentCloseness = Vector2.distance(pickupable.getComponent(TransformationComp.class).getPosition(), mousePos);
			System.out.println("Current closeness: "+ currentCloseness);
			System.out.println("Minimum closeness: "+ minCloseness);
			if(currentCloseness < minCloseness ){ //If a entity that is pickup-able is found closer to the target, use it instead
				toPickUp = pickupable;
				minCloseness = currentCloseness;
			}
		}
		
		if(toPickUp != null){
			toPickUp.addComponent(new AntiGravityComp(entity.getUniqueID()));
			toPickUp.refresh();
			this.notPicking = false;
		}
		
	}

	@Override
	public void initialize(IEntityManager manager) {
		// TODO Auto-generated method stub
		
	}
}
