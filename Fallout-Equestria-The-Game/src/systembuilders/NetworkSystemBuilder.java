package systembuilders;

import misc.PlayerCharacteristics;
import common.Network;

import serverNetworkSystems.*;
import sounds.SoundManager;

import clientNetworkSystems.ClientAnimationsNetworkSystem;
import clientNetworkSystems.ClientEntityCreationNetworkSystem;
import clientNetworkSystems.ClientEntityDestructionNetworkSystem;
import clientNetworkSystems.ClientHealthNetworkSystem;
import clientNetworkSystems.ClientMovementNetworkSystem;
import clientNetworkSystems.ClientPlayerCreatingNetworkSystem;
import clientNetworkSystems.ClientSoundNetworkSystem;

import content.ContentManager;

import entityFramework.EntityNetworkIDManager;
import entityFramework.IEntitySystemManager;
import entityFramework.IEntityWorld;

public class NetworkSystemBuilder {
	
	public static void createClientSystems(IEntityWorld world, Network client, EntityNetworkIDManager idmanager, 
										   SoundManager soundManager, ContentManager contentManager, 
										   PlayerCharacteristics playerch) {
		
		IEntitySystemManager sysManager = world.getSystemManager();
		sysManager.addLogicEntitySystem(new ClientMovementNetworkSystem(world, idmanager, client));		
		sysManager.addLogicEntitySystem(new ClientAnimationsNetworkSystem(world, idmanager, client));
		sysManager.addLogicEntitySystem(new ClientHealthNetworkSystem(world, idmanager, client));
		sysManager.addLogicEntitySystem(new ClientPlayerCreatingNetworkSystem(world, idmanager, client, contentManager, playerch));
		sysManager.addLogicEntitySystem(new ClientEntityDestructionNetworkSystem(world, idmanager, client));
		sysManager.addLogicEntitySystem(new ClientEntityCreationNetworkSystem(world, idmanager,contentManager, client));
		sysManager.addLogicEntitySystem(new ClientSoundNetworkSystem(world, idmanager, client, soundManager));
	}
	
	public static void createServerSystems(IEntityWorld world, Network server, SoundManager soundManager, ContentManager contentManager, PlayerCharacteristics playerch) {
		
		IEntitySystemManager sysManager = world.getSystemManager();
		sysManager.addLogicEntitySystem(new ServerMovementNetworkSystem(world, server));
		sysManager.addLogicEntitySystem(new ServerEntityCreationNetwork(world,server));
		sysManager.addLogicEntitySystem(new ServerHealthNetworkSystem(world,server));
		sysManager.addLogicEntitySystem(new ServerPlayerCreationNetworkSystem(world,server, contentManager, playerch));
		sysManager.addLogicEntitySystem(new ServerAnimationNetworkSystem(world,server));
		sysManager.addLogicEntitySystem(new ServerSoundNetworkSystem(world, server, soundManager));
		sysManager.addLogicEntitySystem(new ServerInputNetworkSystem(world, server));
	}
	
}
