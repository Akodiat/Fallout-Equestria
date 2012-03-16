package tests;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Color;

import entityFramework.IEntityManager;
import utils.Rectangle;
import graphics.Graphics;

public abstract class AbstractSystemTest {
	
	
	protected SystemTester tester;
	protected Graphics graphics;
	private final Rectangle screenDim;
	private final boolean isFullScreen;
	
	public AbstractSystemTest(Rectangle screenDim, boolean fullScreen) {
		this.screenDim = screenDim;
		this.isFullScreen = fullScreen;
	}
	
	public final void start() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(screenDim.getWidth(),screenDim.getHeight()));
		Display.setFullscreen(isFullScreen);
		Display.create();
		this.graphics = new Graphics(this.screenDim);	
		this.tester = new SystemTester();
		
		initializeSystems();
		initializeEntities(this.tester.getWorld().getEntityManager());
		
		tester.startTesting();
		
		while(!Display.isCloseRequested()) {
			tester.updateWorld(1.0f / 60f);
			
			graphics.clearScreen(new Color(240, 104, 255, 255));
			
			tester.renderWorld();
			Display.update();
			Display.sync(60);
		}
	}
	
	public abstract void initializeSystems();
	public abstract void initializeEntities(IEntityManager manager);

}