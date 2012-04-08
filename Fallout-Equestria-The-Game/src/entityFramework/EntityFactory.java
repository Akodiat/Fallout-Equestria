package entityFramework;

import java.util.LinkedList;
import java.util.Queue;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Singleton;

@Singleton
public class EntityFactory implements IEntityFactory{

	private Queue<IEntity> recyclableEntities;
	private int nextAvalibleID;
	
	public EntityFactory() {
		this.recyclableEntities = new LinkedList<IEntity>();
	}
	
	@Override
	public void makeEntityAvalible(IEntity entity) {
		this.recyclableEntities.add(entity);
	}

	@Override
	public IEntity createEmptyEntity(IEntityManager manager,
			IEntityDatabase database) {
		if(!this.recyclableEntities.isEmpty()) {
			IEntity entity = this.recyclableEntities.remove();
			database.addEntity(entity);
			return entity;
		}
		
		IEntity entity = new Entity(nextAvalibleID, manager, database);
		database.addEntity(entity);
		
		this.nextAvalibleID++;
		
		return entity;
	}

	@Override
	public IEntity createEntity(IEntityArchetype archetype,
			IEntityManager manager, IEntityDatabase database) {
		IEntity entity = this.createEmptyEntity(manager, database);
		ImmutableSet<IComponent> components = archetype.getComponents();
		for (IComponent component : components) {
			entity.addComponent(component);
		}
		ImmutableSet<String> groups = archetype.getGroups();
		for (String group : groups) {
			entity.addToGroup(group);
		}
		String label = archetype.getLabel();
		if(label != null && label != "")
			entity.setLabel(label);
		
		entity.refresh();
		return entity;
	}

	@Override
	public IEntity createEntity(ImmutableSet<IComponent> components,
			EntityManager manager, IEntityDatabase database) {
		IEntity entity = this.createEmptyEntity(manager, database);
		for (IComponent component : components) {
			entity.addComponent(component);
		}
		
		return entity;
	}
	

	
}
