package demos;

import graphics.Color;
import graphics.SpriteBatch;
import screenCore.BackgroundScreen;
import screenCore.PausScreen;
import screenCore.ScreenManager;
import screenCore.Test2GUIScreen;
import screenCore.TestGUIScreen;
import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.TimeSpan;
import content.ContentManager;
import utils.Rectangle;

public class ScreenDemo extends Demo{
	private static Rectangle screenDim = new Rectangle(0,0,1366,768);
	private ScreenManager screenManager;
	private SpriteBatch batch;
	private Mouse mouse;
	private Keyboard keyboard;
	
	public static void main(String[] args) {
		new ScreenDemo().start();
	}
	
	public ScreenDemo() {
		super(screenDim, 60);
	}

	@Override
	public void update(GameTime time) {
		this.keyboard.poll();
		this.mouse.poll(screenDim);
		screenManager.update(time);
	}

	@Override
	public void render(GameTime time) {
		this.batch.clearScreen(Color.Black);
		screenManager.render(time);
	}

	
	
	@Override
	protected void initialize() {
		this.batch = new SpriteBatch(screenDim);
		ContentManager manager = new ContentManager("resources");
		this.keyboard = new Keyboard();
		this.mouse = new Mouse();
		
		this.screenManager = new ScreenManager(batch, manager,screenDim, mouse, keyboard);
		BackgroundScreen screen = new BackgroundScreen(false, TimeSpan.fromSeconds(15.0f), TimeSpan.Zero, "foebackground.png");
		TestGUIScreen screen2 = new TestGUIScreen("gui.tdict");
		Test2GUIScreen screen3 = new Test2GUIScreen("graygui.tdict");
		PausScreen screen4 = new PausScreen("gui.tdict");
		
		this.screenManager.registerScreen("BG_Screen", screen);
		this.screenManager.registerScreen("Test_Screen", screen2);
		this.screenManager.registerScreen("Test2_Screen", screen3);
		this.screenManager.registerScreen("PauseScreen", screen4);
		
		this.screenManager.addScreen("BG_Screen");
		this.screenManager.addScreen("Test_Screen");
		
		this.screenManager.initialize();
	}

}
