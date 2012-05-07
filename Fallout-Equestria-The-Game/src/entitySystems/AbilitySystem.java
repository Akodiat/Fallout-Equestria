package entitySystems;

import components.AbilityComp;

import entityFramework.ComponentMapper;
import entityFramework.EntitySingleProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class AbilitySystem extends EntitySingleProcessingSystem{
	
	public AbilitySystem(IEntityWorld world) {
		super(world, AbilityComp.class);
	}
	
	private ComponentMapper<AbilityComp> abilityCM;
	
	@Override
	public void initialize() {
		this.abilityCM = ComponentMapper.create(getDatabase(), AbilityComp.class);
	}

	@Override
	protected void processEntity(IEntity entity) {
		AbilityComp abilityComp = abilityCM.getComponent(entity);
		abilityComp.update(this.getWorld().getTime());
	}

}
