package entityFramework;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public class EntityGroup {
	private final String name;
	private final BitSet groupBits;
	private final Set<IEntity> entities;
	
	@SuppressWarnings("unchecked")
	public EntityGroup(String name, IEntityDatabase database, Class<? extends IComponent>... componentClasses) {
		groupBits = new BitSet();
		entities = new HashSet<>();
		this.name = name;
		
		for (Class<? extends IComponent> componentClass : componentClasses) {
			groupBits.or(database.getComponentTypeBit(componentClass));
		}
	}
	
	public boolean tryAddEntity(IEntity entity) {
		if(entity.getGroups().contains(this.name)) {
			BitSet changedSet = (BitSet)this.groupBits.clone();
			changedSet.and(entity.getComponentBits());
			
			
		}
		return false;
	}
	
	public void removeEntity(IEntity entity) {
		
	}
}
