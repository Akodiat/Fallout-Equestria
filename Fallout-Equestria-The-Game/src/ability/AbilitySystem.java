package ability;

import java.util.HashMap;
import java.util.Map;

import misc.IProcessor;

import com.google.common.collect.ImmutableSet;
import components.AbilityComp;

import entityFramework.EntityProcessingSystem;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;

public class AbilitySystem extends EntityProcessingSystem{

	private Map<Abilities, AbstractAbilityProcessor> abilities;
	
	public AbilitySystem(IEntityWorld world) {
		super(world, AbilityComp.class);
		abilities = new HashMap<>();
	}
	
	public AbilitySystem(IEntityWorld world,Map<Abilities, AbstractAbilityProcessor> abilities) {
		super(world, AbilityComp.class);
		this.abilities = new HashMap<>(abilities);
	}

	public void addAbilityProcessor(AbstractAbilityProcessor processor){
		abilities.put(processor.ability, processor);
	}
	public void addAbilityProcessors(Map<Abilities, AbstractAbilityProcessor> abilities){
		this.abilities.putAll(abilities);
	}
	
	@Override
	public void initialize() {
		for (IProcessor processor : abilities.values()) {
			processor.initialize(this.getEntityManager());
		}
	}

	@Override
	protected void processEntities(ImmutableSet<IEntity> entities) {
		for (IEntity entity : entities) {
			AbilityComp abiComp = entity.getComponent(AbilityComp.class);
			Abilities ability = abiComp.getAbility();
			if (ability != null && ability != Abilities.None && abiComp.getAbilityPoints() >= ability.Cost){
				IProcessor processor = this.abilities.get(ability);
							
				processor.validate(entity);
				processor.processEntity(entity, getEntityManager());
				
				abiComp.removeAbilityPoints(ability.Cost);
				abiComp.setAbility(Abilities.None);
			} else {
				abiComp.setAbility(Abilities.None);
			}
		}
		
	}

}
