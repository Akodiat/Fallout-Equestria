package common;

import org.newdawn.slick.openal.Audio;

import content.ContentManager;
import entityFramework.EntityNetworkIDManager;
import entityFramework.IComponent;
import entityFramework.IEntityWorld;

public class AudioNetworkSystem extends NetworkSystem<AudioPlayedMessage>{

	public AudioNetworkSystem(IEntityWorld world, EntityNetworkIDManager idManager, ContentManager contentManager, Class<? extends IComponent>[] components) {
		super(AudioPlayedMessage.class, world, idManager, contentManager, components);
	}

	@Override
	public void process(AudioPlayedMessage message) {
		Audio audio = contentManager.loadSound(message.audioPath);
		if(message.isMusic)
			audio.playAsMusic(message.pitch, message.gain, message.loop);
		else
			audio.playAsSoundEffect(message.pitch, message.gain, message.loop);
	}

}
