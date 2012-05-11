package common;

import content.ContentManager;
import misc.Event;
import misc.IEventListener;
import entityFramework.EntityFactory;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityDatabase;
import entityFramework.IEntityManager;

public class NetworkedEntityFactory extends EntityFactory {
	private Event<EntityEventArgs> entityCreated;
	private ContentManager contentManager;
	
	public NetworkedEntityFactory(ContentManager manager) {
		this.entityCreated = new Event<>();
		this.contentManager = manager;
	}
	
	public void addCreatedListener(IEventListener<EntityEventArgs> listener) {
		this.entityCreated.addListener(listener);
	}
	
	@Override
	public IEntity createEntity(IEntityArchetype archetype, IEntityManager manager, IEntityDatabase database) {
		IEntity entity = super.createEntity(archetype, manager, database);
		this.onEntityCreated(entity, this.contentManager.getContentName(archetype));
		return entity;
	}

	protected void onEntityCreated(IEntity entity, String archetypeString) {
		EntityEventArgs args = new EntityEventArgs(archetypeString, entity);
		this.entityCreated.invoke(this, args);
	}
}
