package clientNetworkSystems;

import screenCore.ScreenManager;

import common.Network;
import common.messages.GoToScreenMessage;
import entityFramework.EntityNetworkIDManager;
import entityFramework.IComponent;
import entityFramework.IEntityWorld;

public class ClientScreenChangeNetworkSystem extends ClientNetworkSystem<GoToScreenMessage>{
	private ScreenManager screenManager;

	//TODO Is any of these inputs really needed, I think not /Joakim
	public ClientScreenChangeNetworkSystem(IEntityWorld world,EntityNetworkIDManager idManager, ScreenManager screenManager, Network client, Class<? extends IComponent>[] components) {
		super(world, GoToScreenMessage.class, idManager, client, components);
		this.screenManager = screenManager;
	}

	@Override
	protected void processMessage(GoToScreenMessage message) {
		this.screenManager.removeAllScreens();
		this.screenManager.addScreen(message.newScreen);	
	}
	
}
