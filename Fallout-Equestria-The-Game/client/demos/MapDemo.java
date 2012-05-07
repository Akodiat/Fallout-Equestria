package demos;

import misc.SoundManager;

import org.lwjgl.LWJGLException;
import components.BehaviourComp;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityWorld;
import entitySystems.CameraControlSystem;

import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;
import scripting.PlayerScript;
import utils.Camera2D;
import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.Rectangle;


public class MapDemo extends Demo{
	private static final String playerAsset = "Player.archetype";
	private static Rectangle screenDim = new Rectangle(0,0,1920,1080);
	
	private IEntityWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Scene scene;
	
	public MapDemo() {
		super(screenDim, 60);
	}

	protected void initialize() {
		scene = ContentManager.load("MaseScenev0.xml", Scene.class);
		camera = new Camera2D(scene.getWorldBounds(), screenDim);
		spriteBatch = new SpriteBatch(screenDim);
		SoundManager soundManager = new SoundManager(this.ContentManager,1.0f,1.0f,1.0f);
		
		
		gameWorld = WorldBuilder.buildGameWorld(camera, scene, new Mouse(), new Keyboard(), this.ContentManager,soundManager, spriteBatch, false);
		gameWorld.initialize();
		

		IEntityArchetype archetype = ContentManager.loadArchetype(playerAsset);
		IEntity entity = gameWorld.getEntityManager().createEntity(archetype);
		entity.addComponent(new BehaviourComp(new PlayerScript()));
		entity.addToGroup(CameraControlSystem.GROUP_NAME);
		entity.refresh();
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
		this.spriteBatch.clearScreen(Color.CornflowerBlue);
		this.spriteBatch.begin(null, this.camera.getTransformation());
		this.gameWorld.render();
		this.spriteBatch.end();
		
	}
}
