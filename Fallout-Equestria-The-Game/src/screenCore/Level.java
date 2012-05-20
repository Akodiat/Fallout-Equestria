package screenCore;


import misc.PlayerCharacteristics;

import common.messages.InputMessage;

import systembuilders.EntitySystemBuilder;
import systembuilders.NetworkSystemBuilder;
import utils.Camera2D;
import utils.input.Keyboard;
import utils.input.Mouse;
import utils.time.GameTime;
import utils.time.TimeSpan;
import content.ContentManager;
import entityFramework.EntityNetworkIDManager;
import entityFramework.IEntitySystemManager;
import gameMap.Scene;
import graphics.SpriteBatch;

public class Level extends EntityScreen {
	private Camera2D camera;
	private Scene scene;
	private final String level;
	private PlayerCharacteristics playerChars;
	
	public Level(boolean popup, TimeSpan transOnTime, TimeSpan transOffTime, String level) {
		super(popup, transOnTime, transOffTime);
		this.level = level;
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
		
		System.out.println("NOW WE ARE FINISHED!");
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
		
			NetworkSystemBuilder.createServerSystems(World,
													 this.ScreenManager.getNetwork(), 
													 this.ScreenManager.getSoundManager(), 
													 this.ScreenManager.getContentManager(), 
													 this.createPlayerCharacteristics());
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
		this.ScreenManager.getNetwork().getClient().sendUDP(message);
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

}
