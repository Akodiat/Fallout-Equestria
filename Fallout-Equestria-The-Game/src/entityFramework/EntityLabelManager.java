package entityFramework;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.BiMap;

public class EntityLabelManager implements IEntityLabelManager{
	private final BiMap<String, IEntity> entityMap;
	
	public EntityLabelManager() {
		this.entityMap = HashBiMap.create();
	}
	
	
	@Override
	public void setLabelToEntity(IEntity entity, String label) {
		this.entityMap.inverse().remove(entity);
		if(this.entityMap.containsKey(label)) {
			throw new IllegalArgumentException("The key + " + label + "is already present");
		}
		
		this.entityMap.put(label, entity);
	}

	@Override
	public IEntity getEntityFromLabel(String entityLabel) {
		return this.entityMap.get(entityLabel);
	}

	@Override
	public String getLabelFromEntity(IEntity entity) {
		
		String str = this.entityMap.inverse().get(entity);
		return (str != null) ? str : "";
	}

	@Override
	public void removeLabelEntity(IEntity entity) {
		this.entityMap.inverse().remove(entity);
	}

}
