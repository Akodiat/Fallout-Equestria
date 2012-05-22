package uinttests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.google.common.collect.ImmutableList;

import entityFramework.*;




public class IEntityDatabaseTests {
	
	private IEntityDatabase getDatabase() {
		return new EntityDatabase(new ComponentTypeManager());
	}
	
	@Test 
	public void testGetComponents() {
		IEntityDatabase database = this.getDatabase();
		
		IEntity entity = mock(IEntity.class);
		when(entity.getUniqueID()).thenReturn(0);
		
		database.addEntity(entity);
		database.setComponent(entity.getUniqueID(), new ComponentA());
		database.setComponent(entity.getUniqueID(), new ComponentB());
		database.setComponent(entity.getUniqueID(), new ComponentC());
		
		ImmutableList<IComponent> entityComponents = database.getComponents(0).asList();
		assertTrue(entityComponents.size() == 3);
		assertTrue(ComponentA.class.equals(entityComponents.get(0).getClass()));
		assertTrue(ComponentB.class.equals(entityComponents.get(1).getClass()));
		assertTrue(ComponentC.class.equals(entityComponents.get(2).getClass()));
	} 
	
	@Test
	public void testGetEntitysContainingComponent() {
		
		IEntityDatabase database = this.getDatabase();
		List<List<Boolean>> componentLists = new ArrayList<List<Boolean>>();
		componentLists.add(new ArrayList<Boolean>());
		componentLists.add(new ArrayList<Boolean>());
		componentLists.add(new ArrayList<Boolean>());
		
		for (int i = 0; i < 5; i++) {
			IEntity entity = mock(IEntity.class);
			when(entity.getUniqueID()).thenReturn(i);
			database.addEntity(entity);
			if(Math.random() >  0.5) {
				IComponent component = new ComponentA();
				int id = entity.getUniqueID();
				database.setComponent(id,component);
				componentLists.get(0).add(true);
			}
			if(Math.random() >  0.5) {
				IComponent component = new ComponentB();
				database.setComponent(entity.getUniqueID(),component);
				componentLists.get(1).add(true);
			}
			if(Math.random() >  0.5) {
				IComponent component = new ComponentC();
				database.setComponent(entity.getUniqueID(),component);
				componentLists.get(2).add(true);
			}
		}
		
		assertEquals(componentLists.get(0).size(),
				     database.getEntitysContainingComponent(ComponentA.class).size());
		assertEquals(componentLists.get(1).size(),
					 database.getEntitysContainingComponent(ComponentB.class).size());
		assertEquals(componentLists.get(2).size(),
				     database.getEntitysContainingComponent(ComponentC.class).size());
	}
	
	@Test
	public void testSlowSettingComponent() {
		IEntityDatabase database = this.getDatabase();
		int randNum = (int)(Math.random() * 1000);
		
		IEntity entity = mock(IEntity.class);
		when(entity.getUniqueID()).thenReturn(randNum);
		
		database.addEntity(entity);
		
		IComponent component = mock(IComponent.class);
		
		database.setComponent(entity.getUniqueID(),component);
		IComponent actual = database.getComponent(entity.getUniqueID(), component.getClass());
		assertEquals(component, actual);
	}
	
	@Test
	public void testFastSettingComponent() {
		IEntityDatabase database = this.getDatabase();
		int randNum = (int)(Math.random() * 1000);
		
		IEntity entity = mock(IEntity.class);
		when(entity.getUniqueID()).thenReturn(randNum);
		
		database.addEntity(entity);
		
		IComponent component = mock(IComponent.class);
		int componentTypeID = database.getComponentTypeID(component.getClass());
		
		database.setComponent(entity.getUniqueID(),componentTypeID, component);
		IComponent actual = database.getComponent(entity.getUniqueID(),componentTypeID);
		
		assertEquals(component, actual);	
	}
	
	@Test
	public void testRemovingComponent() {
		IEntityDatabase database = this.getDatabase();
		int randNum = (int)(Math.random() * 1000);
		
		IEntity entity = mock(IEntity.class);
		when(entity.getUniqueID()).thenReturn(randNum);
		
		database.addEntity(entity);
		
		IComponent component = mock(IComponent.class);
		database.setComponent(entity.getUniqueID(), component);
		
		database.deleteComponent(entity.getUniqueID(), component.getClass());
		
		assertNull(database.getComponent(entity.getUniqueID(), component.getClass()));
	}
	
	@Test 
	public void testGettingComponent() {
		IEntityDatabase database = this.getDatabase();
		int randNum = (int)(Math.random() * 1000);
		
		IEntity entity = mock(IEntity.class);
		when(entity.getUniqueID()).thenReturn(randNum);
		
		IComponent component = mock(IComponent.class);
		
		database.addEntity(entity);
		assertNull(database.getComponent(entity.getUniqueID(), component.getClass()));
		
		database.setComponent(entity.getUniqueID(), component);
		
		assertEquals(component, database.getComponent(entity.getUniqueID(), component.getClass()));
		
	}
	
	@Test
	public void testAddingAndRemovingEntities() {
		IEntityDatabase database = this.getDatabase();
		int randNum = (int)(Math.random() * 1000);
		
		IEntity entity = mock(IEntity.class);
		when(entity.getUniqueID()).thenReturn(randNum);
		
		database.addEntity(entity);
		
		assertTrue(database.getEntityCount() == 1);
		assertEquals(entity, database.getEntity(randNum));
		
	}
	
	@Test
	public void testRemoveEntity() {
		IEntityDatabase database = this.getDatabase();
		int randNum = (int)(Math.random() * 1000);
		
		IEntity entity = mock(IEntity.class);
		when(entity.getUniqueID()).thenReturn(randNum);
		
		database.addEntity(entity);
		database.removeEntity(entity);
		
		assertTrue(database.getEntityCount() == 0);
		assertNotSame(entity,database.getEntity(randNum));
		
		database.addEntity(entity);
		database.removeEntity(randNum);
		
		assertTrue(database.getEntityCount() == 0);
		assertNotSame(entity,database.getEntity(randNum));
	}

	@Test
	public void testGetEntity() {
		IEntityDatabase database = this.getDatabase();
		int randNum = (int)(Math.random() * 1000);
		assertNull(database.getEntity(randNum));
		
		IEntity entity = mock(IEntity.class);
		when(entity.getUniqueID()).thenReturn(randNum);
		
		database.addEntity(entity);
		assertSame(entity, database.getEntity(randNum));
	}
	
	
	private class ComponentA implements IComponent { public Object clone() { return null;} }
	private class ComponentB implements IComponent { public Object clone() { return null;} }
	private class ComponentC implements IComponent { public Object clone() { return null;} }
}
