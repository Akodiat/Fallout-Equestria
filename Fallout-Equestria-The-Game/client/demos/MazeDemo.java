package demos;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import behavior.Behavior;
import behavior.ChangelingAIScript;
import behavior.PillBehavior;
import behavior.PlayerScript;
import behavior.PortalBehavior;
import behavior.SpawnBehaviour;

import math.MathHelper;
import math.Matrix3;
import math.Matrix4;
import math.Vector2;
import animation.Animation;
import animation.AnimationPlayer;
import components.*;
import entityFramework.*;
import entitySystems.CameraControlSystem;
import gameMap.Scene;

import gameMap.TexturedSceneNode;
import graphics.Color;
import graphics.IShaderEvent;
import graphics.PostProcessingManager;
import graphics.RenderTarget2D;
import graphics.ShaderEffect;
import graphics.SpriteBatch;
import graphics.SpriteBatch.SortMode;
import graphics.Texture2D;
import sounds.SoundManager;
import systembuilders.WorldBuilder;
import utils.Camera2D;
import utils.Rectangle;
import utils.input.Keyboard;
import utils.input.Mouse;
import utils.time.GameTime;

public class MazeDemo extends Demo {
	private static final String playerAsset = "Player.archetype";
	private static Rectangle screenDim 		= new Rectangle(0,0,1366,768);

	private IEntityWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Scene scene;
	private Mouse mouse;
	private Keyboard keyboard;
	private PostProcessingManager postManager;
	private RenderTarget2D renderTarget;

	public static void main(String[] str) {
		MazeDemo demo = new MazeDemo();
		demo.start();
	}

	public MazeDemo() {
		super(screenDim, 250);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameTime time) {

		Display.setTitle("Elapsed Time: " + time.getElapsedTime().getTotalMiliSeconds());

		this.mouse.poll(screenDim);
		this.keyboard.poll();
		this.gameWorld.update(time);
		this.gameWorld.getEntityManager().destoryKilledEntities();

		if(Math.random() < 0.5f) {
			IEntityDatabase database  = this.gameWorld.getDatabase();
			if(database.getEntityCount() < 20) {
				IEntity man = spawnManlyMan();
				if(Math.random() < 0.1f) {
					man.getComponent(TransformationComp.class).setHeight(100);
				}
			}
		}
	}

	private IEntity spawnManlyMan() {

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
		manEntity.addComponent(new ShadowComp());

		manEntity.getComponent(TransformationComp.class).setRotation((float)(MathHelper.Tau * Math.random()));

		if(Math.random() < 0.3){
			Behavior behavior = new ChangelingAIScript();
			manEntity.getComponent(BehaviourComp.class).setBehavior(behavior);
		} else if(Math.random() < 0.1) {
			Behavior behavior = new PillBehavior();
			manEntity.getComponent(BehaviourComp.class).setBehavior(behavior);
			
			AnimationPlayer player = ContentManager.loadAnimationSet("pill.animset").clone();
			manAniCom.setAnimationPlayer(player);		
			manEntity.getComponent(TransformationComp.class).setScale(1,1);
		}

		manEntity.refresh();
		//ENDMAN

		placeAtRandomPosition(manEntity);
		return manEntity;

	}

	@Override
	public void render(GameTime time) {

		Vector2 cameraRenderTarget2Dpos = new Vector2(-this.camera.getWorldPosition().X,
													  this.camera.getWorldPosition().Y);
		
		this.spriteBatch.clearScreen(Color.Black);
		this.spriteBatch.begin(null, Matrix4.createTranslation(cameraRenderTarget2Dpos), this.renderTarget, true, SortMode.None);
		this.gameWorld.render();
		this.spriteBatch.end();	
		
		
		this.postManager.applyEffectsToTarget(renderTarget);
		
		
		Texture2D texture = this.renderTarget.getTexture();
		
		this.spriteBatch.begin();
		this.spriteBatch.draw(texture, Vector2.Zero, Color.White);		
		this.spriteBatch.end();
		
	}

