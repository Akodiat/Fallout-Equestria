package demos;

import behavior.PlayerScript;
import math.Vector2;
import misc.PonyColorChangeHelper;
import animation.Animation;
import animation.AnimationPlayer;
import animation.Bones;
import animation.TextureDictionary;
import components.*;
import entityFramework.*;
import entitySystems.CameraControlSystem;
import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;
import sounds.SoundManager;
import systembuilders.WorldBuilder;
import utils.Camera2D;
import utils.Rectangle;
import utils.input.Keyboard;
import utils.input.Mouse;
import utils.time.GameTime;

public class RDAnimDemo extends Demo {
	private static final String playerAsset = "Player.archetype";
	private static Rectangle screenDim 		= new Rectangle(0,0,1337,768);

	private IEntityWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Scene scene;
	private Mouse mouse;
	private Keyboard keyboard;

	public static void main(String[] str) {
		RDAnimDemo demo = new RDAnimDemo();
		demo.start();
	}

	public RDAnimDemo() {
		super(screenDim, 60);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameTime time) {
		this.mouse.poll(screenDim);
		this.keyboard.poll();
		this.gameWorld.update(time);
		this.gameWorld.getEntityManager().destoryKilledEntities();
	}

	private void spawnManlyMan() {

		IEntityManager manager = this.gameWorld.getEntityManager();

		//MANLYMAN
		AnimationPlayer manPlayer = new AnimationPlayer();
		Animation walkAnimation = ContentManager.load("manlymanwalk.anim", Animation.class);
		manPlayer.addAnimation("walk", walkAnimation);
		Animation explodeAnimation = ContentManager.load("manlymanexplosion.anim", Animation.class);
		manPlayer.addAnimation("explode", explodeAnimation);
		manPlayer.startAnimation("walk");
		AnimationComp manAniCom = new AnimationComp(manPlayer);


		IEntity manEntity = manager.createEntity(ContentManager.loadArchetype("ManlyMan.archetype"));
		manEntity.addComponent(manAniCom);
		manEntity.refresh();
		//ENDMAN

		placeAtRandomPosition(manEntity);


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
		scene = ContentManager.load("PerspectiveV5.xml", Scene.class);
		camera = new Camera2D(scene.getWorldBounds(), screenDim);
		spriteBatch = new SpriteBatch(screenDim);
		mouse = new Mouse();
		keyboard = new Keyboard();

		SoundManager soundManager = new SoundManager(this.ContentManager,1.0f,1.0f,1.0f);

		this.gameWorld = WorldBuilder.buildGameWorld(camera, scene, mouse, keyboard, this.ContentManager,soundManager, spriteBatch, false, "player");
		gameWorld.initialize();

		//ANIMATION UGLY SHIT
		IEntityArchetype archetype = ContentManager.loadArchetype(playerAsset);
		IEntity entity = gameWorld.getEntityManager().createEntity(archetype);
		entity.addComponent(new BehaviourComp(new PlayerScript()));
		entity.addComponent(new ShadowComp());	
		
		AnimationPlayer player = ContentManager.load("rdset.animset", AnimationPlayer.class);
		
////		player.setBoneTexture(Bones.UPPERMANE, asd);
////		player.attachAnimationToBone(Bones.EYE.getValue(), ContentManager.load("monocle.anim", Animation.class));
		player.startAnimation("idle");
		AnimationComp animComp = new AnimationComp(player);
		PonyColorChangeHelper.setBodyColor(Color.Wheat, animComp);
		PonyColorChangeHelper.setEyeColor(Color.Gray, animComp);
		PonyColorChangeHelper.setManeColor(Color.Brown, animComp);
		TextureDictionary dict = ContentManager.load("rddict.tdict", TextureDictionary.class);
		player.setBoneTexture(Bones.EYE.getValue(), dict.extractTextureEntry("TSEYE"));
		player.setBoneTexture(Bones.UPPERMANE.getValue(), dict.extractTextureEntry("TSUPPERMANE"));
		player.setBoneTexture(Bones.LOWERMANE.getValue(), dict.extractTextureEntry("TSLOWERMANE"));
		player.setBoneTexture(Bones.UPPERTAIL.getValue(), dict.extractTextureEntry("TSUPPERTAIL"));
		player.setBoneTexture(Bones.LOWERTAIL.getValue(), dict.extractTextureEntry("TSLOWERTAIL"));
		player.setBoneTexture(Bones.PIPBUCK.getValue(), dict.extractTextureEntry("pipbuck"));

		player.attachAnimationToBone(Bones.ROOT.getValue(), ContentManager.load("weapon.anim", Animation.class));
		entity.addComponent(animComp);
		//END OF ANIMATION UGLY SHIT
		entity.getComponent(TransformationComp.class).setPosition(1000, 1000);

		

		entity.addToGroup(CameraControlSystem.GROUP_NAME);
		entity.refresh();

	}

	private void placeAtRandomPosition(IEntity entity) {
		Vector2 pos = new Vector2((float)Math.random() * (this.scene.getWorldBounds().Width - 100),
				(float)Math.random() * (this.scene.getWorldBounds().Height - 100));
		entity.getComponent(TransformationComp.class).setPosition(pos);
	}

}
