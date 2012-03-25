package entityFramework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableSet;


public final class EntityArchetype implements IEntityArchetype{

	//Since the components should not be changed.
	private ImmutableSet<IComponent> components;
	
	//Since the default groups should not be changed.
	private ImmutableSet<String> groups;
	
	public EntityArchetype(Collection<IComponent> componentCollection) {
		this(componentCollection, new ArrayList<String>());
	}
	
	public EntityArchetype(Collection<IComponent> componentCollection, Collection<String> groups) {
		this.components = ImmutableSet.copyOf(componentCollection);
		this.groups = ImmutableSet.copyOf(groups);
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
	
	@Override
	public ImmutableSet<String> getGroups() {
		return this.groups;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if(this.groups.size() > 0) {
			builder.append("Part of groups" + "\n");
			for (String group : this.groups) {
				builder.append(group + "\n");
			}
		}
		
		for (IComponent component : this.components) {
			builder.append(component.toString() + "\n");
		}
		return builder.toString();
	}

}
