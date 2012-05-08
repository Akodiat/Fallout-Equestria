package demos;

import misc.SoundManager;
import graphics.Color;
import graphics.SpriteBatch;
import screenCore.BackgroundScreen;
import screenCore.GOLScreen;
import screenCore.HostScreen;
import screenCore.MultiplayerScreen;
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
		SoundManager soundManager = new SoundManager(this.ContentManager,1.0f,1.0f,1.0f);
		
		
		this.screenManager = new ScreenManager(batch, manager, soundManager, screenDim, mouse, keyboard);
		BackgroundScreen screen = new BackgroundScreen(false, TimeSpan.fromSeconds(15.0f), TimeSpan.Zero, "foebackground.png");
		TestGUIScreen screen2 = new TestGUIScreen("gui.tdict");
		Test2GUIScreen screen3 = new Test2GUIScreen("graygui.tdict");
		PausScreen screen4 = new PausScreen("gui.tdict");
		GOLScreen screen5 = new GOLScreen();
		MultiplayerScreen screen6 = new MultiplayerScreen("gui.tdict");
		HostScreen screen7 = new HostScreen("gui.tdict");
		
		this.screenManager.registerScreen("BG_Screen", screen);
		this.screenManager.registerScreen("Test_Screen", screen2);
		this.screenManager.registerScreen("Test2_Screen", screen3);
		this.screenManager.registerScreen("PauseScreen", screen4);
		this.screenManager.registerScreen("GOLScreen", screen5);
		this.screenManager.registerScreen("Multiplayer", screen6);
		this.screenManager.registerScreen("Host", screen7);
		
		this.screenManager.addScreen("BG_Screen");
		this.screenManager.addScreen("Test_Screen");
		
		this.screenManager.initialize();
	}

}
