package demos;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import components.BehaviourComp;
import components.TransformationComp;

import content.ContentManager;
import entityFramework.IComponent;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityDatabase;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entitySystems.CameraControlSystem;

import gameMap.ArchetypeNode;
import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;
import scripting.PlayerScript;
import tests.EntityModule;
import utils.Camera2D;
import utils.Clock;
import utils.GameTime;
import utils.Rectangle;


public class MapDemo extends Demo{
	private static final String playerAsset = "Player.archetype";
	private static Rectangle screenDim = new Rectangle(0,0,1920,1080);
	
	private GameWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Clock clock;
	private Scene scene;
	
	public MapDemo() {
		super(screenDim, 60);
	}

	protected void initialize() {
		scene = ContentManager.load("MaseScenev0.xml", Scene.class);
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
		entity.addComponent(new BehaviourComp(new PlayerScript()));
		entity.addToGroup(CameraControlSystem.GROUP_NAME);
		entity.refresh();
		
		sm.initialize();
		
		
		addArchetypes(manager);
		
	}
	
	private void addArchetypes(IEntityManager manager) {
		ImmutableList<ArchetypeNode>  nodes = this.scene.getNodes();
		for (ArchetypeNode node : nodes) {			
			createEntity(manager, node);
		}	
	}

	private void createEntity(IEntityManager manager, ArchetypeNode node) {
		IEntity entity = manager.createEntity(node.getArchetype());
		TransformationComp comp = entity.getComponent(TransformationComp.class);
		comp.setPosition(node.getPosition());
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
