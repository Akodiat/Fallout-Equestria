package serverNetworkSystems;

import misc.IEventListener;
import misc.SoundEventArgs;
import misc.SoundManager;
import entityFramework.IEntityWorld;
import com.esotericsoftware.kryonet.*;
import common.SoundMessage;

public class ServerSoundNetworkSystem extends ServerNetworkSystem {

	private final SoundManager soundManager;
	
	public ServerSoundNetworkSystem(IEntityWorld world,
			Server server, SoundManager soundManager) {
		super(world, server);
		this.soundManager = soundManager;
	}

	@Override
	public void initialize() {
		this.soundManager.addSoundEventListener(new IEventListener<SoundEventArgs>() {
			@Override
			public void onEvent(Object sender, SoundEventArgs e) {
				ServerSoundNetworkSystem.this.sendSoundEvent(e);
			}
		});
	}
	
	protected void sendSoundEvent(SoundEventArgs e) {
		SoundMessage message = new SoundMessage();
		message.isMusic = e.isMusic();
		message.loop = e.isLoop();
		message.pitch = e.getPitch();
		message.sound = e.getSoundPath();
		this.Server.sendToAllUDP(message);
	}

	@Override
	public void process() {	}

}
