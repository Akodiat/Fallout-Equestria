package demos;

import player.PlayerCharacteristics;

import common.Network;

import graphics.Color;
import graphics.SpriteBatch;
import screenCore.*;
import sounds.SoundManager;
import utils.Rectangle;
import utils.input.Keyboard;
import utils.input.Mouse;
import utils.time.GameTime;
import utils.time.TimeSpan;

public class Game extends Demo{
	private static Rectangle screenDim = new Rectangle(0,0,1366,768);
	private ScreenManager screenManager;
	private SpriteBatch batch;
	private Mouse mouse;
	private Keyboard keyboard;
	private PlayerCharacteristics playerCharacteristics;
	
	public static void main(String[] args) {
		new Game().start();
	}
	
	public Game() {
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
		this.keyboard = new Keyboard();
		this.mouse = new Mouse();
		SoundManager soundManager = new SoundManager(this.ContentManager,1.0f,1.0f,1.0f);
		this.playerCharacteristics = new PlayerCharacteristics();
		
		soundManager.playMusic("music\\DrDissonanceBGmusic.ogg");
		
		this.screenManager = new ScreenManager(batch, this.ContentManager, soundManager,new Network(45777,43567), screenDim, mouse, keyboard, playerCharacteristics);
		BackgroundScreen screen = new BackgroundScreen(false, TimeSpan.fromSeconds(15.0f), TimeSpan.Zero, "foebackground.png");
		StartGUIScreen screen2 = new StartGUIScreen("gui.tdict");
		SinglePlayerScreen screen3 = new SinglePlayerScreen("graygui.tdict");
		PausScreen screen4 = new PausScreen("gui.tdict");
		GOLScreen screen5 = new GOLScreen();
		ConnectScreen screen6 = new ConnectScreen("gui.tdict");
		HostScreen screen7 = new HostScreen("gui.tdict");
		LobbyGUI screen8 = new LobbyGUI("gui.tdict");
		MultiplayerScreen screen10 = new MultiplayerScreen("gui.tdict");
		Level screen11 = new Level1(false, TimeSpan.Zero, TimeSpan.Zero, "Level1V2.xml");
		Level screen12 = new Level(false, TimeSpan.Zero, TimeSpan.Zero, "Lobby.xml");
		PonyCreatorScreen screen13 = new PonyCreatorScreen("gui.tdict");
		PonySelector screen14 = new PonySelector("gui.tdict");
		ChatScreen screen15 = new ChatScreen("gui.tdict");
		
		this.screenManager.registerScreen("BG_Screen", screen);
		this.screenManager.registerScreen("Test_Screen", screen2);
		this.screenManager.registerScreen("Test2_Screen", screen3);
		this.screenManager.registerScreen("PauseScreen", screen4);
		this.screenManager.registerScreen("GOLScreen", screen5);
		this.screenManager.registerScreen("Connect", screen6);
		this.screenManager.registerScreen("Host", screen7);
		this.screenManager.registerScreen("LobbyGUI", screen8);
		this.screenManager.registerScreen("Multiplayer", screen10);
		this.screenManager.registerScreen("Level1", screen11);
		this.screenManager.registerScreen("Lobby", screen12);
		this.screenManager.registerScreen("PonyCreator", screen13);
		this.screenManager.registerScreen("PonySelector", screen14);
		this.screenManager.registerScreen("ChatScreen", screen15);
		
		this.screenManager.addScreen("BG_Screen");
		this.screenManager.addScreen("Test_Screen");
		
		this.screenManager.initialize();
	}

}
