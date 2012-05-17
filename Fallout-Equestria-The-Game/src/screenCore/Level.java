package screenCore;

import java.util.Random;

import common.InputMessage;
import common.NetworkSystemBuilder;
import common.PlayerCharacteristics;

import utils.Camera2D;
import utils.EntitySystemBuilder;
import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.TimeSpan;
import content.ContentManager;
import entityFramework.EntityNetworkIDManager;
import entityFramework.IEntitySystemManager;
import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;

public class Level extends EntityScreen {
	private Camera2D camera;
	private Scene scene;
	private final String level;
	
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
													 this.ScreenManager.getNetwork().getServer(), 
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
						 this.ScreenManager.getNetwork().getClient(), 
						 new EntityNetworkIDManager(), this.ScreenManager.getSoundManager(), 
						 this.ScreenManager.getContentManager(), 
						 this.createPlayerCharacteristics());
		}
	}

	private PlayerCharacteristics createPlayerCharacteristics() {
		Random rand = new Random();
		
		PlayerCharacteristics playerChars = new PlayerCharacteristics();
		playerChars.bodyColor	= new Color(rand.nextFloat(), rand.nextFloat(),rand.nextFloat(),1.0f);
		playerChars.eyeColor 	= new Color(rand.nextFloat(), rand.nextFloat(),rand.nextFloat(),1.0f);
		playerChars.maneColor	= new Color(rand.nextFloat(), rand.nextFloat(),rand.nextFloat(),1.0f);
		
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
		this.ScreenManager.getNetwork().getClient().sendUDP(message);
	}

	@Override
	public void update(GameTime time, boolean otherScreeenHasFocus, boolean coveredByOtherScreen) {
		super.update(time, otherScreeenHasFocus, false);
	}
	
	@Override
	public void render(GameTime time, SpriteBatch spriteBatch) {
		spriteBatch.begin(null, this.camera.getTransformation());
		this.World.render();
		spriteBatch.end();
	}

}
