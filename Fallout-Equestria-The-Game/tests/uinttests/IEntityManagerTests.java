package uinttests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import entityFramework.EntityChangedListener;
import entityFramework.EntityManager;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityDatabase;
import entityFramework.IEntityFactory;
import entityFramework.IEntityGroupManager;
import entityFramework.IEntityLabelManager;
import entityFramework.IEntityManager;


public class IEntityManagerTests {
	
	private IEntityManager getManager(IEntityDatabase database, IEntityFactory factory) {
		return new EntityManager(null, null, factory, database);
	}

	private IEntityManager getManager(IEntityLabelManager lm, IEntityGroupManager gm, 
									  IEntityDatabase database, IEntityFactory factory) {
		return new EntityManager(lm, gm, factory, database);
	}
	
	@Test
	public void testCreateEntity() {
		IEntityFactory factory = mock(IEntityFactory.class);
		IEntityDatabase database = mock(IEntityDatabase.class);
		IEntityManager manager = this.getManager(database, factory);
		
		when(factory.createEmptyEntity(manager, database)).thenReturn(mock(IEntity.class));
		IEntity entity = manager.createEmptyEntity();
		
		assertNotNull(entity);
		verify(factory).createEmptyEntity(manager, database);
		
		when(factory.createEntity(null, manager, database)).thenReturn(null);
		IEntityArchetype archeType = mock(IEntityArchetype.class);
		entity = manager.createEntity(archeType);

		assertNull(entity);
		verify(factory).createEntity(archeType, manager, database);
		
	}
	
	@Test
	public void testGetEntity() {
		IEntityFactory factory = mock(IEntityFactory.class);
		IEntityDatabase database = mock(IEntityDatabase.class);
		IEntityManager manager = this.getManager(database, factory);
		IEntity entity = mock(IEntity.class);
		when(entity.getUniqueID()).thenReturn(0);
		
		
		when(factory.createEmptyEntity(manager, database)).thenReturn(entity);
		entity = manager.createEmptyEntity();
		
		when(database.getEntity(0)).thenReturn(entity);
		
		assertEquals(entity, manager.getEntity(entity.getUniqueID()));
		
	}
	
	@Test
	public void testLabelEntity() {

		IEntityFactory factory = mock(IEntityFactory.class);
		IEntityDatabase database = mock(IEntityDatabase.class);
		IEntityLabelManager labelManager = mock(IEntityLabelManager.class);
		IEntityManager manager = this.getManager(labelManager, null, database, factory);
		when(factory.createEmptyEntity(manager, database)).thenReturn(mock(IEntity.class));
		
		
		IEntity entity = manager.createEmptyEntity();
		
		manager.labelEntity(entity, "Trololo");
		verify(labelManager).setLabelToEntity(entity, "Trololo");
		
		when(labelManager.getEntityFromLabel("Trololo")).thenReturn(entity);
		assertEquals(entity, manager.getEntity("Trololo"));
		
	}
	
	@Test
	public void testRefreshEntity() {

		IEntityFactory factory = mock(IEntityFactory.class);
		IEntityDatabase database = mock(IEntityDatabase.class);
		IEntityManager manager = this.getManager(database, factory);
		
		when(factory.createEmptyEntity(manager, database)).thenReturn(mock(IEntity.class));
		IEntity entity = manager.createEmptyEntity();
			
		EntityChangedListener listener = mock(EntityChangedListener.class);
		manager.addEntityChangedListener(listener);
		
		manager.refreshEntity(entity);
		
		verify(listener).entityChanged(entity);
		
	}
}