package entityFramework;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.inject.*;

/**
 * 
 * @author Lukas Kurtyan
 *
 */
public class EntityManager implements IEntityManager{

	//Events 
	private final Set<EntityChangedListener> 	changedEvent;
	private final Set<EntityDestroyedListener> destroyedEvent;
	
	private final IEntityLabelManager 	labelManager;
	private final Set<IEntity> 			toBeRemoved;
	private final IEntityGroupManager   groupManager;
	private final IEntityFactory 		factory;
	private final IEntityDatabase	    database;
	
	@Inject
	public EntityManager(IEntityLabelManager labelManager,
						 IEntityGroupManager groupManager, 
						 IEntityFactory factory, 
						 IEntityDatabase database) {
		this.changedEvent   = new HashSet<>();
		this.destroyedEvent = new HashSet<>();
		this.toBeRemoved    = new HashSet<>();
		this.labelManager   = labelManager;
		this.groupManager   = groupManager;
		this.factory 		= factory;
		this.database 	    = database;
	}
	
	@Override
	public IEntity createEmptyEntity() {
		IEntity entity = factory.createEmptyEntity(this, database);
		return entity;
	}

	@Override
	public IEntity createEntity(IEntityArchetype archetype) {
		IEntity entity = factory.createEntity(archetype, this, database);
		return entity;
	}
	
	@Override
	public IEntity createEntity(ImmutableSet<IComponent> components) {
		IEntity entity = factory.createEntity(components, this, database);
		return entity;
	}

	@Override
	public IEntity getEntity(int entityID) {
		return this.database.getEntity(entityID);
	}

	@Override
	public IEntity getEntity(String entityLabel) {
		return this.labelManager.getEntityFromLabel(entityLabel);
	}

	@Override
	public ImmutableSet<IEntity> getEntityGroup(String groupID) {
		return this.groupManager.getGroup(groupID);
	}

	@Override
	public void destoryKilledEntities() {
		for (IEntity entity : this.toBeRemoved) {
			this.onRemoved(entity);
			
			this.removeFromGroups(entity);
			this.removeLabel(entity);
			this.removeFromDatabase(entity);
			this.refreshEntity(entity);
			this.giveBackToFactory(entity);
			entity = null;
		}
		this.toBeRemoved.clear();
	}
	

	@Override
	public void killEntity(IEntity entity) {
		this.toBeRemoved.add(entity);
	}


	private void removeFromDatabase(IEntity entity) {
		this.database.removeEntity(entity.getUniqueID());
	}

	private void removeLabel(IEntity entity) {
		this.labelManager.removeLabelEntity(entity);
	}

	private void removeFromGroups(IEntity entity) {
		this.groupManager.removeEntityFromAllGroups(entity);
	}

	private void giveBackToFactory(IEntity entity) {
		this.factory.makeEntityAvalible(entity);
	}

	
	@Override
	public void refreshEntity(IEntity entity) {
		this.onChanged(entity);
	}

	@Override
	public void refreshDatabase() {
		ImmutableSet<IEntity> entities = this.database.getAllEntities();
		for (IEntity entity : entities) {
			this.onChanged(entity);
		}
	}

	@Override
	public void labelEntity(IEntity entity, String label) {
		this.labelManager.setLabelToEntity(entity, label);
	}

	@Override
	public void groupEntity(IEntity entity, String entityGroup) {
		this.groupManager.addEntityToGroup(entity, entityGroup);
	}

	@Override
	public void unLabelEntity(IEntity entity) {
		this.labelManager.removeLabelEntity(entity);
	}

	@Override
	public void ungroupEntity(IEntity entity, String entityGroup) {
		this.groupManager.removeEntityFromGroup(entity, entityGroup);
	}
	
	@Override
	public String getEntityLabel(IEntity entity) {
		return this.labelManager.getLabelFromEntity(entity);
	}

	@Override
	public ImmutableSet<String> getGroupsOfEntity(IEntity entity) {
		return this.groupManager.getGroupsOfEntity(entity);
	}

	private void onChanged(IEntity entity) {
		for (EntityChangedListener listener : this.changedEvent) {
			listener.entityChanged(entity);
		}
	}

	private void onRemoved(IEntity entity) {
		for (EntityDestroyedListener listener : this.destroyedEvent) {
			listener.entityDestroyed(entity);
		}
	}

	@Override
	public void addEntityChangedListener(EntityChangedListener changedListener) {
		this.changedEvent.add(changedListener);
	}
	
	@Override
	public void removeEntityChangedListener(
			EntityChangedListener changedListener) {
		this.changedEvent.remove(changedListener);
	}

	@Override
	public void addEntityDestoryedListener(
			EntityDestroyedListener destroyedListener) {
		this.destroyedEvent.add(destroyedListener);
	}

	@Override
	public void removeEntityDestoryedListener(
			EntityDestroyedListener destroyedListener) {
		this.destroyedEvent.remove(destroyedListener);
	}

}
