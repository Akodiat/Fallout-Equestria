package sounds;
import misc.SoundEventArgs;

import org.newdawn.slick.openal.Audio;

import utils.Event;
import utils.IEventListener;

import content.ContentManager;

public class SoundManager {
	private final ContentManager contentManager;
	private Audio activeMusic;
	
	private float masterVolume;
	private float musicVolume;
	private float soundEffectVolume;
	
	private Event<SoundEventArgs> soundEvent;
	
	public SoundManager(ContentManager contentManager, float masterVolume, float musicVolume, float soundEffectVolume) {
		this.contentManager = contentManager;
		this.activeMusic = null;
		this.setMasterVolume(masterVolume);
		this.setMusicVolume(musicVolume);
		this.setSoundEffectVolume(soundEffectVolume);
		
		this.soundEvent = new Event<>();
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
		this.playMusic(sound, 1.0f, true);
	
	}
	
	public void playMusic(String sound, float pitch, boolean loop) {
		this.createSoundEvent(sound, pitch, loop, true);	
		if(this.activeMusic != null) {
			this.activeMusic.stop();
		}
		
		Audio music = this.contentManager.loadSound(sound);
		this.activeMusic = music;
		
		startMusic(pitch,loop);
	}

	private void startMusic(float pitch, boolean loop) {
		float effectiveVolume = this.masterVolume * this.musicVolume;
		this.activeMusic.playAsMusic(pitch, effectiveVolume, loop);
	}

	public void playSoundEffect(String sound) {
		this.playSoundEffect(sound, 1.0f, false);
	}
	
	public void playSoundEffect(String sound, float pitch, boolean loop) {
		Audio soundEffect = this.contentManager.loadSound(sound);
		startSoundEffect(soundEffect, pitch, loop);
		
		this.createSoundEvent(sound, pitch, loop, false);
	}

	private void startSoundEffect(Audio soundEffect, float pitch, boolean loop) {
		float effectiveVolume = this.masterVolume * this.soundEffectVolume;
		soundEffect.playAsSoundEffect(pitch, effectiveVolume, false);
	}

	private void createSoundEvent(String sound, float pitch, boolean loop, boolean isMusic) {
		SoundEventArgs args = new SoundEventArgs(isMusic, sound, pitch, loop);
		this.soundEvent.invoke(this, args);		
	}
	
	public void addSoundEventListener(IEventListener<SoundEventArgs> listener) {
		this.soundEvent.addListener(listener);
	}
	public void removeSoundEventListener(IEventListener<SoundEventArgs> listener) {
		this.soundEvent.removeListener(listener);
	}
}
