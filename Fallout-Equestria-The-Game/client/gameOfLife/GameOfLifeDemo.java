package gameOfLife;

import graphics.Color;
import java.util.Random;

import com.google.inject.Guice;

import math.Point2;
import math.Vector2;
import misc.EventArgs;
import misc.IEventListener;

import GUI.GUIFocusManager;
import GUI.controls.Button;
import GUI.graphics.GUIRenderingContext;
import GUI.graphics.LookAndFeel;
import demos.Demo;

import entityFramework.*;
import entitySystems.*;
import graphics.SpriteBatch;

import tests.EntityModule;
import utils.Camera2D;
import utils.GameTime;
import utils.Mouse;
import utils.Rectangle;

public class GameOfLifeDemo extends Demo {

	private static Rectangle screenDim = new Rectangle(0,0,1366,768);
	private static Rectangle worldBounds = new Rectangle(0, 0, 1366 * 5, 768 * 5);
	private static final int blockSize = 64;
	private static final String groupName = "GameOL";
	private static String cellArchetype = "GameOfLifeCell.archetype";
	
	private IEntityWorld world;
	private SpriteBatch spriteBatch;
	private Camera2D camera;
	private GameOfLifeLogicSystem golSystem;
	
	private GUIRenderingContext context;
	private Mouse mouse;
	
	Button button;
		
	public static void main(String[] args) {
		new GameOfLifeDemo().start();
	}
	
	public GameOfLifeDemo() {
		super(screenDim,60);
	}
	
	@Override
	protected void initialize() {
		this.camera = new Camera2D(worldBounds, screenDim);
		this.camera.setZoom(new Vector2(0.2f, 0.2f));
		this.camera.setPosition(Vector2.Zero);
		this.spriteBatch = new SpriteBatch(screenDim);
		this.mouse = new Mouse();
		this.context = new GUIRenderingContext(spriteBatch, this.ContentManager.load("gui.tdict", LookAndFeel.class), 
											   this.ContentManager.loadShaderEffect("GrayScale.effect"));
		
		
		
		world = buildGameOfLifeWorld(camera, spriteBatch, true);
		world.initialize();
		
		
	
		//Primitive GUI!
		
		this.button = new Button();
		this.button.setFont(this.ContentManager.loadFont("arialb20.xml"));
		this.button.setText("Seed");
		this.button.setBounds(0,710,200,50);
		new GUIFocusManager(button);
		
		final Random random = new Random();
		this.button.addClicked(new IEventListener<EventArgs>() {
			@Override
			public void onEvent(Object sender, EventArgs e) {
				golSystem.seedNewLife(random.nextLong());
			}
		});
		
	}
	
	
	public  IEntityWorld buildGameOfLifeWorld(Camera2D camera, SpriteBatch spriteBatch, boolean debugging) {
		IEntityWorld world = Guice.createInjector(new EntityModule()).getInstance(IEntityWorld.class);
		IEntitySystemManager manager = world.getSystemManager();
		
		this.golSystem = new GameOfLifeLogicSystem(world,
												   this.ContentManager,
												   blockSize, 
												   new Point2((worldBounds.Width) / blockSize, (worldBounds.Height - 220) / blockSize ), 
												   cellArchetype, 
												   groupName);
		
		
		//Logic systems!
		manager.addLogicEntitySystem(golSystem);
		
		//Rendering systems!
		manager.addRenderEntitySystem(new RenderingSystem(world, spriteBatch));
		manager.addRenderEntitySystem(new TextRenderingSystem(world, spriteBatch));
		
		
		return world;
	}
	
	@Override
	public void update(GameTime time) {
		this.world.update(time);
		this.world.getEntityManager().destoryKilledEntities();
		mouse.poll(this.camera);
		button.checkMouseInput(new Point2(0,0),this.mouse);
		
	}

	@Override
	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);

		button.render(this.context, time);
		this.spriteBatch.begin(null, camera.getTransformation());
		this.world.render();		
		this.spriteBatch.end();
		
	}
}
