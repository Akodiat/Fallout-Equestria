package tests;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import entityFramework.IEntityManager;
import utils.Rectangle;
import graphics.Color;
import graphics.SpriteBatch;

public abstract class AbstractSystemTest {
	
	
	protected SystemTester tester;
	protected SpriteBatch graphics;
	private final Rectangle screenDim;
	private final boolean isFullScreen;
	
	public AbstractSystemTest(Rectangle screenDim, boolean fullScreen) {
		this.screenDim = screenDim;
		this.isFullScreen = fullScreen;
	}
	
	public final void start()  {
			try {
				Display.setDisplayMode(new DisplayMode(screenDim.Width,screenDim.Height));
				Display.setFullscreen(isFullScreen);
				Display.create();
			} catch (LWJGLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.graphics = new SpriteBatch(this.screenDim);	
			this.tester = new SystemTester();
			
			initializeSystems();
			initializeEntities(this.tester.getWorld().getEntityManager());
			
			tester.startTesting();
			
			while(!Display.isCloseRequested()) {
				tester.updateWorld(1.0f / 60f);
				
				graphics.clearScreen(new Color(157, 150, 101, 255));
				graphics.begin();
				tester.renderWorld();
				graphics.end();
				
				tester.getWorld().getEntityManager().destoryKilledEntities();
				Display.update();
				Display.sync(60);
			}
			
			Display.destroy(); 
	}
	
	public abstract void initializeSystems();
	public abstract void initializeEntities(IEntityManager manager);

}
