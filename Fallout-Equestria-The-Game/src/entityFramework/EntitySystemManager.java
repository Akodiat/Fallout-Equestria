package entityFramework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EntitySystemManager implements IEntitySystemManager{
	
	private final List<IEntitySystem> logicSystems;
	private final List<IEntitySystem> renderSystems;
	private final IEntityManager entityManager;
	private boolean initialized;
	
	
	public EntitySystemManager(IEntityManager entityManager) {
		this.logicSystems = new ArrayList<IEntitySystem>();
		this.renderSystems = new ArrayList<IEntitySystem>();
		this.entityManager = entityManager;
		this.initialized = false;
	}
	
	@Override
	public void addLogicEntitySystem(IEntitySystem logicSystem) {
		this.logicSystems.add(logicSystem);
		this.entityManager.addEntityChangedListener(logicSystem);
		this.entityManager.addEntityDestoryedListener(logicSystem);
		
		
		if(!this.initialized) {
			this.sortSystem(logicSystems);
		}
	}

	@Override
	public void removeLogicEntitySystem(IEntitySystem logicSystem) {
		this.logicSystems.remove(logicSystem);
		this.entityManager.removeEntityChangedListener(logicSystem);
		this.entityManager.removeEntityDestoryedListener(logicSystem);
	}

	@Override
	public void addRenderEntitySystem(IEntitySystem renderSystem) {
		this.renderSystems.add(renderSystem);
		this.entityManager.addEntityChangedListener(renderSystem);
		this.entityManager.addEntityDestoryedListener(renderSystem);
		
		if(!this.initialized) {
			this.sortSystem(this.renderSystems);
		}
	}

	@Override
	public void removeRenderEntitySystem(IEntitySystem renderSystem) {
		this.renderSystems.remove(renderSystem);
		this.entityManager.removeEntityChangedListener(renderSystem);
		this.entityManager.removeEntityDestoryedListener(renderSystem);
	}

	@Override
	public void initialize() {
		this.initialized = true;
		this.sortSystem(logicSystems);
		this.sortSystem(renderSystems);
		
		for (IEntitySystem entitySystem : this.logicSystems) {
			entitySystem.initialize();
		}
		
		for (IEntitySystem entitySystem : this.renderSystems) {
			entitySystem.initialize();
		}
		
	}

	@Override
	public void logic() {
		for (IEntitySystem entitySystem : this.logicSystems) {
			entitySystem.process();
		}
	}

	@Override
	public void render() {
		for (IEntitySystem entitySystem : this.renderSystems) {
			entitySystem.process();
		}
	}
	
	private void sortSystem(List<IEntitySystem> entitySystem) {
		Collections.sort(entitySystem, new SystemComparer());
	}
	
	private class SystemComparer implements Comparator<IEntitySystem> {

		@Override
		public int compare(IEntitySystem entitySystem0, IEntitySystem entitySystem1) {
			return entitySystem0.getProcessingOrder() - entitySystem1.getProcessingOrder();
		}
	}
}
