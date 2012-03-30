package ability;

import com.google.common.collect.ImmutableSet;

import entityFramework.GroupedEntitySystem;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public abstract class GroupedAbilityEntitySystem extends GroupedEntitySystem {

	private Abilities ability;
	
	@SuppressWarnings("unchecked")
	protected GroupedAbilityEntitySystem(IEntityWorld world, Abilities ability,
			Class<? extends IComponent>... componentsClasses) {
		super(world, ability.toString(), componentsClasses);
		this.ability = ability;
	}

	@Override
	protected final void processEntities(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			this.processEntity(entity);
			entity.removeFromGroup(ability.toString());
			entity.refresh();
		}
		
		
	}

	protected abstract void processEntity(IEntity entity);

}
