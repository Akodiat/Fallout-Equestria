package entityFramework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableSet;


public class EntityArchetype implements IEntityArchetype{

	//Since the components should not be changed we
	private ImmutableSet<IComponent> components;
	
	public EntityArchetype(Collection<IComponent> componentCollection) {
		this.components = ImmutableSet.copyOf(componentCollection);
	}

	@Override
	public ImmutableSet<IComponent> getComponents() {
		//Clones all of the components.
		List<IComponent> clonedList = new ArrayList<IComponent>(components.size());
		for (IComponent component : this.components) {
			IComponent clone = (IComponent)component.clone();
			clonedList.add(clone);
		}
		return ImmutableSet.copyOf(clonedList);
	}

}
