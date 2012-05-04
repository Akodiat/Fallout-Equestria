package content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.opengl.Display;

import animation.Animation;

public class AnimationLoadTest {
	
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
	public void testIfCanLoadValidAnimation() throws Exception {
		AnimationLoader loader = new AnimationLoader(new ContentManager(""), "animations");
		
		Animation animation = loader.loadContent(this.getClass().getResourceAsStream("test.anim"));
		
		assertNotNull(animation);
	}
	
	@Test(expected = Exception.class)
	public void testIfFailsToLoadInvalidAnimation() throws Exception {
		
		//This test will always fail. Slick AudioLoader is incapable of understanding,
		//if a soundfile is invalid. There is no easy fix for this. 
		//2 options. 1. Write our own. 2. Find another source then slick. 
		AnimationLoader loader =  new AnimationLoader(new ContentManager(""), "animations");
		//This should throw an Exception but it dosnt.
		loader.loadContent(this.getClass().getResourceAsStream("invalidSound.ogg"));
		
		fail("This is not supposed to happen!");
	}
	
	@Test
	public void testIfCorrectFolder() {
		AnimationLoader loader = new AnimationLoader(new ContentManager(""), "animations");
		
		String folderName = loader.getFolder();
		assertEquals("sounds", folderName);
	}
	
	@Test
	public void testIfCorrectClassTypeManaged() {
		AnimationLoader loader = new AnimationLoader(new ContentManager(""), "animations");
		assertEquals(Animation.class, loader.getClassAbleToLoad());
	}
}
