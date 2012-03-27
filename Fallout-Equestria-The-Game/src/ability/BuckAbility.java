package ability;

import com.google.common.collect.ImmutableList;

import utils.Circle;
import math.Vector2;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
import components.AttackComponent;
import components.PhysicsComponent;
import components.SpecialComp;
import components.TransformationComp;

public class BuckAbility extends Ability{

	
	public BuckAbility(int apCost, float cooldown) {
		super(apCost, cooldown);
	}

	@Override
	public void useAbility(IEntity sourceEntity, Vector2 targetPos, IEntityManager manager) {
		Vector2 buckOrigin = sourceEntity.getComponent(TransformationComp.class).getPosition();
		int buckStrenght = 9 * sourceEntity.getComponent(SpecialComp.class).getStrength();
		
		AttackComponent attackComp = new AttackComponent((new Circle(Vector2.Zero, 40)),buckStrenght,ImmutableList.of("Enemies"));
		Vector2 attackDirection = Vector2.norm(Vector2.subtract(targetPos, buckOrigin));
		
		PhysicsComponent physComp = new PhysicsComponent();
		IEntity buck = manager.createEmptyEntity();
		
		TransformationComp transpComp = sourceEntity.getComponent(TransformationComp.class).clone();
		
		buck.addComponent(physComp);
		buck.addComponent(attackComp);
		buck.addComponent(transpComp);
		
		buck.refresh();
		
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

}
