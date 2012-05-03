package entityFramework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableSet;

import components.PhysicsComp;
import components.TransformationComp;

/**
 * 
 * @author Joakim Johansson
 *
 */
public final class EntityArchetypeProxy implements IEntityArchetype{

	//Since the components should not be changed.
	private ImmutableSet<IComponent> components;
	
	//Since the default groups should not be changed.
	private ImmutableSet<String> groups;
	
	private String label;
	
	
	public EntityArchetypeProxy(EntityArchetype realArchetype) {
		this.components = ImmutableSet.copyOf(new ArrayList<IComponent>());
		List<IComponent> originalComponentList = realArchetype.getComponents().asList();
		
		for (IComponent component : originalComponentList) {
			if((component instanceof TransformationComp) || (component instanceof PhysicsComp))
				this.components.add(component);
		}
		this.groups = realArchetype.getGroups();
		this.label = realArchetype.getLabel();
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

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public void setLabel(String label) {
		this.label=label;
	}

}
