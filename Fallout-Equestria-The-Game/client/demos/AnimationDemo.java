package demos;

import java.util.ArrayList;
import java.util.List;

import math.Vector2;

import animation.Animation;
import animation.AnimationPlayer;
import animation.Bone;
import animation.Keyframe;
import animation.TextureBounds;
import animation.TextureEntry;

import com.google.inject.Guice;
import com.google.inject.Injector;

import components.AnimationComp;
import components.RenderingComp;
import components.ScriptComp;
import components.TransformationComp;

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
import graphics.Texture2D;
import scripting.PlayerScript;
import tests.EntityModule;
import utils.Camera2D;
import utils.Clock;
import utils.GameTime;
import utils.Rectangle;

public class AnimationDemo extends Demo {
	private static final String playerAsset = "Player.archetype";
	private static final String aiAsset 	= "FollowingTextAI.archetype";
	private static Rectangle screenDim 		= new Rectangle(0,0,800,600);

	private GameWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Scene scene;

	public static void main(String[] str) {
		AnimationDemo demo = new AnimationDemo();
		demo.start();
	}

	public AnimationDemo() {
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
		this.spriteBatch.begin(null, this.camera.getTransformation());
		this.gameWorld.render();
		this.spriteBatch.end();	
	}

	@Override
	protected void initialize() {
		scene = ContentManager.load("SomeSortOfScene.xml", Scene.class);
		camera = new Camera2D(scene.getWorldBounds(), screenDim);
		spriteBatch = new SpriteBatch(screenDim);

		Injector injector = Guice.createInjector(new EntityModule());
		IEntityManager manager = injector.getInstance(IEntityManager.class);
		IEntityDatabase db = injector.getInstance(IEntityDatabase.class);
		IEntitySystemManager sm = injector.getInstance(IEntitySystemManager.class);

		gameWorld = new GameWorld(manager, sm, db, camera, spriteBatch, scene);
		gameWorld.initialize();

		sm.initialize();

		//ANIMATION UGLY SHIT
		IEntityArchetype archetype = ContentManager.loadArchetype(playerAsset);
		IEntity entity = manager.createEntity(archetype);
		entity.addComponent(new ScriptComp(new PlayerScript()));
		
		Animation animation = ContentManager.load("pinkiewalk.anim", Animation.class);
		
		AnimationPlayer player = new AnimationPlayer();
		player.addAnimation("lol", animation);
		player.startAnimation("lol");
		
		
		entity.addComponent(new AnimationComp(player));
		//END OF ANIMATION UGLY SHIT


		entity.addToGroup(CameraControlSystem.GROUP_NAME);
		entity.refresh();

		archetype = ContentManager.loadArchetype(aiAsset);
		for (int i = 0; i < 20; i++) {
			entity = manager.createEntity(archetype);
			placeAtRandomPosition(entity);
			entity.refresh();

		}

	}

	private void placeAtRandomPosition(IEntity entity) {
		Vector2 pos = new Vector2((float)Math.random() * (this.scene.getWorldBounds().Width - 100),
				(float)Math.random() * (this.scene.getWorldBounds().Height - 100));
		entity.getComponent(TransformationComp.class).setPosition(pos);
	}

}
