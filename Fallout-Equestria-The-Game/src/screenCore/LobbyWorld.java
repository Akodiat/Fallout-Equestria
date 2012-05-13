package screenCore;

import utils.Camera2D;
import utils.Keyboard;
import utils.Mouse;
import utils.TimeSpan;
import content.ContentManager;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import graphics.Texture2D;

public class LobbyWorld extends EntityScreen {
	private Camera2D camera;
	private Texture2D background;

	public LobbyWorld() {
		super(false, TimeSpan.Zero, TimeSpan.Zero);
	}

	@Override
	protected void loadContent(ContentManager manager) {
		this.background = manager.loadTexture("sky.png");
		this.camera = new Camera2D(this.background.getBounds(), 
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
