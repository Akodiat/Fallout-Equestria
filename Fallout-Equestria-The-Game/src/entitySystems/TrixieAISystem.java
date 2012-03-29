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
	
	private List<Ability> abilities;

	public TrixieAISystem(IEntityWorld world) {
		super(world, "Trixie", TransformationComp.class, AbilityComp.class, WeaponComp.class);
	}

	@Override
	public void initialize() {
		this.abilities = new ArrayList<Ability>();
		this.abilities.add(new TeleportAbility(5,10,null));
		this.abilities.add(new CircleProjectileAbility(ContentManager.loadArchetype("ppieBullet.archetype"), 5, 5, 10, 10));
	}

	@Override
	protected void processEntity(IEntity entity) {
		TransformationComp transComp = entity.getComponent(TransformationComp.class);
		AbilityComp abiComp = entity.getComponent(AbilityComp.class);
		WeaponComp weapComp = entity.getComponent(WeaponComp.class);
		
		Ability randAbility = this.abilities.get((int)(Math.random()*10)%this.abilities.size());
		Vector2 targetPos = new Vector2((float)Math.random() * Display.getWidth(),(float)Math.random() * Display.getHeight()); //Find random target because that's OK.
		
		if(randAbility instanceof TeleportAbility){
			
		}
			
		
		
		
		
		
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
		
		
		randAbility.useAbility(entity, targetPos, this.getWorld().getEntityManager());
		
	}

	@Override
	protected void processMissingEntity() {
		// TODO Auto-generated method stub
		
	}
}

