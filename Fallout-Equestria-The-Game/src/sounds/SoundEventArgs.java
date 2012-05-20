package sounds;

import utils.EventArgs;

public class SoundEventArgs extends EventArgs {
	private final boolean isMusic;
	private final String soundPath;
	
	private final float pitch;
	private final boolean loop;
	
	public SoundEventArgs(boolean isMusic, String soundPath, float pitch, boolean loop) {
		super();
		this.isMusic = isMusic;
		this.soundPath = soundPath;
		this.pitch = pitch;
		this.loop = loop;
	}
	
	public boolean isMusic() {
		return isMusic;
	}
	
	public String getSoundPath() {
		return soundPath;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public boolean isLoop() {
		return loop;
	}
}
