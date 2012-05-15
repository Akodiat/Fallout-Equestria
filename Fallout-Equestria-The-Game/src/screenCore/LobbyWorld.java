package screenCore;

import misc.SoundManager;
import utils.Camera2D;
import utils.Keyboard;
import utils.Mouse;
import utils.Rectangle;
import utils.TimeSpan;
import content.ContentManager;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entityFramework.IEntityWorld;
import gameMap.Scene;
import graphics.SpriteBatch;

public class LobbyWorld extends EntityScreen {
	
	private IEntityWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Scene scene;
	private Mouse mouse;
	private Keyboard keyboard;
	private SoundManager soundManager;

	public LobbyWorld() {
		super(false, TimeSpan.Zero, TimeSpan.Zero);
	}

	@Override
	protected void loadContent(ContentManager manager) {
		this.camera = new Camera2D(scene.getWorldBounds(), 
				   this.ScreenManager.getSpriteBatch().getViewport());
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

}
