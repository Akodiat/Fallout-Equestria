package serverNetworkSystems;

import misc.AnimationChangeEventArgs;
import misc.IEventListener;
import entityFramework.IEntity;
import entityFramework.IEntityWorld;
import com.esotericsoftware.kryonet.Server;

import common.AnimationChangedMessage;
import components.AnimationComp;

public class ServerAnimationNetworkSystem extends ServerNetworkSystem {

	
	public ServerAnimationNetworkSystem(IEntityWorld world, Server server) {
		super(world, server, AnimationComp.class);
	}
	
	protected void sendAnimationChanged(IEntity entity, AnimationChangeEventArgs e) {
		AnimationChangedMessage message = new AnimationChangedMessage();
		message.messageID = entity.getUniqueID();
		message.newAnimation = e.newAnimation;
		this.Server.sendToAllTCP(message);
	}

	@Override
	protected void entityAdded(final IEntity entity) {
		AnimationComp comp = entity.getComponent(AnimationComp.class);
		comp.getAnimationPlayer().addAnimationChangeListener(new IEventListener<AnimationChangeEventArgs>() {
			public void onEvent(Object sender, AnimationChangeEventArgs e) {
				ServerAnimationNetworkSystem.this.sendAnimationChanged(entity, e);
			}
		});
	}

	@Override
	public void process() { }

}