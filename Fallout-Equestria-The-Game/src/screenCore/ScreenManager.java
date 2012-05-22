package screenCore;

import graphics.SpriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import misc.PlayerCharacteristics;


import com.google.common.collect.ImmutableList;
import common.Network;

import sounds.SoundManager;
import utils.Rectangle;
import utils.input.Keyboard;
import utils.input.Mouse;
import utils.time.GameTime;
import content.ContentManager;

public class ScreenManager {
	
	private final List<GameScreen> activeScreens;
	private final List<GameScreen> screensToUpdate;
	private final Mouse mouse;
	private final Keyboard keyboard;
	private final SpriteBatch spriteBatch;
	private final ContentManager contentManager;
	private final SoundManager soundManager;
	private final Network network;
	private final Map<String, GameScreen> avalibleScreens;
	private PlayerCharacteristics playerCharacteristics;
	
	private boolean isInitialized;
	private Rectangle viewport;	


	public Rectangle getViewport() {
		return this.viewport;
	}
	public void setViewport(Rectangle viewport) {
		this.viewport = viewport;
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}	
	
	public ContentManager getContentManager()  {
		return this.contentManager;
	}
	
	public ImmutableList<GameScreen> getScreens() {
		return ImmutableList.copyOf(this.activeScreens);
	}
	
	public SoundManager getSoundManager() {
		return soundManager;
	}
	
	public Network getNetwork() {
		return network;
	}
	
	public Mouse getMouse() {
		return this.mouse;
	}
	
	public Keyboard getKeyboard() {
		return this.keyboard;
	}
	
	public ScreenManager(SpriteBatch spriteBatch, ContentManager contentManager, SoundManager soundManager, Network network, Rectangle viewport, Mouse mouse, Keyboard keyboard, PlayerCharacteristics playerCharacteristics) {
		this.activeScreens = new ArrayList<>();
		this.screensToUpdate = new ArrayList<>();
		this.viewport = viewport;
		this.spriteBatch = spriteBatch;
		this.contentManager = contentManager;
		this.mouse = mouse;
		this.keyboard = keyboard;
		this.avalibleScreens = new HashMap<>();
		this.soundManager = soundManager;
		this.network = network;
		this.playerCharacteristics = playerCharacteristics;
	}
	
	public void initialize() {
		this.isInitialized = true;
		for (GameScreen screen : this.activeScreens){
			screen.initialize(contentManager);			
		}		
	}
	
	public void update(GameTime time) {
		screensToUpdate.clear();
		screensToUpdate.addAll(activeScreens);
		
		boolean otherScreeenHasFocus = false;
		boolean coveredByOtherScreen = false;
		
		for (int i = screensToUpdate.size() - 1; i >= 0 ; i--) {
			GameScreen screen = screensToUpdate.get(i);
			screen.update(time, otherScreeenHasFocus, coveredByOtherScreen);
			
			ScreenState state = screen.getScreenState();
			
			if(state == ScreenState.TransitionOn || 
			   state == ScreenState.Active) {
				
				if(!otherScreeenHasFocus) {
					screen.handleInput(mouse, keyboard);
					otherScreeenHasFocus = true;
				}
			}
			
			if(!screen.isPopup()) {
				coveredByOtherScreen = true;
			}
			
			if(screen.isExiting()) {
				coveredByOtherScreen = false;
			}
			
		}
	}
	
	public void render(GameTime time) {
		for (GameScreen screen : this.activeScreens) {
			
			if(screen.getScreenState() == ScreenState.Hidden) 
				continue;
			
			screen.render(time, this.spriteBatch);
		}
	}
	
	public void registerScreen(String screenID, GameScreen screen) {
		this.avalibleScreens.put(screenID, screen);
	}
	
	public void unregisterScreen(String screenID) {
		this.avalibleScreens.remove(screenID);
	}
	
	public void unregisterAllScreens() {
		this.avalibleScreens.clear();
	}
	
	public void addScreen(String screenID) {
		GameScreen screen = this.avalibleScreens.get(screenID);
		
		screen.setScreenManager(this);
		screen.setExiting(false);
		this.activeScreens.add(screen);
		
		if(this.isInitialized) {
			screen.initialize(this.contentManager);
		}

		screen.onEnter();
	}
	
	public void removeScreen(String screenID) {
		GameScreen screen = this.avalibleScreens.get(screenID);
		if(screen != null) {
			screen.onExit();
			activeScreens.remove(screen);
			screensToUpdate.remove(screen);
		} 
	}
	
	public void removeScreen(GameScreen screen) {
		activeScreens.remove(screen);
		screensToUpdate.remove(screen);
		
		if(!this.activeScreens.isEmpty()) {
			this.activeScreens.get(this.activeScreens.size() - 1).onEnter();
		}
	}

	public void removeAllScreens() {
		for (GameScreen screen : this.activeScreens) {
			screen.reset();
		}

		this.activeScreens.clear();	
	}
	public PlayerCharacteristics getPlayerCharacteristics() {
		return playerCharacteristics;
	}
	
	public void setPlayerCharacteristics(PlayerCharacteristics pc) {
		this.playerCharacteristics = pc;
	}
}
