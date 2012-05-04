package screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import graphics.SpriteBatch;
import utils.Camera2D;
import utils.GameTime;
import utils.Mouse;
import utils.Rectangle;

public class ScreenManager {
	private static final Rectangle screenDim = new Rectangle(0,0,1366,768);
	
	private Map<String, Screen> screens;
	private List<Screen> activeScreens;
	
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Mouse mouse;
	
	public ScreenManager(int width, int height) {
		this.camera = new Camera2D(screenDim, screenDim);
		this.spriteBatch = new SpriteBatch(screenDim);
		this.mouse = new Mouse();
		
		this.activeScreens = new ArrayList<Screen>();
	}
	
	public void loadScreen(String key, Screen screen, GameTime time, boolean transition) {
		activeScreens.add(screen);
		
		if(transition) {
			transition(screen, time);
		}
	}
	
	public void transition(Screen screen, GameTime time) {
		screen.swap(time);
	}
	
	public void update(GameTime time, Screen screen) {
		for(int i = 0; i < screens.size(); i++) {
			Screen scr = screens.get(i);
			
			if(scr.getTransitionState() == TransitionState.NONE) {
				scr.update(time, false); //FIX boolean
				break;
			} else if (scr.getTransitionState() == TransitionState.OUT && activeScreens.contains(scr)) {
				activeScreens.remove(scr);	
			} else if (scr.getTransitionState() == TransitionState.IN && !activeScreens.contains(scr)){
				activeScreens.add(scr);
			}
			
			transition(screen, time);
		}
	}
	
	public Camera2D getCamera() {
		return camera;
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public Mouse getMouse() {
		return mouse;
	}
}
