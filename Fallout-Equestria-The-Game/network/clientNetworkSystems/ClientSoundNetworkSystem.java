package clientNetworkSystems;

import misc.SoundManager;

import com.esotericsoftware.kryonet.Client;

import common.SoundMessage;
import entityFramework.EntityNetworkIDManager;
import entityFramework.IEntityWorld;

public class ClientSoundNetworkSystem extends ClientNetworkSystem<SoundMessage>{

	private final SoundManager soundManager;
	
	public ClientSoundNetworkSystem(IEntityWorld world,EntityNetworkIDManager idManager,
			Client client, SoundManager soundManager) {
		super(world, SoundMessage.class, idManager, client);
		this.soundManager = soundManager;
	}

	@Override
	protected void processMessage(SoundMessage message) {
		if(message.isMusic) {
			this.soundManager.playMusic(message.sound, message.pitch, message.loop);
		} else {
			this.soundManager.playSoundEffect(message.sound, message.pitch, message.loop);
		}
	}

}
