package screenCore;

import java.util.Random;

import GUI.Button;
import GUI.GUIFocusManager;
import GUI.GUIRenderingContext;
import GUI.LookAndFeel;
import GUI.Panel;
import math.Point2;
import utils.EventArgs;
import utils.IEventListener;
import utils.Rectangle;
import utils.input.Keyboard;
import utils.input.Mouse;
import utils.time.GameTime;
import utils.time.TimeSpan;
import content.ContentManager;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entitySystems.RenderingSystem;
import gameOfLife.GameOfLifeLogicSystem;
import graphics.Color;
import graphics.ShaderEffect;
import graphics.SpriteBatch;


public class GOLScreen extends EntityScreen {

	private final int worldSize = 5;
	private final int blockSize = 64;
	private static final String groupName = "GameOL";
	private static String cellArchetype = "GameOfLifeCell.archetype";
	private GUIRenderingContext context;
	private Panel panel;
	private Button button;
	private Button button2;
	private GameOfLifeLogicSystem golSystem;
	
	public GOLScreen() {
		super(false, TimeSpan.Zero, TimeSpan.Zero);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void loadContent(ContentManager contentManager) {
				
		panel = new Panel();
		panel.setBounds(this.ScreenManager.getViewport());
		panel.setBgColor(new Color(0,0,0,0));
		
		
		this.button = new Button();
		this.button.setText("Seed");
		this.button.setBounds(0,710,200,50);
		this.panel.addChild(button);
		new GUIFocusManager(button);
		
		this.button2 = new Button();
		this.button2.setText("Pause");
		this.button2.setBounds(250,710,200,50);
		this.panel.addChild(button2);
		
		LookAndFeel feel = contentManager.load("gui.tdict", LookAndFeel.class);
		feel.setDefaultFont(contentManager.loadFont("arialb20.xml"));
		ShaderEffect dissabledEffect = contentManager.loadShaderEffect("GrayScale.effect");
		context = new GUIRenderingContext(this.ScreenManager.getSpriteBatch(), feel, dissabledEffect);
		
		final Random random = new Random();
		this.button.addClicked(new IEventListener<EventArgs>() {
			@Override
			public void onEvent(Object sender, EventArgs e) {
				golSystem.seedNewLife(random.nextLong());
			}
		});
		
		this.button2.addClicked(new IEventListener<EventArgs>() {
			
			@Override
			public void onEvent(Object sender, EventArgs e) {
				showPauseScreen();
			}
		});
		
	}

	protected void showPauseScreen() {
		this.ScreenManager.addScreen("PauseScreen");
	}

	
	@Override
	public void update(GameTime time, boolean otherScreeenHasFocus,
			boolean coveredByOtherScreen) {
		if(!otherScreeenHasFocus) {
			super.update(time, otherScreeenHasFocus, coveredByOtherScreen);
		}
	}
	

	@Override
	protected void addEntitySystems(IEntitySystemManager manager) {
		Rectangle vp = this.ScreenManager.getViewport();
		Rectangle worldBounds = new Rectangle(0, 0, vp.Width * worldSize, vp.Height * worldSize);

		this.golSystem = new GameOfLifeLogicSystem(this.World,
												   this.ScreenManager.getContentManager(),
				   blockSize, 
				   new Point2((worldBounds.Width) / blockSize, (worldBounds.Height - 220) / blockSize ), 
				   cellArchetype, 
				   groupName);
		
		
		manager.addLogicEntitySystem(golSystem);
		manager.addRenderEntitySystem(new RenderingSystem(this.World, this.ScreenManager.getSpriteBatch()));
	}

	@Override
	protected void addEntities(IEntityManager entityManager) {	
	}

	@Override
	public void handleInput(Mouse mouse, Keyboard keyboard) {
		this.panel.checkMouseInput(new Point2(0,0), mouse);
		this.panel.checkKeyboardInput(keyboard);
	}
	
	@Override
	public void render(GameTime time, SpriteBatch batch) {
		batch.begin();
		this.World.render();
		batch.end();
		
		this.panel.render(context, time);
	}


}
