package entityFramework;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.BiMap;

public class EntityNetworkIDManager implements IEntityNetworkIDManager{
	private final BiMap<Integer, IEntity> entityMap;
	
	public EntityNetworkIDManager() {
		this.entityMap = HashBiMap.create();
	}
	
	
	@Override
	public void setNetworkIDToEntity(IEntity entity, int networkID) {
		this.entityMap.inverse().remove(entity);
		if(this.entityMap.containsKey(networkID)) {
			throw new IllegalArgumentException("The key + " + networkID + "is already present");
		}
		
		this.entityMap.put(networkID, entity);
	}

	@Override
	public IEntity getEntityFromNetworkID(int networkID) {
		return this.entityMap.get(networkID);
	}

	@Override
	public int getNetworkIDFromEntity(IEntity entity) {
		
		Integer networkID = this.entityMap.inverse().get(entity);
		return (networkID  != null) ? networkID.intValue() : -1; //TODO: Is this really a good thing to do?
	}

	@Override
	public void removeNetworkIDEntity(IEntity entity) {
		this.entityMap.inverse().remove(entity);
	}

}
