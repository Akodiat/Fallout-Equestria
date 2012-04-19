package content;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.newdawn.slick.openal.Audio;

import content.SoundLoader;

public class SoundLoaderTests {

	@Test
	public void testIfCanLoadValidSound() throws IOException {
		SoundLoader loader = new SoundLoader();
		
		Audio audio = loader.loadContent(this.getClass().getResourceAsStream("testSound.ogg"));
		
		assertNotNull(audio);
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
	public void testIfCorrectFolder() {
		SoundLoader loader = new SoundLoader();
		
		String folderName = loader.getFolder();
		assertEquals("sounds", folderName);
	}
	
	@Test
	public void testIfCorrectClassTypeManaged() {
		SoundLoader loader = new SoundLoader();
		assertEquals(Audio.class, loader.getClassAbleToLoad());
	}

}
