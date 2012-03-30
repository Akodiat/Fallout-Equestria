package misc;

import ability.Abilities;

import com.google.common.collect.ImmutableSet;

import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityManager;

public abstract class AbstractProcessor implements IProcessor {

	private final ImmutableSet<Class< ? extends IComponent>> componentClasses;
	
	@SafeVarargs
	public AbstractProcessor(Class<? extends IComponent> ...componentsClasses) {
		this.componentClasses = ImmutableSet.copyOf(componentsClasses);
	}
	
	/* (non-Javadoc)
	 * @see ability.Processor#validate(entityFramework.IEntity)
	 */
	@Override
	public void validate(IEntity entity){
		ImmutableSet<IComponent> components = entity.getComponents();
		boolean valid;
		for (Class< ? extends IComponent> componentClass : componentClasses) {
			valid = false;
			for (IComponent component : components) {
				if(component.getClass().equals(componentClass)) {
					valid = true;
					break;
				}
			}
			if(!valid) {
				throw new ComponentNotFoundException(componentClass.toString() + "\nmissing in entity\n" + entity.toString());
			}
			
		}
	}
	
	/* (non-Javadoc)
	 * @see ability.Processor#processEntity(entityFramework.IEntity, entityFramework.IEntityManager)
	 */
	@Override
	public abstract void processEntity(IEntity entity, IEntityManager manager);
	/* (non-Javadoc)
	 * @see ability.Processor#initialize(entityFramework.IEntityManager)
	 */
	@Override
	public abstract void initialize(IEntityManager manager);
}
