package entityFramework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableSet;

public class EntityGroupManager implements IEntityGroupManager{
	
	private final Map<IEntity, List<String>> entityToGroups;
	private final Map<String, List<IEntity>> entityGroups;
	
	
	public EntityGroupManager() {
		this.entityGroups = new HashMap<String, List<IEntity>>();
		this.entityToGroups = new HashMap<IEntity, List<String>>();
	}
	
	@Override
	public ImmutableSet<IEntity> getGroup(String groupName) {
		List<IEntity> group = this.entityGroups.get(groupName);
		if(group == null) {
			group = new ArrayList<IEntity>();
		}
		return ImmutableSet.copyOf(group);
	}

	@Override
	public ImmutableSet<String> getGroupsOfEntity(IEntity entity) {
		List<String> groups = this.entityToGroups.get(entity);
		if(groups == null) {
			groups = new ArrayList<String>();
		}
		
		return ImmutableSet.copyOf(groups);
	}

	@Override
	public void addEntityToGroup(IEntity entity, String groupName) {
		if(!this.entityToGroups.containsKey(entity)) {
			this.entityToGroups.put(entity, new ArrayList<String>());
		}
		
		if(!this.entityToGroups.get(entity).contains(groupName)) {		
			List<IEntity> group = this.entityGroups.get(groupName);
			if(group == null) {
				group = new ArrayList<IEntity>();
				this.entityGroups.put(groupName, group);
			}
			group.add(entity);
			this.entityToGroups.get(entity).add(groupName);
		}
	}

	@Override
	public void removeEntityFromGroup(IEntity entity, String groupName) {
		List<IEntity> group = this.entityGroups.get(groupName);
		if(group != null) {
			group.remove(entity);
			if(group.isEmpty()) {
				this.entityGroups.remove(groupName);
			}
			
			this.entityToGroups.get(entity).remove(groupName);
			if(this.entityToGroups.get(entity).isEmpty()) {
				this.entityToGroups.remove(entity);
			}	
		}
	}
	
	@Override
	public void removeEntityFromAllGroups(IEntity entity) {
		List<String> groups = this.entityToGroups.get(entity);
		if(groups != null) {
			for (int i = groups.size() - 1; i >= 0; i--) {
				this.removeEntityFromGroup(entity, groups.get(i));
			}
			this.entityToGroups.remove(entity);
		}	
	}
}
