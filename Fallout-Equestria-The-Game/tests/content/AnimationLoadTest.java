package content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.openal.Audio;

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
	public void testIfCanLoadValidSound() throws Exception {
		AnimationLoader loader = new AnimationLoader();
		
		Animation animation = loader.loadContent(this.getClass().getResourceAsStream("test.anim"));
		
		assertNotNull(animation);
	}
	
	@Test(expected = IOException.class)
	public void testIfFailsToLoadInvalidSound() throws IOException {
		
		//This test will always fail. Slick AudioLoader is incapable of understanding,
		//if a soundfile is invalid. There is no easy fix for this. 
		//2 options. 1. Write our own. 2. Find another source then slick. 
		
		SoundLoader loader = new SoundLoader();
		//This should throw an Exception but it dosnt.
		loader.loadContent(this.getClass().getResourceAsStream("invalidSound.ogg"));
		
		fail("This is not supposed to happen!");
	}
	
	@Test
	public void testIfCorrectFoulder() {
		SoundLoader loader = new SoundLoader();
		
		String foulderName = loader.getFoulder();
		assertEquals("sounds", foulderName);
	}
	
	@Test
	public void testIfCorrectClassTypeManaged() {
		SoundLoader loader = new SoundLoader();
		assertEquals(Audio.class, loader.getClassAbleToLoad());
	}
}
