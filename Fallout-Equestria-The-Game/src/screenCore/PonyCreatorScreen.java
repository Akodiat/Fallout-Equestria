package screenCore;

import java.util.Random;

import components.TransformationComp;

import GUI.GUIFocusManager;
import GUI.controls.Button;
import GUI.controls.Panel;
import GUI.graphics.GUIRenderingContext;
import GUI.graphics.LookAndFeel;
import math.Point2;
import misc.EventArgs;
import misc.IEventListener;
import utils.Camera2D;
import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.Rectangle;
import utils.TimeSpan;
import content.ContentManager;
import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entitySystems.AnimationSystem;
import entitySystems.RenderingSystem;
import graphics.Color;
import graphics.ShaderEffect;
import graphics.SpriteBatch;


public class PonyCreatorScreen extends EntityScreen {

	
	public static final String playerBasePath = "player.archetype";
	private GUIRenderingContext context;
	private Panel panel;
	private Button button;
	private Button button2;
	private AnimationSystem animSys;
	private ContentManager contentManager = new ContentManager("resources");
//	private IEntity pony
	
	
	public PonyCreatorScreen() {
		super(false, TimeSpan.Zero, TimeSpan.Zero);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void loadContent(ContentManager contentManager) {
				
		panel = new Panel();
		panel.setBounds(this.ScreenManager.getViewport());
		panel.setBgColor(new Color(0,0,0,0));
		
		
		this.button = new Button();
		this.button.setText("Do stuff");
		this.button.setBounds(0,710,200,50);
		this.panel.addChild(button);
		new GUIFocusManager(button);
		
		this.button2 = new Button();
		this.button2.setText("Do other stuff?");
		this.button2.setBounds(250,710,200,50);
		this.panel.addChild(button2);
		
		LookAndFeel feel = contentManager.load("gui.tdict", LookAndFeel.class);
		feel.setDefaultFont(contentManager.loadFont("arialb20.xml"));
		ShaderEffect dissabledEffect = contentManager.loadShaderEffect("GrayScale.effect");
		context = new GUIRenderingContext(this.ScreenManager.getSpriteBatch(), feel, dissabledEffect);
		
		this.button.addClicked(new IEventListener<EventArgs>() {
			@Override
			public void onEvent(Object sender, EventArgs e) {
				System.out.println("Stuff has been done!");
			}
		});
		
		this.button2.addClicked(new IEventListener<EventArgs>() {
			
			@Override
			public void onEvent(Object sender, EventArgs e) {
				System.out.println("Other stuff has been done!");
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
	protected void addRenderingSystem(IEntitySystemManager systemManager) {
		systemManager.addRenderEntitySystem(new RenderingSystem(this.World, this.ScreenManager.getSpriteBatch()));
	}

	@Override
	protected void addLogicSystem(IEntitySystemManager systemManager) {
		Rectangle vp = this.ScreenManager.getViewport();
		Rectangle worldBounds = new Rectangle(0, 0, vp.Width, vp.Height);
		
		this.animSys = new AnimationSystem(this.World, this.ScreenManager.getSpriteBatch(), 
										   new Camera2D(worldBounds, vp));
		systemManager.addRenderEntitySystem(this.animSys);
	}

	@Override
	protected void addEntities(IEntityManager entityManager) {	
		IEntity pony = entityManager.createEntity(this.contentManager.loadArchetype(playerBasePath));
		TransformationComp transformationComp = pony.getComponent(TransformationComp.class);
		transformationComp.setPosition(this.ScreenManager.getViewport().Width - (transformationComp.getOrigin().X*2), this.ScreenManager.getViewport().Height - (transformationComp.getOrigin().Y*2));
	}

	@Override
	public void handleInput(Mouse mouse, Keyboard keyboard) {
		this.panel.checkMouseInput(new Point2(0,0), mouse);
		this.panel.checkKeyboardInput(keyboard);
	}
	
	@Override
	public void render(GameTime time, SpriteBatch batch) {
		super.render(time, batch);
		this.panel.render(context, time);
	}

}
