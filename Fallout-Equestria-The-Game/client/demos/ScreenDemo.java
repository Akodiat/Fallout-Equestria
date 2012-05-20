package demos;

import animation.PonyColorChangeHelper;
import misc.SoundManager;
import graphics.Color;
import graphics.SpriteBatch;
import screenCore.BackgroundScreen;
import screenCore.GOLScreen;
import screenCore.HostScreen;
import screenCore.Level;
import screenCore.LobbyGUI;
import screenCore.LobbyWorld;
import screenCore.ConnectScreen;
import screenCore.MultiplayerScreen;
import screenCore.PausScreen;
import screenCore.PonyCreatorScreen;
import screenCore.PonySelector;
import screenCore.ScreenManager;
import screenCore.Test2GUIScreen;
import screenCore.TestGUIScreen;
import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.Network;
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
		
		soundManager.playMusic("music\\DrDissonanceBGmusic.ogg");
		
		this.screenManager = new ScreenManager(batch, manager, soundManager,new Network(45777,43567), screenDim, mouse, keyboard);
		BackgroundScreen screen = new BackgroundScreen(false, TimeSpan.fromSeconds(15.0f), TimeSpan.Zero, "foebackground.png");
		TestGUIScreen screen2 = new TestGUIScreen("gui.tdict");
		Test2GUIScreen screen3 = new Test2GUIScreen("graygui.tdict");
		PausScreen screen4 = new PausScreen("gui.tdict");
		GOLScreen screen5 = new GOLScreen();
		ConnectScreen screen6 = new ConnectScreen("gui.tdict");
		HostScreen screen7 = new HostScreen("gui.tdict");
		LobbyGUI screen8 = new LobbyGUI("gui.tdict");
		LobbyWorld screen9 = new LobbyWorld();
		MultiplayerScreen screen10 = new MultiplayerScreen("gui.tdict");
		Level screen11 = new Level(false, TimeSpan.Zero, TimeSpan.Zero, "PerspectiveV5.xml");
		Level screen12 = new Level(false, TimeSpan.Zero, TimeSpan.Zero, "Lobby.xml");
		PonyCreatorScreen screen13 = new PonyCreatorScreen("gui.tdict");
		PonySelector screen14 = new PonySelector("gui.tdict");
		
		this.screenManager.registerScreen("BG_Screen", screen);
		this.screenManager.registerScreen("Test_Screen", screen2);
		this.screenManager.registerScreen("Test2_Screen", screen3);
		this.screenManager.registerScreen("PauseScreen", screen4);
		this.screenManager.registerScreen("GOLScreen", screen5);
		this.screenManager.registerScreen("Connect", screen6);
		this.screenManager.registerScreen("Host", screen7);
		this.screenManager.registerScreen("LobbyGUI", screen8);
		this.screenManager.registerScreen("LobbyWorld", screen9);
		this.screenManager.registerScreen("Multiplayer", screen10);
		this.screenManager.registerScreen("Level1", screen11);
		this.screenManager.registerScreen("Lobby", screen12);
		this.screenManager.registerScreen("PonyCreator", new PonyCreatorScreen("gui.tdict"));
		this.screenManager.registerScreen("PonySelector", screen14);

		
		this.screenManager.addScreen("BG_Screen");
		this.screenManager.addScreen("Test_Screen");
		
		this.screenManager.initialize();
	}

}
