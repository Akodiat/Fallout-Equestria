package screenCore;

import utils.GameTime;
import utils.TimeSpan;
import content.ContentManager;
import demos.WorldBuilder;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entityFramework.IEntityWorld;
import graphics.SpriteBatch;

public abstract class EntityScreen extends GameScreen{
	protected IEntityWorld World;
	
	public EntityScreen(boolean popup, TimeSpan transOnTime,
			TimeSpan transOffTime) {
		super(popup, transOnTime, transOffTime);
	}

	@Override
	public void initialize(ContentManager contentManager) {
		this.loadContent(contentManager);
		this.World = WorldBuilder.buildEmptyWorld();
		this.addLogicSystem(this.World.getSystemManager());
		this.addRenderingSystem(this.World.getSystemManager());
		this.World.initialize();
		this.addEntities(this.World.getEntityManager());
	}
	
	protected abstract void loadContent(ContentManager contentManager);
	protected abstract void addRenderingSystem(IEntitySystemManager systemManager);
	protected abstract void addLogicSystem(IEntitySystemManager systemManager);
	protected abstract void addEntities(IEntityManager entityManager);
	

	@Override
	public void update(GameTime time, boolean otherScreeenHasFocus,
			boolean coveredByOtherScreen) {
		super.update(time, otherScreeenHasFocus, coveredByOtherScreen);
		
		this.World.update(time);
		this.World.getEntityManager().destoryKilledEntities();
	}
	
	@Override
	public void render(GameTime time, SpriteBatch spriteBatch) {
		spriteBatch.begin();
		this.World.render();
		spriteBatch.end();
	}
}
