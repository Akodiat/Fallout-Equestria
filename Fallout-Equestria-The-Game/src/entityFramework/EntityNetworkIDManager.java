package entityFramework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EntityNetworkIDManager{
	private final Map<Integer, IEntity> entityMap;
	
	public EntityNetworkIDManager() {
		this.entityMap = new HashMap<>();
	}
	
	public void setNetworkIDToEntity(IEntity entity, int networkID) {
		if(this.entityMap.containsKey(networkID)) {
			throw new IllegalArgumentException("The key + " + networkID + "is already present");
		}
		this.entityMap.put(networkID, entity);
	}

	public IEntity getEntityFromNetworkID(int networkID) {
		return this.entityMap.get(networkID);
	}

	public void removeNetworkIDEntity(int entityID) {
		this.entityMap.remove(entityID);
	}

	public List<IEntity> getEntities() {
		return new ArrayList<>(entityMap.values());
	}
}	
