package screenCore;

import GUI.graphics.GUIRenderingContext;
import GUI.graphics.LookAndFeel;
import misc.SoundManager;
import utils.Camera2D;
import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.TimeSpan;
import content.ContentManager;
import demos.WorldBuilder;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entityFramework.IEntityWorld;
import gameMap.Scene;
import graphics.Color;
import graphics.ShaderEffect;
import graphics.SpriteBatch;

public class LobbyWorld extends EntityScreen {
	
	private IEntityWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Scene scene;
	private Mouse mouse;
	private Keyboard keyboard;
	private SoundManager soundManager;
	
	private GUIRenderingContext context;

	public LobbyWorld() {
		super(false, TimeSpan.Zero, TimeSpan.Zero);
	}

	@Override
	protected void loadContent(ContentManager manager) {
		this.scene = manager.load("PerspectiveV5.xml", Scene.class);
		this.mouse = new Mouse();
		this.keyboard = new Keyboard();
		this.soundManager = new SoundManager(manager, 0.1f,1.0f,1.0f);
		this.camera = new Camera2D(scene.getWorldBounds(), 
				   this.ScreenManager.getSpriteBatch().getViewport());
		
		this.spriteBatch = this.ScreenManager.getSpriteBatch();
		
		LookAndFeel feel = manager.load("gui.tdict", LookAndFeel.class);
		feel.setDefaultFont(manager.loadFont("arialb20.xml"));
		ShaderEffect dissabledEffect = manager.loadShaderEffect("GrayScale.effect");
		context = new GUIRenderingContext(this.ScreenManager.getSpriteBatch(), feel, dissabledEffect);
		
		this.gameWorld = WorldBuilder.buildServerWorld(camera, scene, mouse, keyboard, manager, this.soundManager, spriteBatch, false, "Player0");
		this.gameWorld.initialize();
	}

	@Override
	protected void addRenderingSystem(IEntitySystemManager systemManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addLogicSystem(IEntitySystemManager systemManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addEntities(IEntityManager entityManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleInput(Mouse mouse, Keyboard keyboard) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(GameTime time, SpriteBatch batch) {
		super.render(time, batch);
	}

}
