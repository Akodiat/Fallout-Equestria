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

		//TEXTURES
		Texture2D textureMan = ContentManager.loadTexture("manlyman.png");

		TextureBounds boundsMan = new TextureBounds();
		boundsMan.setOrigin(new Vector2(textureMan.getBounds().Width/2, textureMan.getBounds().Height/2));
		boundsMan.setLocation(textureMan.getBounds());

		TextureEntry entryMan = new TextureEntry();
		entryMan.setTexture(textureMan);
		entryMan.setUseDictionary(false);
		entryMan.setTextureBounds(boundsMan);

		Texture2D textureArm = ContentManager.loadTexture("manlymanarm.png");

		TextureBounds boundsArm = new TextureBounds();
		boundsArm.setOrigin(new Vector2(textureArm.getBounds().Width/2, textureArm.getBounds().Height/2));
		boundsArm.setLocation(textureArm.getBounds());

		TextureEntry entryArm = new TextureEntry();
		entryArm.setTexture(textureArm);
		entryArm.setUseDictionary(false);
		entryArm.setTextureBounds(boundsArm);
		
		List<TextureEntry> textures = new ArrayList<TextureEntry>();
		textures.add(entryMan);
		textures.add(entryArm);	

		//BONES
		Bone boneArm1 = new Bone();
		boneArm1.setName("manlyarm");
		boneArm1.setParentIndex(0);
		boneArm1.setHidden(false);
		boneArm1.setSelfIndex(1);
		boneArm1.setPosition(Vector2.Zero);
		boneArm1.setRotation(0);
		boneArm1.setScale(Vector2.One);
		boneArm1.setTextureIndex(1);
		boneArm1.setUpdateIndex(1);
		
		Bone boneMan1 = new Bone();
		boneMan1.setName("manlyman");
		boneMan1.setParentIndex(-1);
		boneMan1.setHidden(false);
		boneMan1.setSelfIndex(1);
		boneMan1.setPosition(Vector2.Zero);
		boneMan1.setRotation(0);
		boneMan1.setScale(Vector2.One);
		boneMan1.setTextureIndex(0);
		boneMan1.setUpdateIndex(0);
		
		List<Bone> bones1 = new ArrayList<Bone>();
		bones1.add(boneMan1);
		bones1.add(boneArm1);
		
		Bone boneArm2 = new Bone();
		boneArm2.setName("manlyarm");
		boneArm2.setParentIndex(0);
		boneArm2.setHidden(false);
		boneArm2.setSelfIndex(1);
		boneArm2.setPosition(new Vector2(12f, 26f));
		boneArm2.setRotation(0.5f);
		boneArm2.setScale(Vector2.One);
		boneArm2.setTextureIndex(1);
		boneArm2.setUpdateIndex(1);
		
		Bone boneMan2 = new Bone();
		boneMan2.setName("manlyman");
		boneMan2.setParentIndex(-1);
		boneMan2.setHidden(false);
		boneMan2.setSelfIndex(1);
		boneMan2.setPosition(Vector2.Zero);
		boneMan2.setRotation(1);
		boneMan2.setScale(Vector2.One);
		boneMan2.setTextureIndex(0);
		boneMan2.setUpdateIndex(0);
		
		List<Bone> bones2 = new ArrayList<Bone>();
		bones2.add(boneMan2);
		bones2.add(boneArm2);
		
		//KEYFRAMES
		Keyframe keyframe1 = new Keyframe();
		keyframe1.setBones(bones1);
		keyframe1.setFrameNumber(0);
		keyframe1.setMirrored(false);
		keyframe1.setTrigger("");
		keyframe1.setFrameTime(0f);
		keyframe1.sortBones();
		
		Keyframe keyframe2 = new Keyframe();
		keyframe2.setBones(bones2);
		keyframe2.setFrameNumber(10);
		keyframe2.setMirrored(false);
		keyframe2.setTrigger("");
		keyframe2.setFrameTime(0.5f);
		keyframe2.sortBones();
		
		List<Keyframe> keyframes1 = new ArrayList<Keyframe>();
		keyframes1.add(keyframe1);
		keyframes1.add(keyframe2);

		Animation playerAnim = new Animation();
		playerAnim.setFrameRate(20);
		playerAnim.setLoop(true);
		playerAnim.setVersion("asdLOLf");
		playerAnim.setTextures(textures);
		playerAnim.setLoopTime(1f);
		playerAnim.setKeyframes(keyframes1);
		playerAnim.setLoopFrame(20);
		
		AnimationPlayer animPlayer = new AnimationPlayer();
		animPlayer.addAnimation("lol", playerAnim);		
		animPlayer.startAnimation("lol");
		
		entity.addComponent(new AnimationComp(animPlayer));
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
