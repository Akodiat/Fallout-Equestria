package state;

import utils.Camera2D;
import utils.GameTime;
import entityFramework.IEntityWorld;
import graphics.SpriteBatch;

public abstract class GameState {

	protected IEntityWorld world;
	protected GameStateManager stateManager;
	protected SpriteBatch spriteBatch;
	protected Camera2D camera;
	protected boolean initialized;
	
	
	public GameState(Camera2D camera, SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
		this.camera = camera;
	}
	
	public void initialize() {
		this.initialized = true;
	}
	
	public void update(GameTime time) {
		this.world.update(time);
		this.world.getEntityManager().destoryKilledEntities();
	}
	
	public void render(GameTime time) {
		this.spriteBatch.begin(null, camera.getTransformation());
		this.world.render();
		this.spriteBatch.end();
	}
	
	public boolean isInitialized() {
		return this.initialized;
	}
	
	public void setInitialized() {
		this.initialized = true;
	}
}
