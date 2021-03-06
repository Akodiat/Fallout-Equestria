package screenCore;


import math.Vector2;
import player.PlayerCharacteristics;
import builders.EntitySystemBuilder;
import builders.NetworkSystemBuilder;

import common.messages.InputMessage;

import utils.Camera2D;
import utils.input.Keyboard;
import utils.input.Mouse;
import utils.time.GameTime;
import utils.time.TimeSpan;
import content.ContentManager;
import entityFramework.EntityNetworkIDManager;
import entityFramework.IEntitySystemManager;
import gameMap.Scene;
import gameMap.SceneNode;
import graphics.SpriteBatch;

public class Level extends EntityScreen {
	private Camera2D camera;
	protected Scene scene;
	private final String level;
	private PlayerCharacteristics playerChars;
	private boolean typing;
	
	public Level(boolean popup, TimeSpan transOnTime, TimeSpan transOffTime, String level) {
		super(popup, transOnTime, transOffTime);
		this.level = level;
		this.typing = false;
	}

	@Override
	protected void loadContent(ContentManager contentManager) {
		this.scene = contentManager.load(level, Scene.class);
		this.camera = new Camera2D(scene.getWorldBounds(),this.ScreenManager.getViewport());
	}

	@Override
	protected void addEntitySystems(IEntitySystemManager systemManager) {
	
	}

	@Override
	public void onTransitionFinished() {
		this.World.getDatabase().clear();
		
		if(this.ScreenManager.getNetwork().isServer()) {
			EntitySystemBuilder.buildServerSystems(this.World, 
												   camera, 
												   scene, 
												   this.ScreenManager.getMouse(),
												   this.ScreenManager.getKeyboard(), 
												   this.ScreenManager.getContentManager(), 
												   this.ScreenManager.getSoundManager(), 
												   this.ScreenManager.getSpriteBatch(), 
												   false, "Player0");
		
			Vector2 playerSpawnPosition = this.extractPlayerSpawnPosition();
			
			NetworkSystemBuilder.createServerSystems(World,
													 this.ScreenManager.getNetwork(), 
													 this.ScreenManager.getSoundManager(), 
													 this.ScreenManager.getContentManager(), 
													 this.createPlayerCharacteristics(),
													 playerSpawnPosition);
		} else {
			EntitySystemBuilder.buildClientWorld(this.World, 
					   camera, 
					   scene, 
					   this.ScreenManager.getMouse(),
					   this.ScreenManager.getKeyboard(), 
					   this.ScreenManager.getContentManager(), 
					   this.ScreenManager.getSoundManager(), 
					   this.ScreenManager.getSpriteBatch(), 
					   false, "Player" + this.ScreenManager.getNetwork().getClient().getID());

			NetworkSystemBuilder.createClientSystems(World,
						 this.ScreenManager.getNetwork(), 
						 new EntityNetworkIDManager(), this.ScreenManager.getSoundManager(), 
						 this.ScreenManager.getContentManager(), 
						 this.createPlayerCharacteristics());
		}
		this.World.initialize();
	}
	
	private Vector2 extractPlayerSpawnPosition() {
		SceneNode spawnPoint = this.scene.getNodeByID("Spawn_Player");
		if(spawnPoint != null) {
			return spawnPoint.getPosition();
		}
		
		return new Vector2(1000,1000);
	}

	private PlayerCharacteristics createPlayerCharacteristics() {
		this.playerChars = this.ScreenManager.getPlayerCharacteristics();
		return playerChars;
	}

	@Override
	public void handleInput(Mouse mouse, Keyboard keyboard) {
		if(this.ScreenManager.getNetwork().isClient()) {
				this.sendInput(mouse, keyboard);
		} 
	}

	private void sendInput(Mouse mouse, Keyboard keyboard) {
		InputMessage message = new InputMessage();
		message.mouse = mouse;
		message.keyboard = keyboard;
		message.networkID = this.ScreenManager.getNetwork().getClient().getID();
		
		if(!isTyping()) {
			this.ScreenManager.getNetwork().getClient().sendUDP(message);
		}
	}

	@Override
	public void update(GameTime time, boolean otherScreeenHasFocus, boolean coveredByOtherScreen) {
		super.update(time, otherScreeenHasFocus, false);
		this.handleInput(this.ScreenManager.getMouse(), this.ScreenManager.getKeyboard());
	}
	
	@Override
	public void render(GameTime time, SpriteBatch spriteBatch) {
		spriteBatch.begin(null, this.camera.getTransformation());
		this.World.render();
		spriteBatch.end();
	}
	
	public boolean isTyping() {
		return typing;
	}

	public void setTyping(boolean typing) {
		this.typing = typing;
	}
}
