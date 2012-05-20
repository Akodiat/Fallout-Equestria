package screenCore;

import GUI.graphics.GUIRenderingContext;
import GUI.graphics.LookAndFeel;
import sounds.SoundManager;
import systembuilders.WorldBuilder;
import utils.Camera2D;
import utils.input.Keyboard;
import utils.input.Mouse;
import utils.time.GameTime;
import utils.time.TimeSpan;
import content.ContentManager;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entityFramework.IEntityWorld;
import entitySystems.RenderingSystem;
import gameMap.Scene;
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
	}


	@Override
	protected void addEntitySystems(IEntitySystemManager manager) {
		manager.addRenderEntitySystem(new RenderingSystem(this.World, this.ScreenManager.getSpriteBatch()));
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
	}


}
