package content;

import static org.junit.Assert.*;


import graphics.TextureFont;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.opengl.Display;

import builders.ContentManagerBuilder;

import content.TextureFontLoader;

public class TextureFontLoaderTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Display.create();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Display.destroy();
	}
	
	@Test
	public void testIfCanLoadValidFont() throws Exception {
		TextureFontLoader loader = new TextureFontLoader(ContentManagerBuilder.buildStandardContentManager("resources"), "fonts");
		TextureFont font = loader.loadContent(this.getClass().getResourceAsStream("testFont.font"));
		
		assertNotNull(font);	
	}
	
	@Test(expected = Exception.class)
	public void testIfExceptionIfInvalidFont() throws Exception {
		TextureFontLoader loader = new TextureFontLoader(new ContentManager("resources"), "");
		loader.loadContent(this.getClass().getResourceAsStream("invalidTestFont.font"));
		
		fail("This should not get here!");	
	}
	
	@Test
	public void testIfCorrectFolderAndClassManaged() {
		TextureFontLoader loader = new TextureFontLoader(new ContentManager("resources"), "fonts");
		assertEquals(loader.getFolder(), "fonts");
		assertEquals(loader.getClassAbleToLoad(), TextureFont.class);
	}
}
