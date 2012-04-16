package demos;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.google.inject.Guice;
import com.google.inject.Injector;

import content.ContentManager;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityDatabase;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entitySystems.CameraControlSystem;

import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;
import tests.EntityModule;
import utils.Camera2D;
import utils.Clock;
import utils.GameTime;
import utils.Rectangle;


public class MapDemo extends Demo{
	private static final String playerAsset = "Player.archetype";
	private static Rectangle screenDim = new Rectangle(0,0,800,600);
	
	private GameWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Clock clock;
	private Scene scene;
	
	public MapDemo() {
		super(screenDim, 60);
	}

	protected void initialize() {
		scene = ContentManager.load("SomeSortOfScene.xml", Scene.class);
		camera = new Camera2D(scene.getWorldBounds(), screenDim);
		clock = new Clock();
		spriteBatch = new SpriteBatch(screenDim);
		
		Injector injector = Guice.createInjector(new EntityModule());
		IEntityManager manager = injector.getInstance(IEntityManager.class);
		IEntityDatabase db = injector.getInstance(IEntityDatabase.class);
		IEntitySystemManager sm = injector.getInstance(IEntitySystemManager.class);
		
		gameWorld = new GameWorld(manager, sm, db, camera, spriteBatch, scene);
		gameWorld.initialize();
		

		IEntityArchetype archetype = ContentManager.loadArchetype(playerAsset);
		IEntity entity = manager.createEntity(archetype);
		entity.addToGroup(CameraControlSystem.GROUP_NAME);
		entity.refresh();
		
		sm.initialize();
	}
	
	public static void main(String[] args) throws LWJGLException {
		MapDemo demo = new MapDemo();
		demo.start();
	}

	@Override
	public void update(GameTime time) {
		this.gameWorld.update(time);
		this.gameWorld.getEntityManager().destoryKilledEntities();
		
	}

	@Override
	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);
		this.spriteBatch.begin(null, this.camera.getTransformation());
		this.gameWorld.render();
		this.spriteBatch.end();
		
	}
}
