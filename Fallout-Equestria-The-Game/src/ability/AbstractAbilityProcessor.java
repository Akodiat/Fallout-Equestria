package ability;


import misc.AbstractProcessor;

import entityFramework.IComponent;

public abstract class AbstractAbilityProcessor extends AbstractProcessor {

	protected final Abilities ability;
	
	@SafeVarargs
	public AbstractAbilityProcessor(Abilities ability, Class<? extends IComponent> ...componentsClasses) {
		super(componentsClasses);
		this.ability = ability;
	}
}
