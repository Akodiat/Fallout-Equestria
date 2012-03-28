package ability;

import com.google.common.collect.ImmutableList;

import utils.Circle;
import math.Vector2;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
import components.*;
public class BuckAbility extends Ability{

	
	public BuckAbility(int apCost, float cooldown) {
		super(apCost, cooldown);
	}

	@Override
	public void use(IEntity sourceEntity, Vector2 targetPos, IEntityManager manager) {
		//TODO: Unfinished!
		Vector2 buckOrigin = sourceEntity.getComponent(TransformationComp.class).getPosition();
		int buckStrenght = 9 * sourceEntity.getComponent(SpecialComp.class).getStrength();
		
		AttackComponent attackComp = new AttackComponent();
		attackComp.setSourceEntity(sourceEntity);
		attackComp.setBounds(new Circle(Vector2.Zero,30));
		attackComp.setDamage(buckStrenght);
		attackComp.setDestoryOnHit(true);
		
		Vector2 attackDirection = Vector2.norm(Vector2.subtract(targetPos, buckOrigin));
		
		IEntity buck = manager.createEmptyEntity();
		
		TransformationComp transpComp = sourceEntity.getComponent(TransformationComp.class).clone();

		buck.addComponent(attackComp);
		buck.addComponent(transpComp);
		
		buck.refresh();
		
	}

}
