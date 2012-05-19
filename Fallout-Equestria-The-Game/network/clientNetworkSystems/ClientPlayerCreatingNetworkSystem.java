package clientNetworkSystems;

import scripting.PlayerScript;
import utils.Network;
import animation.PonyColorChangeHelper;

import com.esotericsoftware.kryonet.Client;

import common.NewPlayerMessage;
import common.PlayerCharacteristics;
import components.AnimationComp;
import components.BehaviourComp;
import components.ShadowComp;
import components.TransformationComp;
import content.ContentManager;
import entityFramework.EntityNetworkIDManager;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityWorld;
import entitySystems.CameraControlSystem;

public class ClientPlayerCreatingNetworkSystem extends ClientNetworkSystem<NewPlayerMessage> {

	private final PlayerCharacteristics playerCharacteristics;
	private final ContentManager contentManager;
	
	public ClientPlayerCreatingNetworkSystem(IEntityWorld world, EntityNetworkIDManager idManager, Network client,
											 ContentManager contentManager, PlayerCharacteristics pchars) {
		super(world, NewPlayerMessage.class, idManager, client);
		this.playerCharacteristics = pchars;
		this.contentManager = contentManager;
	}
	
	@Override
	public void initialize() {
		NewPlayerMessage message = new NewPlayerMessage();
		message.senderID = this.getClient().getID();
		message.playerCharacteristics = this.playerCharacteristics;
		this.getClient().sendTCP(message);
	}

	private void createPlayer(NewPlayerMessage message) {
		if(this.getEntityManager().getEntity("Player" + message.senderID) != null)
			return;
		
		
		IEntityArchetype archetype = this.contentManager.loadArchetype("Player.archetype");
		IEntity entity = this.getEntityManager().createEntity(archetype);
		entity.addComponent(new BehaviourComp(new PlayerScript()));
		entity.addComponent(new ShadowComp());
		entity.getComponent(TransformationComp.class).setPosition(1000,1000);	
		AnimationComp animComp = entity.getComponent(AnimationComp.class);
		animComp.changeAnimation("idle", true);
		
		PonyColorChangeHelper.setBodyColor(message.playerCharacteristics.bodyColor, animComp);
		PonyColorChangeHelper.setEyeColor( message.playerCharacteristics.eyeColor, animComp);
		PonyColorChangeHelper.setManeColor(message.playerCharacteristics.maneColor, animComp);
		
		entity.setLabel("Player" + message.senderID);
		if(message.senderID == this.getClient().getID())
			entity.addToGroup(CameraControlSystem.GROUP_NAME);
		
		entity.refresh();		
		
		if(this.IdManager.getEntityFromNetworkID(message.messageID) == null) {
			this.IdManager.setNetworkIDToEntity(entity, message.messageID);			
		}
		
	}
	
	@Override
	protected void processMessage(NewPlayerMessage message) {
		this.createPlayer(message);
	}
}
