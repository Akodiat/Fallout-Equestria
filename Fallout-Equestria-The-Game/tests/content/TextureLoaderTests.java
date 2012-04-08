package content;

import static org.junit.Assert.*;

import java.io.IOException;

import graphics.Texture2D;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.opengl.Display;

import content.TextureLoader;

public class TextureLoaderTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//This has to be done since we cannot use openGL functions without
		//having a valid GLContext.
		Display.create();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Display.destroy();
	}

	@Test
	public void testIfCanLoadValidTexture() throws IOException {
		TextureLoader loader = new TextureLoader();
		Texture2D texture = loader.loadContent(this.getClass().getResourceAsStream("testTexture.png"));
		
		assertNotNull(texture);
		//Basically make sure we have a correct texture. in this case a 1x1 texture.
		assertEquals(texture.Height, 1);
		assertEquals(texture.Width, 1);
	}
	
	@Test(expected = IOException.class)
	public void testIfThrownExceptionIfInvalidTexture() throws IOException {
		
		//A valid texture in this context is a .png file!
		TextureLoader loader = new TextureLoader();
		loader.loadContent(this.getClass().getResourceAsStream("invalidTestTexture.jpg"));
		
		fail("Should not get down here!");
	}
	
	@Test
	public void testIfFoulderIsCorrect() {
		TextureLoader loader = new TextureLoader();
		assertEquals(loader.getFoulder(), "textures");
	}
	
	@Test
	public void testIfClassMangagedIsCorrect() {
		TextureLoader loader = new TextureLoader();
		assertEquals(loader.getClassAbleToLoad(), Texture2D.class);
	}

}
