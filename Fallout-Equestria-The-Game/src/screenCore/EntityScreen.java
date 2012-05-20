package screenCore;

import systembuilders.WorldBuilder;
import utils.Camera2D;
import utils.time.GameTime;
import utils.time.TimeSpan;
import content.ContentManager;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entityFramework.IEntityWorld;

public abstract class EntityScreen extends GameScreen{
	protected IEntityWorld World;
	protected Camera2D camera;
	
	public EntityScreen(boolean popup, TimeSpan transOnTime,
			TimeSpan transOffTime) {
		super(popup, transOnTime, transOffTime);
	}

	@Override
	public void initialize(ContentManager contentManager) {
		this.World = WorldBuilder.buildEmptyNetworkedWorld(contentManager);
		this.loadContent(contentManager);
		this.addEntitySystems(this.World.getSystemManager());
		this.World.initialize();
		
		this.addEntities(this.World.getEntityManager());
	}
	
	@Override
	public void onTransitionFinished() {
		
	}
	
	protected abstract void loadContent(ContentManager contentManager);
	protected abstract void addEntitySystems(IEntitySystemManager manager);
	protected void addEntities(IEntityManager entityManager) { }

	@Override
	public void update(GameTime time, boolean otherScreeenHasFocus,
			boolean coveredByOtherScreen) {
		super.update(time, otherScreeenHasFocus, coveredByOtherScreen);
		this.World.getEntityManager().destoryKilledEntities();
		this.World.update(time);
	}
}
