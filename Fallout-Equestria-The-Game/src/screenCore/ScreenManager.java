package screenCore;

import graphics.SpriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.Rectangle;
import content.ContentManager;

public class ScreenManager {
	
	private List<GameScreen> activeScreens;
	private List<GameScreen> screensToUpdate;
	private Mouse mouse;
	private Keyboard keyboard;
	private SpriteBatch spriteBatch;
	private ContentManager contentManager;
	private boolean isInitialized;
	private Rectangle viewport;	
	private Map<String, GameScreen> avalibleScreens;
	
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
	
	public ScreenManager(SpriteBatch spriteBatch, ContentManager contentManager,Rectangle viewport, Mouse mouse, Keyboard keyboard) {
		this.activeScreens = new ArrayList<>();
		this.screensToUpdate = new ArrayList<>();
		this.viewport = viewport;
		this.spriteBatch = spriteBatch;
		this.contentManager = contentManager;
		this.mouse = mouse;
		this.keyboard = keyboard;
		this.avalibleScreens = new HashMap<>();
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
	}
	
	public void removeScreen(String screenID) {
		GameScreen screen = this.avalibleScreens.get(screenID);
		if(screen != null) {
			activeScreens.remove(screen);
			screensToUpdate.remove(screen);
		}
	}
	
	public void removeScreen(GameScreen screen) {
		activeScreens.remove(screen);
		screensToUpdate.remove(screen);
	}

	public void removeAllScreens() {
		for (GameScreen screen : this.activeScreens) {
			screen.reset();
		}
		
		
		this.activeScreens.clear();
		
		
	}
}
