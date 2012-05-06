package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.Clock;
import utils.GameTime;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import common.*;
import components.*;
import components.TransformationComp;
import content.ContentManager;
import demos.WorldBuilder;
import entityFramework.*;
import gameMap.Scene;


/**
 * 
 * @author Joakim Johansson
 *
 */
public class KyroServer {
	private Server server;
	private List<NewPlayerMessage> addedPlayerMessages; //Used to notify new players of players added earlier.

	private IEntityWorld world;
	private Clock clock;
	
	private ContentManager contentManager;
	private Scene scene;

	public KyroServer() throws IOException{
		this.server = new Server();
		this.server.start();
		this.server.bind(54555, 54777);

		Network.registerClasses(this.server);

		this.server.addListener(this.generateNewListener());

		this.clock = new Clock();
	}

	public static void main(String[] args) throws IOException{
		KyroServer kryoServer = new KyroServer();
	}
	public void start(){
		while(true){
			this.gameLoop();
		}
	}
	private void gameLoop() {
		this.clock.update();
		GameTime time = this.clock.getGameTime();
		this.update(time);
	}
	
	protected void initialize() {
		contentManager = new ContentManager("resources");
		scene = contentManager.load("MaseScenev0.xml", Scene.class);
		clock = new Clock();
		
		addedPlayerMessages = new ArrayList<NewPlayerMessage>();
		
		world = WorldBuilder.buildServerWorld(scene, contentManager);
		world.initialize();
	}

	public void update(GameTime time) {
		
		this.world.update(time);
		this.world.getEntityManager().destoryKilledEntities();
		
		for (IEntity entity: this.world.getDatabase().getEntitysContainingComponents(TransformationComp.class, PhysicsComp.class)) {
			//if(!entity.getComponent(PhysicsComp.class).getVelocity().equals(Vector2.Zero)){
				EntityMovedMessage message = new EntityMovedMessage();
				
				message.entityID = entity.getUniqueID();
				message.newPhysComp = entity.getComponent(PhysicsComp.class);
				message.newTransfComp = entity.getComponent(TransformationComp.class);
				
				server.sendToAllUDP(message);
			//}
				
		}
	}

	private Listener generateNewListener(){
		return new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof InputComp) {
					InputComp message = (InputComp) object;
					IEntity player = world.getEntityManager().getEntity(Utils.getPlayerLabel(connection.getID()));
					player.getComponent(InputComp.class).setAllToBeLike(message);
				}
				else if (object instanceof NewPlayerMessage){
					for (NewPlayerMessage message : addedPlayerMessages) { //Sends messages from all other players to the newly connected player.
						System.out.println(message);
						connection.sendTCP(message);
					}
					
					NewPlayerMessage message = (NewPlayerMessage) object;
					addedPlayerMessages.add(message);
					IEntity player = world.getEntityManager().createEntity(
							contentManager.loadArchetype(
									message.playerCharacteristics.archetypePath));
					player.setLabel(Utils.getPlayerLabel(connection.getID()));
					player.getComponent(RenderingComp.class).setColor(message.playerCharacteristics.color);
					player.refresh();
					server.sendToAllExceptTCP(connection.getID(), message);
				}
			}
			@Override
			public void connected(Connection arg0) {
				super.connected(arg0);	
				
			}
		};
	}


}
