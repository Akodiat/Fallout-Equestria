package entitySystems;


import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import ability.*;

import math.Vector2;
import components.*;
import content.ContentManager;

import entityFramework.*;
/**
 * 
 * @author Joakim Johansson
 *
 */
public class TrixieAISystem extends LabelEntitySystem{


	public TrixieAISystem(IEntityWorld world) {
		super(world, "Trixie", TransformationComp.class, AbilityComp.class, WeaponComp.class);
	}

	@Override
	public void initialize() {
	}

	@Override
	protected void processEntity(IEntity entity) {
		TransformationComp transComp = entity.getComponent(TransformationComp.class);
		AbilityComp abiComp = entity.getComponent(AbilityComp.class);
		WeaponComp weapComp = entity.getComponent(WeaponComp.class);

		List<Ability> abilities = abiComp.getListOfAbilities();
		
		Ability randAbility = abilities.get((int)(Math.random()*10)%abilities.size());
		
		Vector2 targetPos = new Vector2((float)Math.random() * Display.getWidth(),(float)Math.random() * Display.getHeight()); //Find random target because that's OK.

		if(!(randAbility instanceof TeleportAbility)){ //If performing ability against random target isn't OK after all...
			//Set target to the nearest entity from the group "Friends":
			
			List<IEntity> targetList= this.getWorld().getEntityManager().getEntityGroup("Friends").asList();
			if(targetList.isEmpty()){
				if(!ContentManager.loadSound("effects/wasThereEverAnyDoubt.ogg").isPlaying())
					ContentManager.loadSound("effects/wasThereEverAnyDoubt.ogg").playAsSoundEffect(1.0f, 1.0f, false);
				return; //No point in doing things if there isn't even a player to attack...
			}
			IEntity target = targetList.get(0);
			for(IEntity possibleTarget: targetList){
				if (Vector2.distance(transComp.getPosition(), possibleTarget.getComponent(TransformationComp.class).getPosition()) <
						Vector2.distance(transComp.getPosition(), target.getComponent(TransformationComp.class).getPosition()))
					target = possibleTarget;

				targetPos = target.getComponent(TransformationComp.class).getPosition();
			}
		}

		randAbility.useAbility(entity, targetPos, this.getWorld().getEntityManager());
	}
	

	@Override
	protected void processMissingEntity() {
		// TODO Auto-generated method stub

	}
}

