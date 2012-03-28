package entitySystems;


import java.util.List;

import org.lwjgl.opengl.Display;

import ability.Ability;

import math.Vector2;
import components.*;

import entityFramework.*;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class TrixieAISystem extends LabelEntitySystem{
	
	private List<Ability> abilities;

	public TrixieAISystem(IEntityWorld world) {
		super(world, "Trixie", TransformationComp.class, AbilityComp.class, WeaponComp.class);
	}

	@Override
	public void initialize() {
		//Add abilities here!
	}

	@Override
	protected void processEntity(IEntity entity) {
		TransformationComp transComp = entity.getComponent(TransformationComp.class);
		AbilityComp abiComp = entity.getComponent(AbilityComp.class);
		WeaponComp weapComp = entity.getComponent(WeaponComp.class);
		
		Vector2 targetPos = new Vector2((float)Math.random() * Display.getWidth(),(float)Math.random() * Display.getHeight()); //Find random target because that's OK.
		
				//Set target to the nearest entity from the group "Friends"
				List<IEntity> targetList= this.getWorld().getEntityManager().getEntityGroup("Friends").asList();
				if(targetList.isEmpty())
					return; //No point in doing things if there isn't even a player to attack...
				
				IEntity target = targetList.get(0);
				for(IEntity possibleTarget: targetList){
					if (Vector2.distance(transComp.getPosition(), possibleTarget.getComponent(TransformationComp.class).getPosition()) <
							Vector2.distance(transComp.getPosition(), target.getComponent(TransformationComp.class).getPosition()))
						target = possibleTarget;

					targetPos = target.getComponent(TransformationComp.class).getPosition();
				}
		this.abilities.get((int)(Math.random()*10)%this.abilities.size()).useAbility(entity, targetPos, this.getWorld().getEntityManager());
		
	}

	@Override
	protected void processMissingEntity() {
		// TODO Auto-generated method stub
		
	}
}

