package screenCore;

import animation.AnimationPlayer;
import animation.Bones;
import animation.PonyColorChangeHelper;
import animation.TextureDictionary;

import common.PlayerCharacteristics;

import GUI.GUIFocusManager;
import GUI.controls.Button;
import GUI.controls.Textfield;
import GUI.graphics.GUIRenderingContext;
import GUI.graphics.LookAndFeel;
import math.Point2;
import math.Vector2;
import misc.EventArgs;
import misc.IEventListener;
import utils.GameTime;
import utils.Keyboard;
import utils.Mouse;
import utils.TimeSpan;
import content.ContentManager;
import graphics.Color;
import graphics.ShaderEffect;
import graphics.SpriteBatch;


public class PonyCreatorScreen extends TransitioningGUIScreen {


	public static final Vector2 ponyPosition = new Vector2(1150,200);
	public static final Vector2 ponyScale = new Vector2(2,2);
	private GUIRenderingContext context;
	private Button button;
	private Button button2;
	private Textfield textfield;
	private ContentManager contentManager = new ContentManager("resources");
	private AnimationPlayer pony;
	private PlayerCharacteristics character;


	public PonyCreatorScreen(String lookAndFeelPath) {
		super(false, TimeSpan.Zero, TimeSpan.Zero, lookAndFeelPath);
	}

	@Override
	public void initialize(ContentManager contentManager) {
		super.initialize(contentManager);
		
		addPony();
		
		this.button = new Button();
		this.button.setText("Do stuff");
		this.button.setBounds(0,710,200,50);
		new GUIFocusManager(button);
		super.addGuiControl(button, new Vector2(10,50), new Vector2(200,50), new Vector2(10, 100));


		this.button2 = new Button();
		this.button2.setText("Do other stuff?");
		this.button2.setBounds(250,710,200,50);
		super.addGuiControl(button2, new Vector2(-200, 100), new Vector2(200,100), new Vector2(200,50));

		this.textfield = new Textfield();
		this.textfield.setText("");
		this.textfield.setBounds(100,100,300,30);
		this.textfield.setFont(this.contentManager.loadFont("Andale Mono20.xml"));
		this.textfield.setFgColor(Color.White);
		this.textfield.setMaxLength(25);
		super.addGuiControl(this.textfield, new Vector2(-200, 100), new Vector2(200,50), new Vector2(-200, 100));
		

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
			this.pony.update(time);
		}
	}

	private void addPony() {	
		this.pony = this.contentManager.load("rdset.animset", AnimationPlayer.class);;
		this.pony.startAnimation("idle");
		PonyColorChangeHelper.setBodyColor(Color.Wheat, this.pony);
		PonyColorChangeHelper.setEyeColor(Color.Chocolate, this.pony);
		PonyColorChangeHelper.setManeColor(Color.Chocolate, this.pony);
		TextureDictionary dict = this.contentManager.load("rddict.tdict", TextureDictionary.class);
		this.pony.setBoneTexture(Bones.EYE.getValue(), dict.extractTextureEntry("TSEYE"));
	}
	
	@Override
	public void render(GameTime time, SpriteBatch batch) {
		batch.begin();
		this.pony.draw(batch, ponyPosition, false, 0, Color.White, ponyScale);
		super.render(time, batch);
	}


}
