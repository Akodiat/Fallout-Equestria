package uinttests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import entityFramework.EntityGroupManager;
import entityFramework.IEntity;
import entityFramework.IEntityGroupManager;



public class IEntityGroupManagerTests {

	private IEntityGroupManager getManager() {
		return new EntityGroupManager();
	}
	
	@Test
	public void testGetGroup() {
		IEntityGroupManager manager = getManager();
		IEntity entity = mock(IEntity.class);
		
		manager.addEntityToGroup(entity, "Hej");
		
		ImmutableList<IEntity> entities = manager.getGroup("Hej").asList();
		assertSame(entity, entities.get(0));
	}
	
	@Test
	public void testGetGroupsOfEntity() {
		IEntityGroupManager manager = getManager();
		IEntity entity = mock(IEntity.class);
		
		manager.addEntityToGroup(entity, "Hej");
		manager.addEntityToGroup(entity, "Då");

		
		ImmutableList<String> groups = manager.getGroupsOfEntity(entity).asList();
		assertSame("Hej", groups.get(0));
		assertSame("Då", groups.get(1));
	}
	
	@Test
	public void testAddEntityToGroup() {
		IEntityGroupManager manager = getManager();
		IEntity entity = mock(IEntity.class);
			
		ImmutableSet<IEntity> entities1 = manager.getGroup("Hej");
		
		manager.addEntityToGroup(entity, "Hej");
		
		ImmutableSet<IEntity> entities2 = manager.getGroup("Hej");
		assertFalse(entities1.equals(entities2));
		
	}
	
	@Test 
	public void testRemoveEntityFromGroup() {
		IEntityGroupManager manager = getManager();
		IEntity entity = mock(IEntity.class);
			
		manager.addEntityToGroup(entity, "Hej");
		ImmutableSet<IEntity> entities1 = manager.getGroup("Hej");
		manager.removeEntityFromGroup(entity, "Hej");
		ImmutableSet<IEntity> entities2 = manager.getGroup("Hej");
		
		assertFalse(entities1.equals(entities2));
	}
	
	public void testRemoveEntityFromAllGroups() {
		IEntityGroupManager manager = getManager();
		IEntity entity = mock(IEntity.class);
			
		manager.addEntityToGroup(entity, "Hej");
		manager.addEntityToGroup(entity, "Då");
		
		ImmutableSet<IEntity> entities1 = manager.getGroup("Hej");
		ImmutableSet<IEntity> entities2 = manager.getGroup("Då");
		
		assertTrue(entities1.size() == 0);
		assertTrue(entities2.size() == 1);
		
	}
	
}
