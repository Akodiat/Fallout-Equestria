package demos;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import content.ContentManager;

import utils.Rectangle;
import utils.time.Clock;
import utils.time.GameTime;


public abstract class Demo {

	private final Rectangle screenDim;
	private final Clock clock;
	private final int fps;
	protected ContentManager ContentManager;
	
	public Clock getClock() {
		return this.clock;
	}
	
	public Demo(Rectangle screenDim, int fps) {
		this.screenDim = screenDim;
		this.fps = fps;
		this.clock = new Clock();
		this.ContentManager = new ContentManager("resources");
	}	
	
	public void start() {

			try {
				Display.setVSyncEnabled(true);
				Display.setDisplayMode(new DisplayMode(screenDim.Width, screenDim.Height));
				Display.setFullscreen(true);
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
				end();
			}
	}
	
	protected void end() {
		// TODO Auto-generated method stub
		
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
