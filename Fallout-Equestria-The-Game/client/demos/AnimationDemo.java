package demos;

import math.Vector2;
import misc.SoundManager;
import animation.Animation;
import animation.AnimationPlayer;
import components.*;
import entityFramework.*;
import entitySystems.CameraControlSystem;
import gameMap.Scene;
import graphics.*;
import scripting.PlayerScript;
import utils.*;


public class AnimationDemo extends Demo {
	private static final String playerAsset = "Player.archetype";
	private static final String aiAsset 	= "FollowingTextAI.archetype";
	private static Rectangle screenDim 		= new Rectangle(0,0,1920,1080);

	private IEntityWorld gameWorld;
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
		scene = ContentManager.load("MaseScenev0.xml", Scene.class);
		camera = new Camera2D(scene.getWorldBounds(), screenDim);
		spriteBatch = new SpriteBatch(screenDim);
		SoundManager soundManager = new SoundManager(this.ContentManager,1.0f,1.0f,1.0f);
		
		gameWorld = WorldBuilder.buildGameWorld(camera, scene,new Mouse(), new Keyboard(), this.ContentManager,soundManager, spriteBatch, true);
		gameWorld.initialize();


		//ANIMATION UGLY SHIT
		IEntityArchetype archetype = ContentManager.loadArchetype(playerAsset);
		IEntity entity = gameWorld.getEntityManager().createEntity(archetype);
		entity.addComponent(new BehaviourComp(new PlayerScript()));
		
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
			entity = gameWorld.getEntityManager().createEntity(archetype);
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
