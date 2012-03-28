package content;

import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

public class SoundLoader {

	public Audio loadSound(FileInputStream fileInputStream) throws IOException {
		Audio a = AudioLoader.getAudio("OGG", fileInputStream);
		return a;
	}

}
