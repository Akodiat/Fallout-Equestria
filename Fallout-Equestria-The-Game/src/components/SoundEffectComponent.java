package components;

import java.util.List;
import java.util.Map;

import utils.SoundEvent;

import entityFramework.IComponent;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SoundEffecs")
public class SoundEffectComponent implements IComponent{
	private List<String> soundsToPlay;
	
	Map<SoundEvent, String> sounds;
	
	public Object clone() {
		return null;
	}
	
	public void setToPlayEffect(SoundEvent soundEvent) {
		if(sounds.get(soundEvent) != null) {
			String effect = sounds.get(soundEvent);
			soundsToPlay.add(effect);
		}
	}
	
	public List<String> getSoundsToPlay() {
		return this.soundsToPlay;
	}
	
	public void mapSoundToEvent(SoundEvent soundEvent, String path) {
		sounds.put(soundEvent, path);
	}
}
