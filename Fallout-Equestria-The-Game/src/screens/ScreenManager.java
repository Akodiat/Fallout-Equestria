package screens;

import graphics.SpriteBatch;
import utils.Camera2D;
import utils.Mouse;
import utils.Rectangle;
import utils.TimeSpan;

public class ScreenManager {
	private static final Rectangle screenDim = new Rectangle(0,0,1366,768);
	
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Mouse mouse;
	
	public ScreenManager(int width, int height) {
		this.camera = new Camera2D(screenDim, screenDim);
		this.spriteBatch = new SpriteBatch(screenDim);
		this.mouse = new Mouse();
	}
	
	public void transition(Screen fromScreen, Screen toScreen, TimeSpan time) {
		fromScreen.switchFrom(time);
		toScreen.switchTo(time);
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
