package common;

import misc.EventArgs;
import entityFramework.IEntity;

public class EntityEventArgs extends EventArgs{
	private final String entityArchetype;
	private final IEntity entity;
	
	public EntityEventArgs(String entityArchetype, IEntity entity) {
		this.entityArchetype = entityArchetype;
		this.entity = entity;
	}

	public String getEntityArchetype() {
		return entityArchetype;
	}

	public IEntity getEntity() {
		return entity;
	}
}
