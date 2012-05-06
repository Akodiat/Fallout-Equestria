package demos;

import math.MathHelper;
import math.Vector2;
import animation.Animation;
import animation.AnimationPlayer;
import animation.TextureDictionary;
import animation.TextureEntry;
import components.*;
import entityFramework.*;
import entitySystems.CameraControlSystem;
import gameMap.Scene;
import gameMap.SceneNode;

import gameMap.TexturedSceneNode;
import graphics.Color;
import graphics.SpriteBatch;
import scripting.PlayerScript;
import utils.Camera2D;
import utils.GameTime;
import utils.Rectangle;

public class MazeDemo extends Demo {
	private static final String playerAsset = "Player.archetype";
	private static Rectangle screenDim 		= new Rectangle(0,0,1920,1020);

	private IEntityWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Scene scene;

	public static void main(String[] str) {
		MazeDemo demo = new MazeDemo();
		demo.start();
	}

	public MazeDemo() {
		super(screenDim, 60);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameTime time) {
		this.gameWorld.update(time);
		this.gameWorld.getEntityManager().destoryKilledEntities();
		
		if(Math.random() < 0.5f) {
			IEntityDatabase database  = this.gameWorld.getDatabase();
			if(database.getEntityCount() < 850) {
				spawnManlyMan();
			}
		}

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

		manEntity.getComponent(TransformationComp.class).setRotation((float)(MathHelper.Tau * Math.random()));
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
		scene = ContentManager.load("PerspectiveV1.xml", Scene.class);
		camera = new Camera2D(scene.getWorldBounds(), screenDim);
		spriteBatch = new SpriteBatch(screenDim);

		this.gameWorld = WorldBuilder.buildGameWorld(camera, scene,this.ContentManager, spriteBatch, false);
		gameWorld.initialize();

		//ANIMATION UGLY SHIT
		IEntityArchetype archetype = ContentManager.loadArchetype(playerAsset);
		IEntity entity = this.gameWorld.getEntityManager().createEntity(archetype);
		entity.addComponent(new BehaviourComp(new PlayerScript()));

		entity.getComponent(TransformationComp.class).setPosition(1000,1000);
		
		SceneNode playerPosNode = scene.getNodeByID("PlayerSpawnPosition");
		entity.getComponent(TransformationComp.class).setPosition(playerPosNode.getPosition());
		addTexturedNodes();
		
		
		Animation animation = ContentManager.load("rdwalk.anim", Animation.class);
		AnimationPlayer player = new AnimationPlayer();
		player.addAnimation("lol", animation);
		player.startAnimation("lol");
		TextureDictionary dict = ContentManager.load("gui.tdict", TextureDictionary.class);
		TextureEntry entry = new TextureEntry(dict.getTexture(), dict.getTextureBounds("Scrollbar_BG"));
		player.setBoneTexture("RIGHTFRONTOOF", entry);
		
		AnimationComp comp = new AnimationComp(player);
		comp.setTint(Color.Green);
		
		entity.removeComponent(RenderingComp.class);	
		entity.addComponent(comp);
		//END OF ANIMATION UGLY SHIT
		

		entity.addToGroup(CameraControlSystem.GROUP_NAME);
		entity.refresh();

	}

	private void addTexturedNodes() {
		for (TexturedSceneNode tNode : this.scene.getTexturedNodes()) {
			IEntity entity = this.gameWorld.getEntityManager().createEmptyEntity();
			TransformationComp transComp = new TransformationComp();
			transComp.setPosition(tNode.getPosition());
			
			RenderingComp renderComp = new RenderingComp();
			renderComp.setTexture(tNode.getTexture());
			
			entity.addComponent(transComp);
			entity.addComponent(renderComp);
			entity.refresh();
		}
		
		
	}

	private void placeAtRandomPosition(IEntity entity) {
		Vector2 pos = new Vector2((float)Math.random() * (this.scene.getWorldBounds().Width - 100),
				(float)Math.random() * (this.scene.getWorldBounds().Height - 100));
		entity.getComponent(TransformationComp.class).setPosition(pos);
	}

}
