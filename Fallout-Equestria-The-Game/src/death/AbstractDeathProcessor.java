package death;

import entityFramework.IComponent;
import misc.AbstractProcessor;

public abstract class AbstractDeathProcessor extends AbstractProcessor {

	protected final DeathActions deathAction;
	
	@SafeVarargs
	public AbstractDeathProcessor(DeathActions deathAction, Class<? extends IComponent> ...componentsClasses) {
		super(componentsClasses);
		this.deathAction = deathAction;
	}
}
