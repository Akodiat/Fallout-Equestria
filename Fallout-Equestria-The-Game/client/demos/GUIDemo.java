package demos;

import com.google.inject.Guice;
import components.GUIComp;

import content.ContentManager;

import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entityFramework.IEntityWorld;
import entitySystems.GUIRenderingSystem;
import entitySystems.ScriptMouseSystem;
import entitySystems.ScriptSystem;
import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;
import utils.Camera2D;
import utils.GameTime;
import utils.Rectangle;

public class GUIDemo extends Demo {
	private static final String playerAsset = "Player.archetype";
	private static final String aiAsset 	= "FollowingTextAI.archetype";
	private static Rectangle screenDim 		= new Rectangle(0,0,1366,768);

	private IEntityWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	
	public static void main(String[] args) {
		new GUIDemo().start();
	}
	
	public GUIDemo() {
		super(screenDim, 60);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameTime time) {
		this.gameWorld.update(time);
		this.gameWorld.getEntityManager().destoryKilledEntities();
	}

	@Override
	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);
		this.spriteBatch.begin();
		this.gameWorld.render();
		this.spriteBatch.end();
	}

	@Override
	protected void initialize() {
		this.camera = new Camera2D(screenDim, screenDim);
		this.spriteBatch = new SpriteBatch(screenDim);
		
		this.gameWorld = WorldBuilder.buildGUIWorld(camera, spriteBatch);
		this.gameWorld.initialize();
		
		IEntity entity = this.gameWorld.getEntityManager().createEntity(ContentManager.loadArchetype("FalloutButton.archetype"));
		entity.getComponent(GUIComp.class).setPosition(new Rectangle(100,100,200,80));
		
	}

}
