package content;

import static org.junit.Assert.*;
import entityFramework.IEntityArchetype;

import org.junit.Test;

import builders.ContentManagerBuilder;


public class EntityArchetypeLoaderTests {

	@Test
	public void testIfCanLoadValidArchetype() throws Exception {
		EntityArchetypeLoader loader = new EntityArchetypeLoader(ContentManagerBuilder.buildStandardContentManager("resources"), "archetypes");
		IEntityArchetype archetype = loader.loadContent(this.getClass().getResourceAsStream("Bullet.archetype"));
		
		assertNotNull(archetype);	
	}
	
	@Test(expected = Exception.class)
	public void testIfExceptionIfInvalidFont() throws Exception {
		EntityArchetypeLoader loader = new EntityArchetypeLoader(new ContentManager(""), "");
		loader.loadContent(this.getClass().getResourceAsStream("invalidTestArchetype.archetype"));
		
		fail("This should not get here!");	
	}
	
	@Test
	public void testIfCorrectFolderAndClassManaged() {
		EntityArchetypeLoader loader = new EntityArchetypeLoader(new ContentManager(""), "archetypes");
		assertEquals(loader.getFolder(), "archetypes");
		assertEquals(loader.getClassAbleToLoad(), IEntityArchetype.class);
	}
}
