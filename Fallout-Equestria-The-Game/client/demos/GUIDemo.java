package demos;


import org.lwjgl.input.Keyboard;

import math.Point2;
import misc.IEventListener;
import GUI.Button;
import GUI.ButtonRenderer;
import GUI.GUIControl;
import GUI.GUIFocusManager;
import GUI.Panel;
import GUI.GUIPanelRenderer;
import GUI.GUIRenderingContext;
import GUI.Label;
import GUI.LabelRenderer;
import GUI.ScrollEventArgs;
import GUI.Slider;
import GUI.SliderRenderer;
import GUI.TextField;
import GUI.TextboxRenderer;

import com.google.inject.Guice;
import components.GUIComp;

import content.ContentManager;

import entityFramework.IEntity;
import entityFramework.IEntityManager;
import entityFramework.IEntitySystemManager;
import entityFramework.IEntityWorld;
import entitySystems.GUIRenderingSystem;
import entitySystems.ScriptMouseSystem;
import entitySystems.ScriptSystem;
import gameMap.Scene;
import graphics.Color;
import graphics.SpriteBatch;
import utils.Camera2D;
import utils.GameTime;
import utils.Mouse;
import utils.Rectangle;

public class GUIDemo extends Demo {
	private static final String playerAsset = "Player.archetype";
	private static final String aiAsset 	= "FollowingTextAI.archetype";
	private static Rectangle screenDim 		= new Rectangle(0,0,1366,768);

	private IEntityWorld gameWorld;
	private Camera2D camera;
	private SpriteBatch spriteBatch;
	private Mouse mouse;
	
	private GUIRenderingContext context;
	private GUIFocusManager manager;
	Panel panel;
	
	public static void main(String[] args) {
		new GUIDemo().start();
	}
	
	public GUIDemo() {
		super(screenDim, 60);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameTime time) {
		this.mouse.poll(camera);
		panel.checkMouseInput(new Point2(0,0), mouse);
		panel.checkKeyboardInput();
	}

	@Override
	public void render(GameTime time) {
		this.spriteBatch.clearScreen(Color.Black);
		panel.render(context, time);
	}

	@Override
	protected void initialize() {
		this.camera = new Camera2D(screenDim, screenDim);
		this.spriteBatch = new SpriteBatch(screenDim);
		this.mouse = new Mouse();
		context = new GUIRenderingContext(spriteBatch);
		context.addRenderer(new LabelRenderer());
		context.addRenderer(new GUIPanelRenderer());
		context.addRenderer(new ButtonRenderer());
		context.addRenderer(new TextboxRenderer());
		context.addRenderer(new SliderRenderer());
		
		
		
		panel = new Panel();
		panel.setBounds(new Rectangle(0,0,1366,768));
		

		Button falloutButton0 = new Button();
		falloutButton0.setBounds(new Rectangle(600,400,128,128));
		falloutButton0.setText("Click Me!");
		falloutButton0.setFgColor(new Color(200,50,200,255));
		falloutButton0.setImage(ContentManager.loadTexture("GUI/Fallout-2-icon.png"));
		falloutButton0.setOverTexture(ContentManager.loadTexture("GUI/Fallout-2-2-icon.png"));
		falloutButton0.setDownTexture(ContentManager.loadTexture("GUI/fallout_point_lookout.png"));
		falloutButton0.setFont(ContentManager.loadFont("arialb20.xml"));
		panel.addChild(falloutButton0);
		
		Label buttonLabel = new Label();
		buttonLabel.setBounds(800,345,128,50);
		buttonLabel.setFgColor(Color.Orange);
		buttonLabel.setText("Disabled \nbutton over here!");
		buttonLabel.setFont(ContentManager.loadFont("arialb20.xml"));
		panel.addChild(buttonLabel);
		


		Button disabledButton = new Button();
		disabledButton.setBounds(new Rectangle(800,400,128,128));
		disabledButton.setText("Click Me!");
		disabledButton.setFgColor(new Color(200,50,200,255));
		disabledButton.setImage(ContentManager.loadTexture("GUI/Fallout-2-icon.png"));
		disabledButton.setOverTexture(ContentManager.loadTexture("GUI/Fallout-2-2-icon.png"));
		disabledButton.setDownTexture(ContentManager.loadTexture("GUI/fallout_point_lookout.png"));
		disabledButton.setFont(ContentManager.loadFont("arialb20.xml"));
		disabledButton.setEnabled(false);
		panel.addChild(disabledButton);

		
		Label textFieldLabel = new Label();
		textFieldLabel.setBounds(new Rectangle(200,200,250,23));
		textFieldLabel.setFgColor(Color.Orange);
		textFieldLabel.setText("Label over a textfield!");
		textFieldLabel.setFont(ContentManager.loadFont("arialb20.xml"));
		panel.addChild(textFieldLabel);
		

		TextField field = new TextField();
		field.setBounds(new Rectangle(200,225,250,23));
		field.setText("Testing...");
		field.setBackground(ContentManager.loadTexture("GUI/guiBackground.png"));
		field.setFont(ContentManager.loadFont("arialb20.xml"));
		field.setFgColor(Color.Goldenrod);
		field.setMaxLength(13);
		panel.addChild(field);
		
		final Label sliderLabel = new Label();
		sliderLabel.setBounds(new Rectangle(200,400,250, 50));
		sliderLabel.setFgColor(Color.Orange);
		sliderLabel.setText("Label over a slider that \ncontrols color!");
		sliderLabel.setFont(ContentManager.loadFont("arialb20.xml"));
		panel.addChild(sliderLabel);
		
		
		Button sliderButton = new Button();
		sliderButton.setBounds(new Rectangle(0,0,10,35));
		sliderButton.setImage(ContentManager.loadTexture("GUI/buttonmoseover.png"));
		sliderButton.setFgColor(Color.Purple);
		sliderButton.setOverTexture(ContentManager.loadTexture("GUI/falloutOverButton.png"));
		sliderButton.setDownTexture(ContentManager.loadTexture("GUI/buttonmoseover.png"));
		
		
		Slider slider = new Slider(sliderButton);
		slider.setSlideMax(255);
		slider.setBounds(new Rectangle(200,455,250,30));
		slider.setBackground(ContentManager.loadTexture("GUI/guiBackground.png"));
		slider.addScrollListener(new IEventListener<ScrollEventArgs>() {
			
			@Override
			public void onEvent(Object sender, ScrollEventArgs e) {
				sliderLabel.setFgColor(new Color(e.getNewValue(),0,150,255));
			}
		});
		
		panel.addChild(slider);
		
		
		
		
		manager = new GUIFocusManager(panel);
	}

}
