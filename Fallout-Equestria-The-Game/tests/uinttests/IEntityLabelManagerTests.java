package uinttests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import entityFramework.EntityLabelManager;
import entityFramework.IEntity;
import entityFramework.IEntityLabelManager;


public class IEntityLabelManagerTests {

	private IEntityLabelManager getManager() {
		return new EntityLabelManager();
	}
	
	@Test
	public void testLabelToEntity() {
		IEntityLabelManager manager = getManager();
		IEntity entity = mock(IEntity.class);
		
		manager.setLabelToEntity(entity, "Hej");
	}
	
	
	@Test
	public void testgetLabelFromEntity() {
		IEntityLabelManager manager = getManager();
		IEntity entity = mock(IEntity.class);
		
		manager.setLabelToEntity(entity, "Hej");
		
		String actual = manager.getLabelFromEntity(entity);
		
		assertEquals("Hej", actual);
	}
	
	@Test
	public void testgetEntityFromLabel() {
		IEntityLabelManager manager = getManager();
		IEntity entity = mock(IEntity.class);
		
		manager.setLabelToEntity(entity, "Hej");
		
		IEntity expected = manager.getEntityFromLabel("Hej");
		
		assertEquals(entity, expected);	
	}
	
	@Test
	public void removeLabelEntity() {
		IEntityLabelManager manager = getManager();
		IEntity entity = mock(IEntity.class);
		
		manager.setLabelToEntity(entity, "Hej");
		
		manager.removeLabelEntity(entity);
		IEntity entity2 = manager.getEntityFromLabel("Hej");
		assertNull(entity2);
		assertTrue(manager.getLabelFromEntity(entity) == "");
		
	}
	
	
	


}
