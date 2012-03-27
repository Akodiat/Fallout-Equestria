package ability;

import java.util.List;

import com.google.common.collect.ImmutableSet;

import math.Vector2;
import entityFramework.IEntity;
import entityFramework.IEntityManager;

public class TelekinesisAbility extends Ability{

	public TelekinesisAbility(int apCost, float cooldown) {
		super(apCost, cooldown);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useAbility(IEntity sourceEntity, Vector2 targetPos,
			IEntityManager manager) {
		ImmutableSet<IEntity> pickupableSet = manager.getEntityGroup("Pickup-able");
		List<IEntity> pickupable = pickupableSet.asList();
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

}
