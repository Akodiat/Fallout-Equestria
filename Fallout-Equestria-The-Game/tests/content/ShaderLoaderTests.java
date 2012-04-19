package content;

import static org.junit.Assert.*;
import graphics.ShaderEffect;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.opengl.Display;

import content.ShaderLoader;

public class ShaderLoaderTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Display.create();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Display.destroy();
	}

	@Test
	public void testIfCanLoadValidShaderEffect() throws Exception {
		ShaderLoader loader = new ShaderLoader();
		ShaderEffect effect = loader.loadContent(this.getClass().getResourceAsStream("testEffect.effect"));		
		assertNotNull(effect);
	}
	
	@Test(expected = RuntimeException.class)
	public void testIfExceptionIfInvalidEffect() throws Exception {
		ShaderLoader loader = new ShaderLoader();
		loader.loadContent(this.getClass().getResourceAsStream("invalidTestEffect.effect"));		
		
		fail("It should not get here!");
	}
	
	@Test
	public void testIfCorrectFolderAndClassManaged() {
		ShaderLoader loader = new ShaderLoader();
		assertEquals(loader.getFolder(), "shaders");
		assertEquals(loader.getClassAbleToLoad(), ShaderEffect.class);
	}

}
