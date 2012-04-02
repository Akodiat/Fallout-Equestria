package content.tests;

import static org.junit.Assert.*;


import graphics.TextureFont;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.opengl.Display;

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
		TextureFontLoader loader = new TextureFontLoader();
		TextureFont font = loader.loadContent(this.getClass().getResourceAsStream("testFont.font"));
		
		assertNotNull(font);	
	}
	
	@Test(expected = Exception.class)
	public void testIfExceptionIfInvalidFont() throws Exception {
		TextureFontLoader loader = new TextureFontLoader();
		loader.loadContent(this.getClass().getResourceAsStream("invalidTestFont.font"));
		
		fail("This should not get here!");	
	}
	
	@Test
	public void testIfCorrectFoulderAndClassManaged() {
		TextureFontLoader loader = new TextureFontLoader();
		assertEquals(loader.getFoulder(), "fonts");
		assertEquals(loader.getClassAbleToLoad(), TextureFont.class);
	}
}
