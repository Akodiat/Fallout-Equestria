package entitySystems;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import components.SoundEffectComponent;
import entityFramework.*;

public class SoundEffectSystem extends EntitySingleProcessingSystem{
	
	private Map<String, Audio> audioMap = new HashMap<String, Audio>();
	
	protected SoundEffectSystem(IEntityWorld world) {
		super(world, SoundEffectComponent.class);
	}

	private ComponentMapper<SoundEffectComponent> SCM;

	@Override
	public void initialize() {
		SCM = ComponentMapper.create(this.getWorld().getDatabase(), SoundEffectComponent.class);
		
		File soundFolder = new File("resources/sound/effects");
		File[] sounds = soundFolder.listFiles();
		
		for(int i = 0; i < sounds.length; i++) {
			try {
				audioMap.put(sounds[i].getPath(), AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream(sounds[i].getPath())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void processEntity(IEntity entity) {
		SoundEffectComponent se = SCM.getComponent(entity);
		
		List<String> sounds = se.getSoundsToPlay();
		for (int i = 0; i < sounds.size(); i++) {
			Audio audio = this.audioMap.get(sounds.get(i));
			audio.playAsSoundEffect(1, 1, false);
		}
		
		sounds.clear();
	}
	
}
