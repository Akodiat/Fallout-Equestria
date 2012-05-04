package content;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

public class SoundLoader extends ContentLoader<Audio>{

	public SoundLoader(String folder) {
		super(folder);
	}

	public Audio loadSound(FileInputStream fileInputStream) throws IOException  {
		Audio a = AudioLoader.getAudio("OGG", fileInputStream);
		return a;
	}

	@Override
	public Class<Audio> getClassAbleToLoad() {
		return Audio.class;
	}

	@Override
	public Audio loadContent(InputStream in) throws IOException {
		Audio a = AudioLoader.getAudio("OGG", in);
		return a;
	}
}
