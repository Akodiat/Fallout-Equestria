package misc;
import org.newdawn.slick.openal.Audio;

import content.ContentManager;

public class SoundManager {
	private final ContentManager contentManager;
	private Audio activeMusic;
	
	private float masterVolume;
	private float musicVolume;
	private float soundEffectVolume;
	
	public SoundManager(ContentManager contentManager, float masterVolume, float musicVolume, float soundEffectVolume) {
		this.contentManager = contentManager;
		this.activeMusic = null;
		this.setMasterVolume(masterVolume);
		this.setMusicVolume(musicVolume);
		this.setSoundEffectVolume(soundEffectVolume);
	}

	public float getMasterVolume() {
		return masterVolume;
	}

	public void setMasterVolume(float masterVolume) {
		this.masterVolume = masterVolume;
	}

	public float getMusicVolume() {
		return musicVolume;
	}

	public void setMusicVolume(float musicVolume) {
		this.musicVolume = musicVolume;
	}

	public float getSoundEffectVolume() {
		return soundEffectVolume;
	}

	public void setSoundEffectVolume(float soundEffectVolume) {
		this.soundEffectVolume = soundEffectVolume;
	}
	
	public void playMusic(String sound) {
		if(this.activeMusic != null) {
			this.activeMusic.stop();
		} 
		Audio music = this.contentManager.loadSound(sound);
		this.activeMusic = music;
		
		startMusic();
	}
	
	private void startMusic() {
		float effectiveVolume = this.masterVolume * this.musicVolume;
		this.activeMusic.playAsMusic(1.0f, effectiveVolume, false);
	}

	public void playSoundEffect(String sound) {
		Audio soundEffect = this.contentManager.loadSound(sound);
		startSoundEffect(soundEffect);
	}

	private void startSoundEffect(Audio soundEffect) {
		float effectiveVolume = this.masterVolume * this.soundEffectVolume;
		soundEffect.playAsSoundEffect(1.0f, effectiveVolume, false);
	}
}