	@Override
	protected void initialize() {
		Display.setVSyncEnabled(false);
		this.renderTarget = new RenderTarget2D(screenDim.Width, screenDim.Height);
		
		
		scene = ContentManager.load("PerspectiveV5.xml", Scene.class);

		camera = new Camera2D(scene.getWorldBounds(), screenDim);
		spriteBatch = new SpriteBatch(screenDim);
		postManager = new PostProcessingManager(this.spriteBatch);
		final ShaderEffect effect = this.ContentManager.loadShaderEffect("HorizontalBlur.effect");

		effect.setAppliedListener(new IShaderEvent() {			
			@Override
			public void onApply() {
				effect.setUniform("blurSize",1.0f  / (float)screenDim.Width);
			}
		});
		final ShaderEffect effect2 = this.ContentManager.loadShaderEffect("VerticalBlur.effect");
		effect2.setAppliedListener(new IShaderEvent() {	
			@Override
			public void onApply() {
				effect2.setUniform("blurSize",1.0f / (float)screenDim.Height);
			}
		});
		final ShaderEffect effect3 = this.ContentManager.loadShaderEffect("BloomExtract.effect");
		effect3.setAppliedListener(new IShaderEvent() {	
			@Override
			public void onApply() {
				effect3.setUniform("bloomThreshold", 0.4f);
			}
		});
		final ShaderEffect effect4 = this.ContentManager.loadShaderEffect("BloomFinal.effect");
		effect4.setAppliedListener(new IShaderEvent() {	
			@Override
			public void onApply() {
				effect4.setUniform("bloomIntensity", 0.8f);
				effect4.setUniform("bloomSaturation", 0.5f);
			}
		});
		
		final ShaderEffect effect5 = this.ContentManager.loadShaderEffect("ExperimentalBloom.effect");
		effect5.setAppliedListener(new IShaderEvent() {	
			@Override
			public void onApply() {
			}
		});
		
		
		
		this.postManager.pushPostProcessingEffect(effect3);
		this.postManager.pushPostProcessingEffect(effect);
		this.postManager.pushPostProcessingEffect(effect2);
		this.postManager.pushPostProcessingEffect(effect4);
		
		
		mouse = new Mouse();
		keyboard = new Keyboard();
		SoundManager soundManager = new SoundManager(this.ContentManager,0.1f,1.0f,1.0f);

		this.gameWorld = WorldBuilder.buildGameWorld(camera, scene ,mouse, keyboard, this.ContentManager,soundManager, spriteBatch, true, "Player");

		gameWorld.initialize();
		addTexturedNodes();

		//ANIMATION UGLY SHIT
		IEntityArchetype archetype = ContentManager.loadArchetype(playerAsset);
		IEntity entity = this.gameWorld.getEntityManager().createEntity(archetype);
		entity.addComponent(new BehaviourComp(new PlayerScript()));
		entity.addComponent(new ShadowComp());
		entity.getComponent(TransformationComp.class).setPosition(1000,1000);	

		AnimationPlayer player = this.ContentManager.loadAnimationSet("cactuar.animset");
		AnimationComp comp = new AnimationComp(player);
		comp.setTint(Color.Green);
		
		entity.removeComponent(RenderingComp.class);	
		entity.addComponent(comp);
		//END OF ANIMATION UGLY SHIT

		entity.addToGroup(CameraControlSystem.GROUP_NAME);
		entity.refresh();
		
		
		IEntityArchetype entityArchtype = ContentManager.loadArchetype("Portal.archetype");
		
		IEntity portalA = gameWorld.getEntityManager().createEntity(entityArchtype);
		portalA.addComponent(new BehaviourComp(new PortalBehavior("PortalA", "PortalB")));
		portalA.getComponent(TransformationComp.class).setPosition(100, 1000);
		portalA.refresh();
		
		IEntity portalB = gameWorld.getEntityManager().createEntity(entityArchtype);
		portalB.addComponent(new BehaviourComp(new PortalBehavior("PortalB", "PortalA")));
		portalB.getComponent(TransformationComp.class).setPosition(5000, 1000);
		portalB.refresh();
		
		
		IEntity spawnPoint = gameWorld.getEntityManager().createEntity(ContentManager.loadArchetype("Spawn.archetype"));
		spawnPoint.addComponent(new BehaviourComp(new SpawnBehaviour("Bullet.archetype", 10)));
		spawnPoint.getComponent(TransformationComp.class).setPosition(1000, 1500);
		spawnPoint.refresh();
		
	}

	private void addTexturedNodes() {
		for (TexturedSceneNode tNode : this.scene.getTexturedNodes()) {
			IEntity entity = this.gameWorld.getEntityManager().createEmptyEntity();
			TransformationComp transComp = new TransformationComp();
			System.out.println(tNode.getPosition());
			transComp.setPosition(tNode.getPosition());
			transComp.setOrigin(tNode.getSrcRectangle().getCenter());
			RenderingComp renderComp = new RenderingComp();
			renderComp.setTexture(tNode.getTexture());
			renderComp.setSource(tNode.getSrcRectangle());

			entity.addComponent(transComp);
			entity.addComponent(renderComp);
			entity.refresh();
			
			System.out.println("WAS ADDED!");
		}


	}

	private void placeAtRandomPosition(IEntity entity) {
		Vector2 pos = new Vector2((float)Math.random() * (this.scene.getWorldBounds().Width - 100),
				(float)Math.random() * (this.scene.getWorldBounds().Height - 100));
		entity.getComponent(TransformationComp.class).setPosition(pos);
	}

}
