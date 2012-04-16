package demos;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import utils.Clock;
import utils.GameTime;
import utils.Rectangle;


public abstract class Demo {

	private final Rectangle screenDim;
	private final Clock clock;
	private final int fps;
	
	public Clock getClock() {
		return this.clock;
	}
	
	public Demo(Rectangle screenDim, int fps) {
		this.screenDim = screenDim;
		this.fps = fps;
		this.clock = new Clock();
	}	
	
	public void start() {

			try {
				Display.setVSyncEnabled(true);
				Display.setDisplayMode(new DisplayMode(screenDim.Width, screenDim.Height));
				Display.create();
				this.initialize();
				
				while(!Display.isCloseRequested()) {
					this.gameLoop();
					Display.update();
					Display.sync(this.fps);
				}	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				Display.destroy();
			}
	}
	
	private void gameLoop() {
		this.clock.update();
		GameTime time = this.clock.getGameTime();
		this.update(time);
		this.render(time);
	}

	public abstract void update(GameTime time);
	public abstract void render(GameTime time);
	protected abstract void initialize();
	
	
}
