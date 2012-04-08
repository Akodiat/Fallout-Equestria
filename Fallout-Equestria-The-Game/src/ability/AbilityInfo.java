package ability;

import entityFramework.IEntityArchetype;

public class AbilityInfo {
	
	public static final AbilityInfo None = new AbilityInfo(Abilities.None, null);
	private final IEntityArchetype archetype;
	private final Abilities ability;
	
	public AbilityInfo(Abilities ability) {
		this(ability, null);
	}
	
	public AbilityInfo(Abilities ability, IEntityArchetype archetype) {
		this.ability 	= ability;
		this.archetype  = archetype;
	}

	public IEntityArchetype getArchetype() {
		return archetype;
	}

	public Abilities getAbility() {
		return ability;
	}
	
	public String toString() {
		return this.ability.toString();
	}
}
